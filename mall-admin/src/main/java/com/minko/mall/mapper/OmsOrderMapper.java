package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.minko.mall.dto.OmsOrderDetail;
import com.minko.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    OmsOrderDetail getInfo(@Param("id") Long id);
}
