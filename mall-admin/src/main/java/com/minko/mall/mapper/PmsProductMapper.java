package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.PmsProductResult;
import com.minko.mall.model.PmsProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
