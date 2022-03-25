package com.kaxin.qkcustomermanage.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class QkCustmAllocationVO {

    @ApiModelProperty("批次ID")
    private Integer batchId;

    @ApiModelProperty("分配客户数量")
    private Integer custmCount;

    @ApiModelProperty("分配时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("用户ID")
    private Integer userid;

    @ApiModelProperty("用户姓名")
    private String name;

    @ApiModelProperty("用户别名")
    private String alias;

    @ApiModelProperty(value = "分组名称")
    private Integer groupName;

    @ApiModelProperty(value = "分组名称")
    private Integer custmLimit;

    @ApiModelProperty(value = "循环分配")
    private Boolean alloLoop;

    @ApiModelProperty(value = "客户信息")
    private List<QkCustmInfoVO> custmInfoList;

}
