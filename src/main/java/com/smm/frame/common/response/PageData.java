package com.smm.frame.common.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Rao
 * @Date 2021/2/24
 **/
@Data
public class PageData implements Serializable {
    private static final long serialVersionUID = -1461066178400899365L;

    @ApiModelProperty("页号")
    private Integer pageNo;

    @ApiModelProperty("页数")
    private Integer pageSize;

    @ApiModelProperty("总数")
    private Long total;

}
