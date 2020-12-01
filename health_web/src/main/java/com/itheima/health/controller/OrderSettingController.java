package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author DoerFei
 * @Date 2020/11/25 下午6:12
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    /**
     * 批量导入预约设置
     * @param excelFile
     * @return
     */
    public Result upload(MultipartFile excelFile){
        //接收上传的文件，MultipartFile 参数名excelFile
        //调用poiutils解析excel,list<String[]>
        try {
            List<String[]> orderInfoStringArrList = POIUtils.readExcel(excelFile);
            //再List<String[]>转成List<String[]>
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = orderInfoStringArrList.stream().map(orderInfoStringArr -> {
                OrderSetting os = new OrderSetting();
                String orderDateStr = orderInfoStringArr[0];// 日期的字符串
                try {
                    os.setOrderDate(sdf.parse(orderDateStr));
                } catch (ParseException e) {}
                os.setNumber(Integer.valueOf(orderInfoStringArr[1]));// 最大预约数量
                return os;
            }).collect(Collectors.toList());
            //调用服务导入
            orderSettingService.add(orderSettingList);
        } catch (IOException e) {
            log.error("导入预约设置失败",e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        //返回操作的结果给页面
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 日历展示
     * @param month
     * @return
     */
    @GetMapping("/getDataByMonth")
    public Result getDataByMonth(String month){
        //调用服务查询
        List<Map<String,Integer>> monthData = orderSettingService.getDataByMonth(month);
        return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,monthData);
    }

    /**
     * 基于日历的预约设置
     * @param os
     * @return
     */
    public Result editNumberByData(@RequestBody OrderSetting os){
        //调用服务来更新
        orderSettingService.editNumberByDate(os);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}





































































