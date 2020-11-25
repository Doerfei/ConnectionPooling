package com.itheima.health.service;

import com.itheima.health.Excpetion.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @Author DoerFei
 * @Date 2020/11/24 下午4:09
 */
public interface SetmealService {
    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal,Integer[] checkgroupIds);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 通过ID查询套餐信息
     * @param id
     * @return
     */
    Setmeal findById(int id);

    /**
     * 通过套餐查询选中的检查组id集合
     * @param id
     * @return
     */
    List<Integer> findCheckgroupIdsBySetmealId(int id);

    /**
     * 更新
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal,Integer[] checkgroupIds);

    /**
     * 通过id删除
     * @param id
     * @throws MyException
     */
    void deleteById(int id)throws MyException;

}
