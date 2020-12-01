package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/26 下午5:46
 */
public interface OrderSettingDao {
    /**
     * 通过日期检查预约设置表
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新最大预约数
     * @param orderSetting
     */
    void updateNumber(OrderSetting orderSetting);

    /**
     * 添加预约设置
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 根据月份查询预约设置
     * @param month
     * @return
     */
    List<Map<String, Integer>> getDataByMonth(String month);

    /**
     * 更新以预约人数
     * @param orderSetting
     * @return
     */
    int editReservationsByOrderDate(OrderSetting orderSetting);
}
