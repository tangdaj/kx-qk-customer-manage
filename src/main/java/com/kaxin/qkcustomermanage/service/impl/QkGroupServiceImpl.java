package com.kaxin.qkcustomermanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.kaxin.qkcustomermanage.entity.DTO.QkGroupDTO;
import com.kaxin.qkcustomermanage.entity.PO.QkGroup;
import com.kaxin.qkcustomermanage.entity.PO.QkRule;
import com.kaxin.qkcustomermanage.entity.PO.QkUser;
import com.kaxin.qkcustomermanage.entity.VO.QkGroupVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
import com.kaxin.qkcustomermanage.exception.KaXinException;
import com.kaxin.qkcustomermanage.mapper.QkGroupMapper;
import com.kaxin.qkcustomermanage.service.QkGroupService;
import com.kaxin.qkcustomermanage.service.QkRuleService;
import com.kaxin.qkcustomermanage.service.QkUserService;
import com.kaxin.qkcustomermanage.servlet.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
@Service
public class QkGroupServiceImpl extends BaseServiceImpl<QkGroupMapper, QkGroup> implements QkGroupService {

    @Autowired
    private QkUserService qkUserService;
    @Autowired
    private QkRuleService qkRuleService;

    /**
     * 查询分组列表
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkGroupVO>
     */
    @Override
    public List<QkGroupVO> queryList() {
        List<QkGroupVO> groupVOList = getBaseMapper().queryList();
        for (QkGroupVO qkGroupVO : groupVOList) {
            Integer groupId = qkGroupVO.getId();
            List<QkUser> qkUsers = qkUserService.lambdaQuery().eq(QkUser::getGroupId, groupId).list();
            qkGroupVO.setUserList(qkUsers.stream().map(item -> BeanUtil.copyProperties(item, QkUserVO.class)).collect(Collectors.toList()));
        }
        return groupVOList;
    }

    /**
     * 新增分组
     *
     * @param dto dto
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGroup(QkGroupDTO dto) {
        dto.setId(null);
        QkGroup byName = lambdaQuery().eq(QkGroup::getName, dto.getName()).one();
        if (byName != null) {
            throw new KaXinException(String.format("分组[%s]已存在", byName.getName()));
        }
        QkRule qkRule = BeanUtil.copyProperties(dto, QkRule.class);
        qkRuleService.save(qkRule);
        QkGroup qkGroup = BeanUtil.copyProperties(dto, QkGroup.class);
        qkGroup.setRuleId(qkRule.getId());
        save(qkGroup);
        allocationGroup(dto.getUserIds(), qkGroup.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroup(QkGroupDTO dto) {
        QkGroup qkGroup = getById(dto.getId());
        if (qkGroup == null) {
            throw new KaXinException("分组不存在");
        }
        QkGroup byName = lambdaQuery().eq(QkGroup::getName, dto.getName()).one();
        if (byName != null && ObjectUtil.notEqual(byName.getId(), dto.getId())) {
            throw new KaXinException(String.format("分组[%s]已存在", byName.getName()));
        }
        BeanUtil.copyProperties(dto, qkGroup, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        updateById(qkGroup);
        QkRule qkRule = qkRuleService.getById(qkGroup.getRuleId());
        BeanUtil.copyProperties(dto, qkRule, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        qkRuleService.updateById(qkRule);
    }

    /**
     * 分配分组成员
     *
     * @param userIds 用户列表
     * @param groupId 分组ID
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void allocationGroup(List<Integer> userIds, Integer groupId) {
        List<QkUser> updateUserList = new ArrayList<>();
        for (Integer userId : userIds) {
            QkUser qkUser = qkUserService.getById(userId);
            Integer oldGroupId = qkUser.getGroupId();
            if (oldGroupId != 0 && ObjectUtil.notEqual(oldGroupId, groupId)) {
                QkGroup oldGroup = getById(oldGroupId);
                throw new KaXinException(String.format("员工[%s]已存在于分组[%s]", qkUser.getName(), oldGroup.getName()));
            }
            qkUser.setGroupId(groupId);
            updateUserList.add(qkUser);
        }
        List<QkUser> userList = qkUserService.lambdaQuery().eq(QkUser::getGroupId, groupId).list();
        for (QkUser qkUser : userList) {
            if (!userIds.contains(qkUser.getUserid())) {
                qkUser.setGroupId(0);
                updateUserList.add(qkUser);
            }
        }
        qkUserService.updateBatchById(updateUserList);
    }

    /**
     * 删除分组
     *
     * @param groupId 分组ID
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(Integer groupId) {
        removeById(groupId);
        List<QkUser> userList = qkUserService.lambdaQuery().eq(QkUser::getGroupId, groupId).list();
        for (QkUser qkUser : userList) {
            qkUser.setGroupId(0);
        }
        if (CollUtil.isNotEmpty(userList)) {
            qkUserService.updateBatchById(userList);
        }
    }
}
