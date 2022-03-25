package com.kaxin.qkcustomermanage.service;

import com.kaxin.qkcustomermanage.entity.PO.QkCustmInfo;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationLogVO;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationVO;
import com.kaxin.qkcustomermanage.servlet.BaseService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
public interface QkCustmInfoService extends BaseService<QkCustmInfo> {

    /**
     * 分配客户
     *
     * @return void
     */
    void allocation();

    /**
     * 分配记录查询
     *
     * @return java.util.List<com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationLogVO>
     */
    List<QkCustmAllocationLogVO> queryAllocationLogList();

    /**
     * 分配记录详情
     *
     * @param id 批次ID
     * @return com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationVO
     */
    QkCustmAllocationVO queryAllocationLogDetail(Integer id);
}
