package com.minko.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.model.PmsProductAttributeCategory;
import com.minko.mall.dao.PmsProductAttributeCategoryDao;
import com.minko.mall.dto.PmsProductAttributeCategoryDto;
import com.minko.mall.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductAttributeCategoryServiceImpl extends ServiceImpl<PmsProductAttributeCategoryDao, PmsProductAttributeCategory> implements PmsProductAttributeCategoryService {
    @Autowired
    private PmsProductAttributeCategoryDao pmsProductAttributeCategoryDao;

    @Override
    public List<PmsProductAttributeCategoryDto> getListWithAttr() {
        return pmsProductAttributeCategoryDao.getListWithAttr();
    }

    @Override
    public Page<PmsProductAttributeCategory> selectPage(Page<PmsProductAttributeCategory> page) {
        Page<PmsProductAttributeCategory> selectPage = pmsProductAttributeCategoryDao.selectPage(page, null);
        return selectPage;
    }
}
