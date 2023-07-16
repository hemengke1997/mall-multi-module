package com.minko.mall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CmsSubjectProductRelation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long subjectId;

    private Long productId;

    private static final long serialVersionUID = 1L;
}