package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分组
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkGroupRuleDTO {

    @ApiModelProperty("分组ID")
    private Integer id;

    @ApiModelProperty("客户上限")
    private Integer custmLimit;

    @ApiModelProperty("循环分配")
    private Boolean alloLoop;
}
