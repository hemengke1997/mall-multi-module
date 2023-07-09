package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.PmsBrandParam;
import com.minko.mall.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

public interface PmsBrandSerive extends IService<PmsBrand> {
    int create(PmsBrandParam pmsBrand);

    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    Page<PmsBrand> selectPage(Page<PmsBrand> pmsBrandPage, String keyword, Integer showStatus);
}
