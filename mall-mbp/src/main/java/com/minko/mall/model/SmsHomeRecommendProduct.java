package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 人气推荐商品表
 * 用于管理首页显示的人气推荐信息
 */
@Data
public class SmsHomeRecommendProduct implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String productName;

    private Integer recommendStatus;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}
