package com.minko.mall.common.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CPage<E> {
    /**
     * page
     */
    private Object pageInfo;
    /**
     * 当前页码
     */
    private Long pageNum;
    /**
     * 每页数量
     */
    private Long pageSize;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 分页数据
     */
    private List<E> list;

    /**
     * 总条数
     */
    private Long total;

    /**
     * mybatis-plus 分页结果
     */
    public static <E> CPage<E> restPage(Page<E> pageInfo) {
        CPage<E> result = new CPage<>();
        result.setPageNum(pageInfo.getCurrent());
        result.setPageSize(pageInfo.getSize());
        result.setList(pageInfo.getRecords());
        result.setTotal(pageInfo.getTotal());
        result.setTotalPage(pageInfo.getPages());
        result.setPageInfo(pageInfo);
        return result;
    }

    /**
     * springData分页结果
     */
    public static <E> CPage<E> restPage(org.springframework.data.domain.Page<E> pageInfo) {
        CPage<E> result = new CPage<>();
        result.setPageNum((long) pageInfo.getNumber());
        result.setPageSize((long) pageInfo.getSize());
        result.setList(pageInfo.getContent());
        result.setTotal(pageInfo.getTotalElements());
        result.setTotalPage((long) pageInfo.getTotalPages());
        result.setPageInfo(pageInfo);
        return result;
    }
}
