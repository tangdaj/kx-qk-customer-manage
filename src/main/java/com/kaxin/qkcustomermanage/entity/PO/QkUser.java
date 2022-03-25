package com.kaxin.qkcustomermanage.entity.PO;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author tangdj
 * @since 2022-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("kx_qk_user")
@ApiModel(value = "QkUser对象", description = "")
public class QkUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户在仟客中的唯一标识")
    @TableId
    private Integer userid;

    @ApiModelProperty(value = "部门ID")
    private Integer departmentId;

    @ApiModelProperty(value = "分组ID")
    private Integer groupId = 0;

    @ApiModelProperty(value = "用户别名")
    private String alias;

    @ApiModelProperty(value = "用户姓名")
    private String name;


}
