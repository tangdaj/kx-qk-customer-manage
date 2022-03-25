package com.kaxin.qkcustomermanage.controller;


import com.kaxin.qkcustomermanage.entity.DTO.QkCustmAllocationBatchDTO;
import com.kaxin.qkcustomermanage.entity.RespBean;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationLogVO;
import com.kaxin.qkcustomermanage.entity.VO.QkCustmAllocationVO;
import com.kaxin.qkcustomermanage.entity.VO.QkDeptVO;
import com.kaxin.qkcustomermanage.service.QkCustmInfoService;
import com.kaxin.qkcustomermanage.service.QkUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
@RestController
@RequestMapping("/qkCustomer")
@Api(tags = "客户管理")
public class QkCustomerController {

    @Autowired
    private QkCustmInfoService qkCustmInfoService;

    @ApiOperation("分配客户")
    @PostMapping("/allocation")
    public RespBean<Integer> allocation() {
        qkCustmInfoService.allocation();
        return RespBean.ok();
    }

    @ApiOperation("分配记录查询")
    @PostMapping("/allocation/log/list")
    public RespBean<List<QkCustmAllocationLogVO>> allocationLogList() {
        List<QkCustmAllocationLogVO> custmAllocationLogVOList = qkCustmInfoService.queryAllocationLogList();
        return RespBean.ok(custmAllocationLogVOList);
    }

    @ApiOperation("分配记录详情")
    @PostMapping("/allocation/log/detail")
    public RespBean<QkCustmAllocationVO> allocationLogList(@RequestBody QkCustmAllocationBatchDTO dto) {
        QkCustmAllocationVO qkCustmAllocationVO = qkCustmInfoService.queryAllocationLogDetail(dto.getId());
        return RespBean.ok(qkCustmAllocationVO);
    }
}

