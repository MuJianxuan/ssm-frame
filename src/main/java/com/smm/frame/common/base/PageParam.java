package com.smm.frame.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rao
 * @Date 2020/12/2
 **/
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = 3802585707404636271L;
    @ApiModelProperty(value="起始页",required = true)
    private int page = 1;

    @ApiModelProperty(value="页容量 init:15",required = false)
    private int pageSize = 15;
}
