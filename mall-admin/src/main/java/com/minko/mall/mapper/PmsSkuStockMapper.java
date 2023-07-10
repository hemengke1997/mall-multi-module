package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsSkuStockMapper extends BaseMapper<PmsSkuStock> {
    int replaceList(@Param("list") List<PmsSkuStock> skuStockList);
}
