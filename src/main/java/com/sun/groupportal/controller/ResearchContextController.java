package com.sun.groupportal.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sun.groupportal.common.lang.Result;
import com.sun.groupportal.entity.NumbersContext;
import com.sun.groupportal.entity.ResearchContext;
import com.sun.groupportal.service.NumbersContextService;
import com.sun.groupportal.service.ResearchContextService;
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
public class ResearchContextController {

    @Autowired
    ResearchContextService researchContextService;

    @GetMapping("/ResearchContexts")
    public Result detail() {
        ResearchContext researchContext =  researchContextService.getById(1L);//人员情况的数据库表格只存储了一行数据
        Assert.notNull(researchContext, "人事情况页：已被删除");
        return Result.succ(researchContext);
    }


    @RequiresAuthentication
    @PostMapping("/ResearchContexts/edit")
    public Result edit(@Validated @RequestBody ResearchContext temp) {
        ResearchContext researchContext = researchContextService.getById(1L);
        BeanUtil.copyProperties(temp, researchContext, "id","status");
        researchContextService.saveOrUpdate(researchContext);
        return Result.succ(null);
    }
}
