package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Member;

import java.util.List;

/**
 * @Author DoerFei
 * @Date 2020/11/28 下午5:57
 */
public interface MemberDao {
    List<Member> findAll();
    Page<Member> selectByCondition(String queryString);
    void add(Member member);
    void deleteById(Integer id);
    Member findById(Integer id);
    void edit(Member member);

    /**
     * 通过手机号码查询会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 报表使用的
     */
    Integer findMemberCountBeforeDate(String date);
    Integer findMemberCountByDate(String date);
    Integer findMemberCountAfterDate(String date);
    Integer findMemberTotalCount();
}
