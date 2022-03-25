package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仟客客户接口控制
 *
 * @author tangdj
 * @date 2022/3/18
 */
@Data
public class QkCustctrolDTO {


    @ApiModelProperty("跟进人")
    private Boolean followers = true;


}
