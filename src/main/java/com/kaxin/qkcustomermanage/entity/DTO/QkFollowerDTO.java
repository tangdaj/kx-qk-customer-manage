package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 仟客客户跟进人
 *
 * @author tangdj
 * @date 2022/3/18
 */
@Data
public class QkFollowerDTO {

    @ApiModelProperty("跟进人ID")
    private Integer pid;

    @ApiModelProperty("开始时间")
    private Date startTime;
}
