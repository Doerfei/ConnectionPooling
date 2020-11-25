package com.itheima.health.service;

import com.itheima.health.Excpetion.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup,Integer[]checkitemIds);

    /**
     * 分业查询条件
     * @param queryPageBean
     * @return
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    CheckGroup findById(int id);

    /**
     * 通过ID查询选中的检查项ID集合
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 编辑检查组更新
     * @param checkGroup
     * @param checkitemsIds
     */
    void update(CheckGroup checkGroup ,Integer[] checkitemsIds);

    /**
     * 通过ID删除检查组
     * @param id
     * @throws MyException
     */
    void deleteById(int id)throws MyException;

    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();
}
