package com.kaxin.qkcustomermanage.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kaxin.qkcustomermanage.entity.PO.QkCustmInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 部门信息
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkCustmAllocationLogVO {

    @ApiModelProperty("批次ID")
    private Integer batchId;

    @ApiModelProperty("分配客户数量")
    private Integer custmCount;

    @ApiModelProperty("分配时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
