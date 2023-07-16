package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ums_resource")
public class UmsResource {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("资源url")
    private String url;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("资源分类ID")
    private Long categoryId;

    private static final long serialVersionUID = 1L;
}
