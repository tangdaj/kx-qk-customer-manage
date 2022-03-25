package com.kaxin.qkcustomermanage.service;

import com.kaxin.qkcustomermanage.entity.PO.QkUser;
import com.kaxin.qkcustomermanage.entity.VO.QkDeptVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
import com.kaxin.qkcustomermanage.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
public interface QkUserService extends BaseService<QkUser> {

    /**
     * 查询用户列表（部门树）
     * @param refresh 刷新标识
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkDeptVO>
     */
    List<QkDeptVO> queryListByDept(Boolean refresh);

    /**
     * 查询用户列表
     * @param refresh 刷新标识
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkUserVO>
     */
    List<QkUserVO> queryList(Boolean refresh);
}
