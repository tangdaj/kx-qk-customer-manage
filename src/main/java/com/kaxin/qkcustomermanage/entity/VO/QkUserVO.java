package com.kaxin.qkcustomermanage.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门信息
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkUserVO {

    @ApiModelProperty("用户ID")
    private Integer userid;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户别名")
    private String alias;

    @ApiModelProperty(value = "分组ID", hidden = true)
    private Integer groupId;

}
