package com.itheima.service;

import com.itheima.health.pojo.Permission;
import com.itheima.health.pojo.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author DoerFei
 * @Date 2020/11/29 下午5:26
 */
public class UserService2 implements UserDetailsService {

    /**
     * UserDetailsService 提供登陆用户信息
     *
     * loadUserByUsername: 通过用户名加载用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=======usernmae=" + username);
        com.itheima.health.pojo.User user = findByUsername(username);
        // username 用户名,  password,密码
        // Collection<? extends GrantedAuthority> authorities 这个登陆用户的权限集合
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 注意 这里的密码如果是明文 要补上{noop}
        // 授权
        // 遍历用户的角色，角色下还有权限
        Set<Role> roles = user.getRoles();
        roles.forEach(role -> {
            // 授予角色
            authorities.add(new SimpleGrantedAuthority(role.getKeyword()));
            //角色下还有权限
            Set<Permission> permissions = role.getPermissions();
            permissions.forEach(permission -> {
                // 授予权限
                authorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            });
        });
        // 用户名，数据库的密码【注意加密的方法】，权限集合
        User userDetails = new User(username, "{noop}" + user.getPassword(),authorities);
        return userDetails;
    }

    /**
     * 这个用户admin/admin, 有ROLE_ADMIN角色，角色下有ADD_CHECKITEM权限
     * 假设从数据库查询
     * @param username
     * @return
     */
    private com.itheima.health.pojo.User findByUsername (String username){
        if("admin".equals(username)) {
            com.itheima.health.pojo.User user = new com.itheima.health.pojo.User();
            user.setUsername("admin");
            // 使用密文，删除{noop}
            user.setPassword("admin");

            // 角色
            Role role = new Role();
            role.setKeyword("ROLE_ADMIN");

            // 权限
            Permission permission = new Permission();
            permission.setKeyword("ADD_CHECKITEM");

            // 给角色添加权限
            role.getPermissions().add(permission);

            // 把角色放进集合
            Set<Role> roleList = new HashSet<Role>();
            roleList.add(role);

            role = new Role();
            role.setKeyword("ABC");
            roleList.add(role);

            /**
             * admin
             * 角色-- ROLE_ADMIN
             *      权限-- ADD_CHECKITEM
             * 角色-- ABC
             */

            // 设置用户的角色
            user.setRoles(roleList);
            return user;
        }
        // 查询不到用户信息
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 加密密码
        //System.out.println(encoder.encode("1234"));

        // 密码校验,
        // 参数1：明文
        // 参数2: 密文
        System.out.println(encoder.matches("1234", "$2a$10$wfPYdSkYC/dNu5l0.NMeDOJUjGr.XpHb9kguTnteS1L1aVJYa2Q0i"));
        System.out.println(encoder.matches("1234", "$2a$10$Fzh/J9THO5aj70hjDu80XOCMLih44v60v5EJ1cs1Kkuey2dZV4/QC"));
        System.out.println(encoder.matches("1234", "$2a$10$101DMlFO8Q/GlEq3HUDW0OSjU8B3ph8mBy/Y5Uy4BJLqEnQ44mFgm"));
        System.out.println(encoder.matches("1234", "$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a"));
    }
}
