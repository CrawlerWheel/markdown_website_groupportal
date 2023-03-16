package com.sun.groupportal.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sun.groupportal.common.lang.Result;
import com.sun.groupportal.entity.HarvestContext;
import com.sun.groupportal.entity.HomeContext;
import com.sun.groupportal.entity.NumbersContext;
import com.sun.groupportal.service.HarvestContextService;
import com.sun.groupportal.service.NumbersContextService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
@RestController
public class HarvestContextController {

    @Autowired
    HarvestContextService harvestContextService;

    @GetMapping("/HarvestContexts")
    public Result detail() {
        HarvestContext harvestContext =  harvestContextService.getById(1L);//人员情况的数据库表格只存储了一行数据
        Assert.notNull(harvestContext, "人事情况页：已被删除");
        return Result.succ(harvestContext);
    }


    @RequiresAuthentication
    @PostMapping("/HarvestContexts/edit")
    public Result edit(@Validated @RequestBody HarvestContext temp) {
        HarvestContext harvestContext = harvestContextService.getById(1L);
        BeanUtil.copyProperties(temp, harvestContext, "id","status");
        harvestContextService.saveOrUpdate(harvestContext);
        return Result.succ(null);
    }
}
