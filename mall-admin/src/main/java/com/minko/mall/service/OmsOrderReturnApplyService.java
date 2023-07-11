package com.minko.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.OmsReturnApplyQueryParam;
import com.minko.mall.dto.OmsUpdateStatusParam;
import com.minko.mall.model.OmsOrderReturnApply;

public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {
    Page<OmsOrderReturnApply> selectPage(IPage<OmsOrderReturnApply> page, OmsReturnApplyQueryParam queryParam);

    int updateStatus(Long id, OmsUpdateStatusParam param);
}
