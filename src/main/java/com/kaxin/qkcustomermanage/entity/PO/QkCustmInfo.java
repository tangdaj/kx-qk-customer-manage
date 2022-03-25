package com.kaxin.qkcustomermanage.entity.PO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kaxin.qkcustomermanage.entity.DTO.QkFollowerDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
@TableName("kx_qk_custm_info")
@ApiModel(value = "QkCustmInfo对象", description = "")
public class QkCustmInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "客户id")
    private Integer custmid;

    @ApiModelProperty(value = "客户名称")
    private String name;

    @ApiModelProperty(value = "客户地址")
    private String addr;

    @ApiModelProperty(value = "标记")
    private String note;

    @ApiModelProperty(value = "跟进人id")
    private Integer followerId = 0;

    @ApiModelProperty(value = "分组id")
    private Integer groupId = 0;

    @ApiModelProperty(value = "批次id")
    private Integer batchId = 0;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "跟进人")
    @TableField(exist = false)
    private List<QkFollowerDTO> followers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QkCustmInfo that = (QkCustmInfo) o;
        return custmid.equals(that.custmid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(custmid);
    }
}
