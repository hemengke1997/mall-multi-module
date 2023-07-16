package com.minko.mall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.model.OmsOrderReturnApply;
import com.minko.mall.dto.OmsReturnApplyQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OmsOrderReturnApplyDao extends BaseMapper<OmsOrderReturnApply> {
    Page<OmsOrderReturnApply> getList(IPage<OmsOrderReturnApply> page, @Param("queryParam") OmsReturnApplyQueryParam queryParam);
}
