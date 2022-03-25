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
public class QkGroupParamDTO {

    @ApiModelProperty("员工ID列表")
    private List<Integer> userIds;

    @ApiModelProperty("分组ID")
    private Integer groupId;
}
