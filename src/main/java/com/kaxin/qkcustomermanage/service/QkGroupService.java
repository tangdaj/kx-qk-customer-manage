package com.kaxin.qkcustomermanage.service;

import com.kaxin.qkcustomermanage.entity.DTO.QkGroupDTO;
import com.kaxin.qkcustomermanage.entity.PO.QkGroup;
import com.kaxin.qkcustomermanage.entity.VO.QkGroupVO;
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
public interface QkGroupService extends BaseService<QkGroup> {

    /**
     * 查询分组列表
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkGroupVO>
     */
    List<QkGroupVO> queryList();

    /**
     * 新增分组
     *
     * @param dto dto
     * @return void
     */
    void saveGroup(QkGroupDTO dto);

    /**
     * 修改分组
     *
     * @param dto dto
     * @return void
     */
    void updateGroup(QkGroupDTO dto);

    /**
     * 分配分组成员
     *
     * @param userIds 用户列表
     * @param groupId 分组ID
     * @return void
     */
    void allocationGroup(List<Integer> userIds, Integer groupId);

    /**
     * 删除分组
     *
     * @param groupId 分组ID
     * @return void
     */
    void deleteGroup(Integer groupId);
}
