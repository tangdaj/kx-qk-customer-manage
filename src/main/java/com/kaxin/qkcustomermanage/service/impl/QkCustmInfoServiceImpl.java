package com.kaxin.qkcustomermanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaxin.qkcustomermanage.entity.DTO.QkFollowerDTO;
import com.kaxin.qkcustomermanage.entity.PO.QkCustmAllocationBatch;
import com.kaxin.qkcustomermanage.entity.PO.QkCustmInfo;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationLogVO;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationVO;
import com.kaxin.qkcustomermanage.entity.VO.QkGroupVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
import com.kaxin.qkcustomermanage.exception.KaXinException;
import com.kaxin.qkcustomermanage.mapper.QkCustmInfoMapper;
import com.kaxin.qkcustomermanage.service.QkCustmAllocationBatchService;
import com.kaxin.qkcustomermanage.service.QkCustmInfoService;
import com.kaxin.qkcustomermanage.service.QkCustomerService;
import com.kaxin.qkcustomermanage.service.QkGroupService;
import com.kaxin.qkcustomermanage.servlet.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
@Service
public class QkCustmInfoServiceImpl extends BaseServiceImpl<QkCustmInfoMapper, QkCustmInfo> implements QkCustmInfoService {

    @Autowired
    private QkCustomerService qkCustomerService;
    @Autowired
    private QkCustmAllocationBatchService qkCustmAllocationBatchService;
    @Autowired
    private QkGroupService qkGroupService;

    private List<QkCustmInfo> queryTotalCustmInfo() {
        List<QkCustmInfo> qkCustmInfoList = new ArrayList<>();
        int startPo = 0;
        int count = 1000;
        while (true) {
            JSONObject jsonObject = qkCustomerService.queryCustmInfoList(startPo, count);
            JSONArray customers = jsonObject.getJSONArray("custms");
            if (customers.isEmpty()) {
                break;
            }
            List<QkCustmInfo> qkCustmInfos = customers.toJavaList(QkCustmInfo.class);
            qkCustmInfoList.addAll(qkCustmInfos);
            startPo = startPo + count;
        }
        return qkCustmInfoList;
    }

    private static boolean allocationCache = false;

    /**
     * 分配客户
     *
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allocation() {
        if (allocationCache) {
            throw new KaXinException("当前系统正在进行分配，请稍后再试");
        }
        allocationCache = true;
        try {
            QkCustmAllocationBatch qkCustmAllocationBatch = new QkCustmAllocationBatch();

            List<QkCustmInfo> qkCustmInfos = queryTotalCustmInfo();
            List<QkCustmInfo> oldQkCustmInfos = list();
            DateTime dateTime = DateUtil.parseDate(DateUtil.formatDateTime(DateUtil.date()));
            qkCustmInfos = qkCustmInfos.stream().filter(source -> !oldQkCustmInfos.contains(source) && source.getCreateTime().after(dateTime)).collect(Collectors.toList());
            int custmSize = qkCustmInfos.size();
            qkCustmAllocationBatch.setCustmCount(custmSize);
            qkCustmAllocationBatchService.save(qkCustmAllocationBatch);
            int index = 0;
            List<QkGroupVO> groupVOList = qkGroupService.queryList();
            index = allocation(groupVOList, qkCustmInfos, qkCustmAllocationBatch.getId(), index, true);
            if (index != qkCustmInfos.size()) {
                do {
                    index = allocation(groupVOList, qkCustmInfos, qkCustmAllocationBatch.getId(), index, false);
                } while (index != qkCustmInfos.size());
            }
            saveBatch(qkCustmInfos);
        } finally {
            allocationCache = false;
        }
    }

    /**
     * 分配记录查询
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationLogVO>
     */
    @Override
    public List<QkCustmAllocationLogVO> queryAllocationLogList() {
        List<QkCustmAllocationBatch> allocationBatchList = qkCustmAllocationBatchService.list();
        return allocationBatchList.stream().map(item -> BeanUtil.copyProperties(item, QkCustmAllocationLogVO.class)).collect(Collectors.toList());
    }

    /**
     * 分配记录详情
     *
     * @param id 批次ID
     * @return com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationVO
     */
    @Override
    public QkCustmAllocationVO queryAllocationLogDetail(Integer id) {
        QkCustmAllocationBatch custmAllocationBatch = qkCustmAllocationBatchService.getById(id);
        QkCustmAllocationVO qkCustmAllocationVO = BeanUtil.copyProperties(custmAllocationBatch, QkCustmAllocationVO.class);
        List<QkCustmInfo> custmInfoList = lambdaQuery().eq(QkCustmInfo::getBatchId, id).list();

        return null;
    }

    private int allocation(List<QkGroupVO> groupVOList, List<QkCustmInfo> qkCustmInfos, int batchId, int index, boolean first) {
        for (QkGroupVO qkGroupVO : groupVOList) {
            if (!first && Boolean.FALSE.equals(qkGroupVO.getAlloLoop())) {
                continue;
            }
            List<QkUserVO> userList = qkGroupVO.getUserList();
            if (CollUtil.isEmpty(userList)) {
                continue;
            }
            Integer custmLimit = qkGroupVO.getCustmLimit();
            for (QkUserVO qkUserVO : userList) {
                if (index == qkCustmInfos.size()) {
                    return index;
                }
                int userCustmCount = 0;
                for (int i = 0; i < custmLimit; i++) {
                    if (index == qkCustmInfos.size()) {
                        return index;
                    }
                    if (userCustmCount == custmLimit) {
                        break;
                    }
                    QkCustmInfo qkCustmInfo = qkCustmInfos.get(index);
                    List<QkFollowerDTO> followers = qkCustmInfo.getFollowers();
                    if (CollUtil.isEmpty(followers)) {
                        continue;
                    }
                    QkFollowerDTO qkFollowerDTO = new QkFollowerDTO();
                    qkFollowerDTO.setPid(qkUserVO.getUserid());
                    qkFollowerDTO.setStartTime(DateUtil.date());
                    followers.add(qkFollowerDTO);
                    // 仟客修改客户跟进人
                    qkCustomerService.updateCustmFollowerId(qkCustmInfo.getCustmid(), followers.get(0).getPid(), followers);

                    // 本地修改客户跟进人
                    qkCustmInfo.setFollowerId(qkUserVO.getUserid());
                    qkCustmInfo.setGroupId(qkUserVO.getGroupId());
                    qkCustmInfo.setBatchId(batchId);
                    index++;
                    userCustmCount++;
                }
            }
        }
        return index;
    }

}
