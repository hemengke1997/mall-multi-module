package com.minko.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minko.mall.bo.UmsAdminUserDetails;
import com.minko.mall.dao.OmsOrderDao;
import com.minko.mall.dao.OmsOrderOperateHistoryDao;
import com.minko.mall.dto.OmsOrderDeliveryParam;
import com.minko.mall.dto.OmsOrderDetail;
import com.minko.mall.dto.OmsOrderQueryParam;
import com.minko.mall.dto.OmsReceiverInfoParam;
import com.minko.mall.mapper.OmsOrderOperateHistoryMapper;
import com.minko.mall.model.OmsOrder;
import com.minko.mall.model.OmsOrderOperateHistory;
import com.minko.mall.service.OmsOrderService;
import com.minko.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrder> implements OmsOrderService {
    @Autowired
    private OmsOrderDao orderDao;
    @Autowired
    private OmsOrderOperateHistoryMapper omsOrderOperateHistoryMapper;
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private OmsOrderOperateHistoryDao orderOperateHistoryDao;

    @Override
    public Page<OmsOrder> selectPage(Page<OmsOrder> orderPage, OmsOrderQueryParam omsOrderQueryParam) {
        LambdaQueryWrapper<OmsOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OmsOrder::getDeleteStatus, 0);
        if (StrUtil.isNotEmpty(omsOrderQueryParam.getOrderSn())) {
            lambdaQueryWrapper.eq(OmsOrder::getOrderSn, omsOrderQueryParam.getOrderSn());
        }
        if (omsOrderQueryParam.getStatus() != null) {
            lambdaQueryWrapper.eq(OmsOrder::getStatus, omsOrderQueryParam.getStatus());
        }
        if (omsOrderQueryParam.getSourceType() != null) {
            lambdaQueryWrapper.eq(OmsOrder::getSourceType, omsOrderQueryParam.getSourceType());
        }
        if (omsOrderQueryParam.getOrderType() != null) {
            lambdaQueryWrapper.eq(OmsOrder::getOrderType, omsOrderQueryParam.getOrderType());
        }
        if (StrUtil.isNotEmpty(omsOrderQueryParam.getCreateTime())) {
            lambdaQueryWrapper.likeRight(OmsOrder::getCreateTime, omsOrderQueryParam.getCreateTime());
        }
        if (StrUtil.isNotEmpty(omsOrderQueryParam.getReceiverKeyword())) {
            lambdaQueryWrapper.like(OmsOrder::getReceiverName, omsOrderQueryParam.getReceiverKeyword())
                    .or()
                    .like(OmsOrder::getReceiverPhone, omsOrderQueryParam.getReceiverKeyword());
        }
        return orderDao.selectPage(orderPage, lambdaQueryWrapper);
    }

    @Override
    public OmsOrderDetail getInfo(Long id) {
        return orderDao.getInfo(id);
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderDao.updateById(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        UmsAdminUserDetails userDetails = (UmsAdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        history.setOperateMan(userDetails.getUsername());
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        omsOrderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        LambdaQueryWrapper<OmsOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(OmsOrder::getId, ids);
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setDeleteStatus(1);
        int update = orderDao.update(omsOrder, lambdaQueryWrapper);
        return update;
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderDao.updateById(order);
        // 插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        UmsAdminUserDetails userDetails = (UmsAdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        history.setOperateMan(userDetails.getUsername());
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        omsOrderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        //批量发货
        int count = orderDao.delivery(deliveryParamList);
        //添加操作记录
        List<OmsOrderOperateHistory> operateHistoryList = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(operateHistoryList);
        return count;
    }
}
