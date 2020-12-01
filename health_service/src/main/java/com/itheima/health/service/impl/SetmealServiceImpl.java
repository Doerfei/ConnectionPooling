package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Excpetion.MyException;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author DoerFei
 * @Date 2020/11/24 下午4:25
 */

@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal,Integer[] checkgroupIds){
        //先添加套餐
        setmealDao.add(setmeal);
        //在获取套餐的ID
        Integer setmealId = setmeal.getId();
        //遍历检查组的ID
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                //添加套餐与检查组的关系
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    /**
     * 通过ID查询套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(int id) {
       return setmealDao.findById(id);
    }

    /**
     * 通过检查组查询选中的检查组ID集合
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    /**
     * 更新
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
       //先更新
        setmealDao.update(setmeal);
        //删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加新关系
        if (null!=checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
    }

    /**
     * 通过ID删除
     * @param id
     * @throws MyException
     */
    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        //是否订单使用
        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count>0) {
            //被订单使用了
            throw new MyException("该套餐已经被订单使用了，不能删除");
        }
        //先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        //在删除套餐
        setmealDao.deleteById(id);
    }

    /**
     * 查询数据库中套餐的所有图片
     * @return
     */
    @Override
   public List<String> findImgs(){
        return setmealDao.findImgs();
   }

    /**
     * 查询所有的套餐
     * @return
     */
   @Override
    public List<Setmeal> findAll(){
        return setmealDao.findAll();
   }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
   @Override
    public Setmeal findDetailById(int id){
       return setmealDao.findDetailById(id);
   }
}
























