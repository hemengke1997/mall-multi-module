package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 收货地址
 */
@Data
public class OmsCompanyAddress implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "地址名称")
    private String addressName;

    @ApiModelProperty(value = "默认发货地址：0->否；1->是")
    private Integer sendStatus;

    @ApiModelProperty(value = "是否默认收货地址：0->否；1->是")
    private Integer receiveStatus;

    @ApiModelProperty(value = "收发货人姓名")
    private String name;

    @ApiModelProperty(value = "收货人电话")
    private String phone;

    @ApiModelProperty(value = "省/直辖市")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String region;

    @ApiModelProperty(value = "详细地址")
    private String detailAddress;

    private static final long serialVersionUID = 1L;
}
