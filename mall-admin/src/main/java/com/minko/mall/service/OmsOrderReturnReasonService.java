package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.model.OmsOrderReturnReason;

import java.util.List;

public interface OmsOrderReturnReasonService extends IService<OmsOrderReturnReason> {
    Page<OmsOrderReturnReason> selectPage(Page<OmsOrderReturnReason> page);

    int updateStatus(List<Long> ids, Integer status);

    int delete(List<Long> ids);
}
