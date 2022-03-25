package com.kaxin.qkcustomermanage.constant.enums;

/**
 * @author tangdj
 * 默认的状态枚举
 */

public enum BaseStatusEnum {
    //开启
    OPEN(1),
    //关闭
    CLOSE(0);

    private Integer status;

    BaseStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }


}
