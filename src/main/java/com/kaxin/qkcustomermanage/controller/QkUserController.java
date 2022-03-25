package com.kaxin.qkcustomermanage.controller;


import com.kaxin.qkcustomermanage.entity.DTO.QkUserParamDTO;
import com.kaxin.qkcustomermanage.entity.RespBean;
import com.kaxin.qkcustomermanage.entity.VO.QkDeptVO;
import com.kaxin.qkcustomermanage.entity.VO.QkUserVO;
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
@RequestMapping("/qkUser")
@Api(tags = "员工管理")
public class QkUserController {

    @Autowired
    private QkUserService qkUserService;

    @ApiOperation("查询员工列表（部门树）")
    @PostMapping("/list/deptTree")
    public RespBean<List<QkDeptVO>> queryListDeptTree(@RequestBody QkUserParamDTO dto) {
        List<QkDeptVO> deptVOList = qkUserService.queryListByDept(dto.getRefresh());
        return RespBean.ok(deptVOList);
    }

    @ApiOperation("查询员工列表")
    @PostMapping("/list")
    public RespBean<List<QkUserVO>> queryList(@RequestBody QkUserParamDTO dto) {
        List<QkUserVO> userVOList = qkUserService.queryList(dto.getRefresh());
        return RespBean.ok(userVOList);
    }
}

