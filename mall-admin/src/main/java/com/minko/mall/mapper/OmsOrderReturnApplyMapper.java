package com.minko.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minko.mall.dto.OmsReturnApplyQueryParam;
import com.minko.mall.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OmsOrderReturnApplyMapper extends BaseMapper<OmsOrderReturnApply> {
    Page<OmsOrderReturnApply> getList(IPage<OmsOrderReturnApply> page, @Param("queryParam") OmsReturnApplyQueryParam queryParam);
}
