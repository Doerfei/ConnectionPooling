package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();

        System.out.println(list);
        // 封装到result
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * 添加检查项
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        // 调用服务添加
        checkItemService.add(checkItem);
        // 返回结果
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 分页查询条件
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务查询
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //返回结果给页面
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    @GetMapping("findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkItem);
     }

    /**
     * 更新
     * @param checkItem
     * @return
     */
     @PostMapping("/update")
      public Result update(@RequestBody CheckItem checkItem) {
        //调用服务修改
         checkItemService.update(checkItem);
         return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
     }

    /**
     * 删除
     * @param id
     * @return
     */
     @PostMapping("/deleteById")
    public Result deleteById(int id){
         checkItemService.deleteById(id);
         return  new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
     }
}





















































