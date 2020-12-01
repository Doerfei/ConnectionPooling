package com.itheima.health.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import com.itheima.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午8:55
 */
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交预约
     *
     * @param paraMap
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> paraMap){
        // ================== 验证码校验 ======================
        Jedis jedis = jedisPool.getResource();
        //- 获取手机号码
        String telephone = paraMap.get("telephone");
        //- 拼接redis的key
        String key = RedisMessageConstant.SENDTYPE_ORDER+":"+telephone;
        //- 再从redis取出验证码
        String codeInRedis = jedis.get(key);
        //  - 没值，用户可能没有点击获取验证，验证码过程，返回 重新获取验证
        if(StringUtils.isEmpty(codeInRedis)){
            return new Result(false, "请重新获取验证码!");
        }
        //- 校验前端提交的验证码与redis中的验证码是否一致
        if(!codeInRedis.equals(paraMap.get("validateCode"))) {
            //- 不一致，返回 验证码错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //- 一致： 删除验证码key，
        jedis.del(key);
        jedis.close();
        // 设置预约类型 health_mobile微信预约
        paraMap.put("orderType", Order.ORDERTYPE_WEIXIN);
        // 调用服务进行预约
        Integer id = orderService.submit(paraMap);
        return new Result(true, MessageConstant.ORDER_SUCCESS,id);
    }

    /**
     * 订单详情
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
      //调用服务查询
      Map<String,String> orderInfo = orderService.findOrderDetailById(id);
      return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderInfo);
    }
}
