package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品满减
 */
@Data
public class PmsProductFullReduction implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;

    private static final long serialVersionUID = 1L;

}