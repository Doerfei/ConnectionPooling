package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Excpetion.MyException;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional         //添加事务
    public void add(CheckGroup checkGroup,Integer[] checkitemIds){
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组id
        Integer checkGroupId = checkGroup.getId();
        //遍历选中的检查项ids数组
        if (null != checkitemIds) {
            //添加检查组与检查项的关系
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    /**
     * 分页条件查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean){
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> checkGroupPage = checkGroupDao.findByCondition(queryPageBean.getQueryString());
         return  new PageResult<CheckGroup>(checkGroupPage.getTotal(),checkGroupPage.getResult());
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id){
        return checkGroupDao.findById(id);
    }

    /**
     * 通过ID查询选中的检查项ID集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id){
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 跟新检查组
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds){
        //跟新检查组信息
        checkGroupDao.update(checkGroup);
        //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //遍历添加新关系
        if (null!=checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
               checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    /**
     * 通过ID删除检查组
     * @param id
     * @throws MyException
     */
    @Override
    @Transactional
    public void deleteById(int id)throws MyException{

        //判断是否被套餐使用
        int count = checkGroupDao.findCountByCheckGroupId(id);
        //被使用了，报错
        if (count>0) {
            throw new MyException("该检查组被检查项使用了，不能删除");
        }
        //没使用//先删除关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //在删除检查组
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询所有检查组
     * @return
     */
    public List<CheckGroup> findAll(){
        return  checkGroupDao.findAll();
    }
}






























