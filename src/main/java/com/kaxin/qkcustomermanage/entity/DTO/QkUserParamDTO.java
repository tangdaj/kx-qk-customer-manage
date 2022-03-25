package com.kaxin.qkcustomermanage.entity.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkUserParamDTO {

    @ApiModelProperty("刷新")
    private Boolean refresh = false;
}
