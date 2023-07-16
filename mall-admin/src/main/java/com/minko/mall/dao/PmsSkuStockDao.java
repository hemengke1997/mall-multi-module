package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PmsSkuStockDao extends BaseMapper<PmsSkuStock> {
    int replaceList(@Param("list") List<PmsSkuStock> skuStockList);
}
