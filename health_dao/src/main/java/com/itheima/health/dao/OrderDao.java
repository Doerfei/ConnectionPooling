package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午5:50
 */
public interface OrderDao {
    /**
     * 添加
     * @param order
     */
    void add(Order order);

    /**
     * 条件查询
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);

    // 报表相关
    Integer findOrderCountByDate(String date);
    Integer findOrderCountAfterDate(String date);
    Integer findVisitsCountByDate(String date);
    Integer findVisitsCountAfterDate(String date);
    List<Map> findHotSetmeal();
}
