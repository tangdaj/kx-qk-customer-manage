package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 仟客客户
 *
 * @author tangdj
 * @date 2022/3/18
 */
@Data
public class QkCustomerInfoDTO {

    @ApiModelProperty("客户ID")
    private Integer custmid;

    @ApiModelProperty("跟进人列表")
    private List<QkFollowerDTO> followers;
}
