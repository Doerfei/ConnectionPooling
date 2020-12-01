package com.itheima.health.security;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author DoerFei
 * @Date 2020/11/29 下午5:13
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    /**
     * 订阅用户服务
     */
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过用户名查询用户信息  用户下的角色，角色下的权限 5表关联查询 1:多关系映射
        User user = userService.findByUsername(username);
        if(null != user){
            // 授权
            // 权限集合
            List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
            // 用户下的角色
            Set<Role> userRoles = user.getRoles();

            if(null != userRoles){
                userRoles.forEach(role -> {
                    // 【注意】这里要使用keyword ROLE_， 当使用hasRole用的角色做权限控制
                    authorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
                    // 角色下的权限 当调用hasAuthority用的权限做控制
                    Set<Permission> permissions = role.getPermissions();
                    if(null != permissions){
                        permissions.forEach(permission -> {
                            authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
                        });
                    }
                });
            }
            // 返回登陆用户的权限信息
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    username,user.getPassword(),authorityList
            );
            return userDetails;
        }
        return null;
    }
}
