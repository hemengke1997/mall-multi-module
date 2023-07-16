package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.model.OmsOrder;
import com.minko.mall.dto.OmsOrderDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OmsOrderDao extends BaseMapper<OmsOrder> {
    OmsOrderDetail getInfo(@Param("id") Long id);
}
