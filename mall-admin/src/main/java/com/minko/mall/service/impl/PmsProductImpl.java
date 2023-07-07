package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductParam;
import com.minko.mall.mapper.PmsProductMapper;
import com.minko.mall.model.PmsProduct;
import com.minko.mall.service.PmsProductService;
import org.springframework.stereotype.Service;

@Service
public class PmsProductImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Override
    public int create(PmsProductParam productParam) {
        return 0;
    }
}
