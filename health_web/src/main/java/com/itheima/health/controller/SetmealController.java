package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author DoerFei
 * @Date 2020/11/24 下午3:45
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取文件名，获取他的后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //产生为一名称，拼接后缀名，图片名
        String imgName = UUID.randomUUID().toString() + suffix;
        //调用QiNiuUtils上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), imgName);
            //成功后返回图片名与域名
            /**
             * imgName:图片名
             * domain:域名
             */
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("imgName", imgName);
            resultMap.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true,MessageConstant.UPLOAD_SUCCESS,resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            //e.printStackTrace();
            log.error("上传文件失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用服务添加
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal>pageResult= setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        /**
         * Result{
         *     flag:
         *     message;
         *     data:{
         *     	domain: 路径
         *     	setmeal: formData  img图片名
         *        }
         * }
         */
        Map<String,Object> resultMap = new HashMap<String,Object>(2);
        resultMap.put("domain",QiNiuUtils.DOMAIN);
        resultMap.put("setmeal",setmeal);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }

    /**
     * 通过套餐ID查询选中的检查组id集合
     * @param id
     * @return
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        List<Integer> ids = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,ids);
    }

    /**
     * 更新套餐
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[]checkgroupIds){
        //调用服务更新
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,"更改套餐成功");
    }

    /**
     * 通过ID删除
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用服务层删除
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
