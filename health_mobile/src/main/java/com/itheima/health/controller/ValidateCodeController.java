package com.itheima.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisMessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午5:11
 */

/**
 * 发送验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    //引入redis中的JedisPool连接池
    @Autowired
    private JedisPool jedisPool;

    /**
     * 体检预约
     * @param(参数类型) telephone
     * @return
     */
    @PostMapping("/send4Order")
    public Result send40rder(String telephone){
        Jedis jedis = jedisPool.getResource();
        //判断是否发送过验证码，验证码储存redis中，在redis中查看
        //redis中的key要带上业务标识
        String key = RedisMessageConstant.SENDTYPE_ORDER+"."+telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);
        log.debug("Redis中的验证码：{},{}",codeInRedis,telephone);
        if (!StringUtils.isEmpty(codeInRedis)) {
            //如果有值，发送过了
            return new Result(false,"验证码已经发送过了，请注意查收");
        }
        //如果没有发送到，生成验证码
        String code = String .valueOf(ValidateCodeUtils.generateValidateCode(6));
        //调用SMSUtils发送
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("发送验证码失败");
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        log.debug("验证码发送成功:{},{}",code,telephone);
        //验证码存入redis,同时要设置有效期，10分钟
        jedis.setex(key,10*60,code);
        jedis.close();
        //返回结果页面
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 快速登录 验证码
     * @param telephone
     * @return
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
      Jedis jedis = jedisPool.getResource();
        //判断是否发送过了。从redis中取
        //redis中的key要带上业务标识
        String key = RedisMessageConstant.SENDTYPE_LOGIN+":"+telephone;
        //redis中的验证码
        String codeInRedis = jedis.get(key);
        log.debug("Redis中的验证码:{},{}",codeInRedis,telephone);
        if (!StringUtils.isEmpty(codeInRedis)) {
            //如果有值，发送过了
            return  new Result(false,"验证码已经发送过了，请注意查收!");
        }
         //没有发送，生成验证码
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
        //调用SMSUtils发送
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("发送验证码失败",e);
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        log.debug("验证码发送成功:{},{}",code,telephone);
        //验证码存入redis,同时要设置有效期，10分钟
        jedis.setex(key,10*60,code);
        jedis.close();
        //返回页面结果
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
























