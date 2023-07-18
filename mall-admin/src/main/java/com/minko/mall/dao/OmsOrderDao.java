package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.OmsOrderDeliveryParam;
import com.minko.mall.dto.OmsOrderDetail;
import com.minko.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OmsOrderDao extends BaseMapper<OmsOrder> {
    OmsOrderDetail getInfo(@Param("id") Long id);

    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);
}
