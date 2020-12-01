package com.itheima.health.service;

import com.itheima.health.Excpetion.MyException;

import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午5:44
 */
public interface OrderService {
    /**
     * 预约提交业务
     * @param paraMap
     * @return
     * @throws MyException
     */
    Integer submit(Map<String,String>paraMap)throws MyException;

    /**
     * 订单详情
     * @param id
     * @return
     */
    Map<String, String>findOrderDetailById(int id);
}
