package com.itheima.health.dao;

import com.itheima.health.pojo.User;

/**
 * @Author DoerFei
 * @Date 2020/11/29 下午5:02
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息 用户下的角色。角色下的权限 5表关联查询 1：多关系映射
     * @param username
     * @return
     */
    User findByUsername(String username);
}
