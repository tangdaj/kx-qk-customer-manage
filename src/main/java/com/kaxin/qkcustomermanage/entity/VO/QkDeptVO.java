package com.kaxin.qkcustomermanage.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 部门信息
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkDeptVO {

    @ApiModelProperty("部门ID")
    private Integer id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("上级部门ID")
    private Integer parentid;

    @ApiModelProperty("子部门")
    private List<QkDeptVO> childList = Collections.emptyList();

    @ApiModelProperty("子部门")
    private List<QkUserVO> userList = Collections.emptyList();
}
