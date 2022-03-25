package com.kaxin.qkcustomermanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kaxin.qkcustomermanage.entity.PO.QkDept;
import com.kaxin.qkcustomermanage.entity.PO.QkUser;
import com.kaxin.qkcustomermanage.entity.VO.QkDeptVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
import com.kaxin.qkcustomermanage.mapper.QkUserMapper;
import com.kaxin.qkcustomermanage.service.QkCustomerService;
import com.kaxin.qkcustomermanage.service.QkDeptService;
import com.kaxin.qkcustomermanage.service.QkUserService;
import com.kaxin.qkcustomermanage.servlet.BaseServiceImpl;
import com.kaxin.qkcustomermanage.util.RecursionUtil;
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
public class QkUserServiceImpl extends BaseServiceImpl<QkUserMapper, QkUser> implements QkUserService {

    @Autowired
    private QkCustomerService qkCustomerService;
    @Autowired
    private QkDeptService qkDeptService;
    @Autowired
    private QkUserService qkUserService;

    /**
     * 查询用户列表
     * @param refresh 刷新标识
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkDeptVO>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<QkDeptVO> queryListByDept(Boolean refresh) {
        JSONObject deptInfo = qkCustomerService.queryDeptInfo();
        JSONArray departments = deptInfo.getJSONArray("departments");
        List<QkDeptVO> qkDeptList = departments.toJavaList(QkDeptVO.class);
        List<QkDeptVO> childListTree = RecursionUtil.getChildListTree(qkDeptList, "parentid", 1, "id", "childList", QkDeptVO.class);
        queryDeptEmpList(childListTree, refresh);
        return childListTree;
    }

    /**
     * 查询用户列表
     * @param refresh 刷新标识
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkUserVO>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<QkUserVO> queryList(Boolean refresh) {
        List<QkDeptVO> qkDeptList;
        if (Boolean.TRUE.equals(refresh)) {
            JSONObject deptInfo = qkCustomerService.queryDeptInfo();
            JSONArray departments = deptInfo.getJSONArray("departments");
            qkDeptList = departments.toJavaList(QkDeptVO.class);
        } else {
            qkDeptList = qkDeptService.list().stream().map(item -> BeanUtil.copyProperties(item, QkDeptVO.class)).collect(Collectors.toList());
        }
        List<QkDeptVO> childListTree = RecursionUtil.getChildListTree(qkDeptList, "parentid", 1, "id", "childList", QkDeptVO.class);
        List<QkUser> qkUserList = new ArrayList<>();
        List<QkUser> qkUsers = queryEmpList(childListTree, refresh, qkUserList);
        return qkUsers.stream().map(item -> BeanUtil.copyProperties(item, QkUserVO.class)).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public List<QkUser> queryEmpList(List<QkDeptVO> childListTree, Boolean refresh, List<QkUser> qkUserList) {
        for (QkDeptVO qkDeptVO : childListTree) {
            List<QkDeptVO> childList = qkDeptVO.getChildList();
            if (CollUtil.isNotEmpty(childList)) {
                queryEmpList(childList, refresh, qkUserList);
            } else {
                Integer deptId = qkDeptVO.getId();
                List<QkUserVO> qkUserVOList = queryEmp(refresh, deptId);
                qkDeptVO.setUserList(qkUserVOList);
                List<QkUser> qkUsers = qkUserVOList.stream().map(item -> {
                    QkUser qkUser = BeanUtil.copyProperties(item, QkUser.class);
                    qkUser.setDepartmentId(deptId);
                    return qkUser;
                }).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(qkUsers)) {
                    qkUserList.addAll(qkUsers);
                    if (Boolean.TRUE.equals(refresh)) {
                        saveOrUpdateBatch(qkUsers);
                    }
                }
            }
        }
        return qkUserList;
    }

    private List<QkUserVO> queryEmp(Boolean refresh, Integer deptId) {
        List<QkUserVO> qkUserVOList;
        if (Boolean.TRUE.equals(refresh)) {
            JSONObject deptEmpInfo = qkCustomerService.queryDeptEmpInfo(deptId);
            JSONArray member = deptEmpInfo.getJSONArray("member");
            qkUserVOList = member.toJavaList(QkUserVO.class);
        } else {
            qkUserVOList = qkUserService.lambdaQuery().eq(QkUser::getDepartmentId, deptId).list()
                    .stream().map(item -> BeanUtil.copyProperties(item, QkUserVO.class)).collect(Collectors.toList());
        }
        return qkUserVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void queryDeptEmpList(List<QkDeptVO> childListTree, Boolean refresh) {
        if (Boolean.TRUE.equals(refresh)) {
            List<QkDept> deptList = childListTree.stream().map(item -> BeanUtil.copyProperties(item, QkDept.class)).collect(Collectors.toList());
            qkDeptService.saveOrUpdateBatch(deptList);
        }
        for (QkDeptVO qkDeptVO : childListTree) {
            List<QkDeptVO> childList = qkDeptVO.getChildList();
            if (CollUtil.isNotEmpty(childList)) {
                queryDeptEmpList(childList, refresh);
            } else {
                Integer deptId = qkDeptVO.getId();
                List<QkUserVO> qkUserVOList = queryEmp(refresh, deptId);
                qkDeptVO.setUserList(qkUserVOList);
                if (Boolean.TRUE.equals(refresh)) {
                    List<QkUser> qkUsers = qkUserVOList.stream().map(item -> {
                        QkUser qkUser = BeanUtil.copyProperties(item, QkUser.class);
                        qkUser.setDepartmentId(deptId);
                        return qkUser;
                    }).collect(Collectors.toList());
                    if (CollUtil.isNotEmpty(qkUsers)) {
                        saveOrUpdateBatch(qkUsers);
                    }
                }
            }
        }
    }
}
