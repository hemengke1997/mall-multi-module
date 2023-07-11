package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页品牌推荐表
 * 用于管理首页显示的品牌制造商直供信息
 */
@Data
public class SmsHomeBrand implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long brandId;

    private String brandName;

    private Integer recommendStatus;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}
