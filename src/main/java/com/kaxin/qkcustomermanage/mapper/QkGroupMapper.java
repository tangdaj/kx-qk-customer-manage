package com.kaxin.qkcustomermanage.mapper;

import com.kaxin.qkcustomermanage.entity.PO.QkGroup;
import com.kaxin.qkcustomermanage.entity.VO.QkGroupVO;
import com.kaxin.qkcustomermanage.servlet.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
public interface QkGroupMapper extends BaseMapper<QkGroup> {

    /**
     * 查询分组列表
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkGroupVO>
     */
    List<QkGroupVO> queryList();
}
