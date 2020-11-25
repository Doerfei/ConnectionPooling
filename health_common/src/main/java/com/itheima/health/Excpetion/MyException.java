package com.itheima.health.Excpetion;

public class MyException extends RuntimeException{

    /**
     * 自定义异常3个作用：
     * 1、区分是由系统抛出还是由程序员抛出
     * 2、封装有好的提示
     * 3、终止已经不符合业务逻辑代码的继续执行
     * @param msg
     */

    public MyException(String msg) {
        super(msg);
    }
}
