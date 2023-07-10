package com.minko.mall.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minko.mall.dto.OmsOrderDetail;
import com.minko.mall.dto.OmsOrderQueryParam;
import com.minko.mall.dto.OmsReceiverInfoParam;
import com.minko.mall.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsOrderService extends IService<OmsOrder> {
    Page<OmsOrder> selectPage(Page<OmsOrder> orderPage, OmsOrderQueryParam omsOrderQueryParam);

    OmsOrderDetail getInfo(Long id);

    @Transactional
    int updateNote(Long id, String note, Integer status);

    int delete(List<Long> ids);

    /**
     * 修改订单信息
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);
}
