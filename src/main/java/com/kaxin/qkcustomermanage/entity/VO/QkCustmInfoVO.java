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
public class QkCustmInfoVO {

    @ApiModelProperty(value = "客户id")
    private Integer custmid;

    @ApiModelProperty(value = "客户名称")
    private String name;

    @ApiModelProperty(value = "客户地址")
    private String addr;

    @ApiModelProperty(value = "标记")
    private String note;

    @ApiModelProperty(value = "批次id")
    private Integer batchId;

}
