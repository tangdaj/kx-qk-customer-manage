package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分组
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkGroupDTO {

    @ApiModelProperty("分组ID")
    private Integer id;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("客户上限")
    private Integer custmLimit;

    @ApiModelProperty("循环分配")
    private Boolean alloLoop;

    @ApiModelProperty("员工ID列表")
    private List<Integer> userIds;
}
