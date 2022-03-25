package com.kaxin.qkcustomermanage.service;

import com.alibaba.fastjson.JSONObject;
import com.kaxin.qkcustomermanage.entity.DTO.QkFollowerDTO;

import java.util.List;

/**
 * 功能描述
 *
 * @author tangdj
 * @date 2022/3/24
 */
public interface QkCustomerService {

    /**
     * 查询部门信息
     *
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject queryDeptInfo();

    /**
     * 查询部门员工信息
     *
     * @param deptId 部门ID
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject queryDeptEmpInfo(Integer deptId);

    /**
     * 查询客户信息
     *
     * @param start start
     * @param count 数量
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject queryCustmInfoList(int start, int count);


    /**
     * 修改客户跟进人
     *
     * @param custmId   客户ID
     * @param pid       跟进人ID（修改人）
     * @param followers 跟进人列表
     * @return void
     */
    void updateCustmFollowerId(Integer custmId, Integer pid, List<QkFollowerDTO> followers);
}
