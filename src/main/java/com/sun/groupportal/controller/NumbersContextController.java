package com.sun.groupportal.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.groupportal.common.lang.Result;
import com.sun.groupportal.entity.NumbersContext;
import com.sun.groupportal.service.NumbersContextService;
import com.sun.groupportal.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class NumbersContextController {

    @Autowired
    NumbersContextService numbersContextService;

    @GetMapping("/NumbersContexts")
    public Result detail() {
        NumbersContext numbersContext =  numbersContextService.getById(1L);//人员情况的数据库表格只存储了一行数据
        Assert.notNull(numbersContext, "人事情况页：已被删除");
        return Result.succ(numbersContext);
    }


    @RequiresAuthentication
    @PostMapping("/NumbersContexts/edit")
    public Result edit(@Validated @RequestBody NumbersContext temp) {
        NumbersContext numbersContext = numbersContextService.getById(1L);
        BeanUtil.copyProperties(temp, numbersContext, "id","status");
        numbersContextService.saveOrUpdate(numbersContext);
        return Result.succ(null);
    }
}
