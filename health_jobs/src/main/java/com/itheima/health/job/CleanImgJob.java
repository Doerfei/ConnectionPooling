package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


//@Component
public class CleanImgJob {

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Reference
    private SetmealService setmealService;

    // 发布时应该  @Scheduled(cron = "0 0 2 * * ? *")
    //@Scheduled(initialDelay = 3000,fixedDelay = 1800000)
    public void cleanImg(){
        log.info("清理任务开始执行....");
        List<String> img7Niu = QiNiuUtils.listFile();
        log.debug("7牛上有{}张图片",null==img7Niu?0:img7Niu.size());
        List<String> imgInDB = setmealService.findImgs();
        log.debug("数据库有{}张图片",null==imgInDB?0:imgInDB.size());
        // 执行removeAll之后，img7Niu剩下的就是7牛上有的但是数据库没有的
        img7Niu.removeAll(imgInDB);
        log.debug("要删除的图片有{}张",img7Niu.size());
        String[] need2Delete = img7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(need2Delete);
        log.info("删除{}张图片成功",img7Niu.size());
    }

    public static void main(String[] args) {
        List<String> a1 = new ArrayList<String>(3);
        a1.add("1");
        a1.add("2");
        a1.add("3");

        List<String> a2 = new ArrayList<String>(3);
        a2.add("2");
        a2.add("3");
        a2.add("4");

        a1.removeAll(a2);

        a1.forEach(System.out::println);
    }
}
