package com.smm.frame.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Rao
 * @Date 2021/2/24
 **/
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -3225542163835801745L;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除 0-未删  1-已删除")
    private Integer deleted;

}
