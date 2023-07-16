package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品分类和商品属性的关系表
 */
@Data
public class PmsProductCategoryAttributeRelation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productCategoryId;

    private Long productAttributeId;

    private static final long serialVersionUID = 1L;
}
