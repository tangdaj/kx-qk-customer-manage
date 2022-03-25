package com.kaxin.qkcustomermanage.entity.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 分组信息
 *
 * @author tangdj
 * @date 2022/3/24
 */
@Data
public class QkGroupVO {

    @ApiModelProperty("分组ID")
    private Integer id;

    @ApiModelProperty("分组名称")
    private String name;

    @ApiModelProperty("规则ID")
    private Integer ruleId;

    @ApiModelProperty(value = "客户分配上限")
    private Integer custmLimit;

    @ApiModelProperty(value = "是否循环分配")
    private Boolean alloLoop;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "员工列表")
    private List<QkUserVO> userList = Collections.emptyList();


}
