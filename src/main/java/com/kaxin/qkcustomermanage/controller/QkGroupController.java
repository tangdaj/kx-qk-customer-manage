package com.kaxin.qkcustomermanage.controller;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.kaxin.qkcustomermanage.entity.DTO.QkGroupDTO;
import com.kaxin.qkcustomermanage.entity.DTO.QkGroupParamDTO;
import com.kaxin.qkcustomermanage.entity.RespBean;
import com.kaxin.qkcustomermanage.entity.VO.QkGroupVO;
import com.kaxin.qkcustomermanage.service.QkGroupService;
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
@RequestMapping("/qkGroup")
@Api(tags = "分组管理")
public class QkGroupController {

    @Autowired
    private QkGroupService qkGroupService;

    @ApiOperation("查询分组列表")
    @PostMapping("/list")
    public RespBean<List<QkGroupVO>> queryList() {
        List<QkGroupVO> groupVOList = qkGroupService.queryList();
        return RespBean.ok(groupVOList);
    }

    @ApiOperation("新增分组")
    @PostMapping("/save")
    public RespBean save(@RequestBody QkGroupDTO dto) {
        qkGroupService.saveGroup(dto);
        return RespBean.ok();
    }

    @ApiOperation("修改分组")
    @PostMapping("/update")
    public RespBean update(@RequestBody QkGroupDTO dto) {
        qkGroupService.updateGroup(dto);
        return RespBean.ok();
    }

    @ApiOperation("删除分组")
    @ApiOperationSupport(includeParameters = {"dto.groupId"})
    @PostMapping("/delete")
    public RespBean delete(@RequestBody QkGroupParamDTO dto) {
        qkGroupService.deleteGroup(dto.getGroupId());
        return RespBean.ok();
    }

    @ApiOperation("分配分组成员")
    @PostMapping("/allocation")
    public RespBean allocation(@RequestBody QkGroupParamDTO dto) {
        qkGroupService.allocationGroup(dto.getUserIds(), dto.getGroupId());
        return RespBean.ok();
    }
}

