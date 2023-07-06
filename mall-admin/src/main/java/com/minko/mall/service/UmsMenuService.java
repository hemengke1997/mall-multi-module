package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.UmsMenuNode;
import com.minko.mall.model.UmsMenu;

import java.util.List;

public interface UmsMenuService extends IService<UmsMenu> {
    Page<UmsMenu> page(Page<UmsMenu> page, Long parentId);

    List<UmsMenuNode> treeList();

    int updateHidden(Long id, Integer hidden);

}
