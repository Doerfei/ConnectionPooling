package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Excpetion.MyException;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * interfaceClass: 指定发布的服务接口
 * 在dubbo:2.6.0里使用事务时，需要指定
 * </p>
 */

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }


    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    /**
     * 分页查询条件
     * @param queryPageBean
     * @return
     */

    @Override
    public PageResult<CheckItem>findPage(QueryPageBean queryPageBean){
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //是否查询条件【注意!!!!】
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //调用dao查询
        Page<CheckItem> checkItemPage = checkItemDao.findByCondition(queryPageBean.getQueryString());
        long total = checkItemPage.getTotal();//总计路数
        List<CheckItem> rows = checkItemPage.getResult();//分页结果集
        return  new PageResult<CheckItem>(total,rows);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id){
        return checkItemDao.findById(id);
    }

    /**
     * 更新
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem){
        checkItemDao.update(checkItem);
    }


    /**
     * 通过ID删除
     * @param id
     */
    @Override
    public void deleteById(int id){
        //判断是否被检查组使用了
        int count = checkItemDao.findCountByCheckItemId(id);
        //count>0被使用了
              if (count>0) {
                  throw  new MyException("该检查项被检查组使用了，不允许被删除");
              }
              //count = 0 则可以被删除
              checkItemDao.deleteById(id);
          }
}





























