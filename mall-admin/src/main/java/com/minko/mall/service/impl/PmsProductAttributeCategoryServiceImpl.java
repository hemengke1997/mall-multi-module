package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import com.minko.mall.mapper.PmsProductAttributeCategoryMapper;
import com.minko.mall.model.PmsProductAttributeCategory;
import com.minko.mall.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryMapper, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;

    @Override
    public List<PmsProductAttributeCategoryDto> getListWithAttr() {
        return pmsProductAttributeCategoryMapper.getListWithAttr();
    }

    @Override
    public Page<PmsProductAttributeCategory> selectPage(Page<PmsProductAttributeCategory> page) {
        Page<PmsProductAttributeCategory> selectPage = pmsProductAttributeCategoryMapper.selectPage(page, null);
        return selectPage;
    }
}
