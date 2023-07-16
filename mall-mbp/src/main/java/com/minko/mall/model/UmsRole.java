package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ums_role")
public class UmsRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("后台用户数量")
    private Integer adminCount;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("启用状态: 0 -> 禁用; 1 -> 启用")
    private Integer status;
    private Integer sort;

    private static final long serialVersionUID = 1L;

}
