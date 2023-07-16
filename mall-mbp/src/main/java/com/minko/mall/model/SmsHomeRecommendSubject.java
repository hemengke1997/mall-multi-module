package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 首页专题推荐表
 * 用于管理首页显示的专题精选信息
 */
@Data
public class SmsHomeRecommendSubject implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long subjectId;

    private String subjectName;

    private Integer recommendStatus;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}
