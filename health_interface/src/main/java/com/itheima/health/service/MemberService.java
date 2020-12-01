package com.itheima.health.service;

import com.itheima.health.pojo.Member;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午5:47
 */
public interface MemberService {
    /**
     * 通过手机号查询
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加新会员
     * @param member
     */
    void add(Member member);
}
