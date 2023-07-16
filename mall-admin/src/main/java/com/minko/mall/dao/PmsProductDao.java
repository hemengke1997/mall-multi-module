package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PmsProductDao extends BaseMapper<PmsProduct> {
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
