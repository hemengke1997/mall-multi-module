package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 新品推荐商品表
 * 用于管理首页显示的新鲜好物信息
 */
@Data
public class SmsHomeNewProduct implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String productName;

    private Integer recommendStatus;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}
