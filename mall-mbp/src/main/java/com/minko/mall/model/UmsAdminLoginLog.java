package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ums_admin_login_log")
public class UmsAdminLoginLog implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    private long adminId;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("ip")
    private String ip;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("浏览器登录类型")
    private String userAgent;

    private static final long serialVersionUID = 1L;
}
