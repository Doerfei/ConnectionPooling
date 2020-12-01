package com.itheima.health.service;

import com.itheima.health.Excpetion.MyException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/25 下午6:09
 */
public interface OrderSettingService {
    /**
     * 批量导入预约设置
     * @param orderSettingList
     * @throws MyException
     */
    void add(List<OrderSetting> orderSettingList) throws MyException;

    /**
     * 通过月份查询预约设置
     * @param month
     * @return
     */
    List<Map<String, Integer>> getDataByMonth(String month);

    /**
     * 根据日历查询预约设置
     * @param os
     */
    void editNumberByDate(OrderSetting os);
}
