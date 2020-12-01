package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.Excpetion.MyException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/26 下午5:44
 */
@Service(interfaceClass = OrderSettingService.class)//添加事务
public class OrderSettingServiceImpl implements OrderSettingService {
   @Autowired
   private OrderSettingDao orderSettingDao;

    /**
     * 批量导入预约设置
     * @param orderSettingList
     */
   @Transactional
   @Override
   public void add(List<OrderSetting> orderSettingList){
       //遍历List<OrderSetting>
       if (null != orderSettingList) {
           for (OrderSetting orderSetting : orderSettingList) {
               //通过日期查询预约设置表
               OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
               //如果预约设置存在
               if (null != osInDB) {
                   //判断更新后的最大数是否等于已预约人数
                   if(orderSetting.getNumber()<osInDB.getReservations()){
                      //小于报错已经约数超过最大预约数，接口异常声明
                      throw new MyException("更新后的最大约数，不能小一预约数");
                   }
                   //大于，则可以更新最大预约数
                   orderSettingDao.add(orderSetting);
               }
               //事务控制
           }
       }
   }

   @Override
   public List<Map<String,Integer>> getDataByMonth(String month){
       month+="%";
       return orderSettingDao.getDataByMonth(month);
   }


    /**
     * 基于日历的预约设置
     * @param orderSetting
     */
    @Override
    public void  editNumberByDate(OrderSetting orderSetting){
       //通过日期查询预约设置表
       OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
       //如果存在预约设置
       if (null != osInDB) {
           //判断更新后的最大数是否大于等于已经预约人数
           if (orderSetting.getNumber()<osInDB.getReservations()) {
               //小于报错，已经预约人数超过最大预约数，接口异常声明
               throw new MyException("更新后的最大预约数，不能小于已经预约数");
           }
           //大于，则可以更新最大预约数
           orderSettingDao.updateNumber(orderSetting);
       }else{
           //不存在，则可以添加预约设置
       }
   }
}













































