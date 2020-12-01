package com.itheima.health.controller;

import com.itheima.health.Excpetion.MyException;
import com.itheima.health.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author DoerFei
 * @Date 2020/11/26 下午7:42
 */
@RestControllerAdvice
public class HealExceptionAdvice {
    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    /**
     * catch(MyException)
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e){
        return new Result(false,e.getMessage());
    }

    /**
     * catch(MyException)
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        //e.printStackTrace();
        //System.out.print
        log.error("发生未知异常",e);
        return new Result(false,"发生未知异常，请稍后重试");
    }
}
