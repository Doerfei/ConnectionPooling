package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author DoerFei
 * @Date 2020/11/26 下午8:03
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 套餐列表展示
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        //查询所有的套餐
        List<Setmeal> list = setmealService.findAll();
        //页面要显示图片，拼接图片的完整路径
        list.forEach(s -> {
            s.setImg(QiNiuUtils.DOMAIN + s.getImg());
        });
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
    }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id) {
        Setmeal setmeal = setmealService.findDetailById(id);
        //页面要显示图片，拼接图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    /**
     * 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        //页面要显示图片，拼接图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
