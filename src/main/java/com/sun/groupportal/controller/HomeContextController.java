package com.sun.groupportal.controller;


import cn.hutool.core.bean.BeanUtil;
import com.sun.groupportal.common.dto.PostersDto;
import com.sun.groupportal.common.lang.Result;
import com.sun.groupportal.entity.HomeContext;
import com.sun.groupportal.entity.NumbersContext;
import com.sun.groupportal.service.HomeContextService;
import com.sun.groupportal.service.NumbersContextService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
@RestController
public class HomeContextController {

    @Autowired
    HomeContextService homeContextService;

    @GetMapping("/HomeContexts")
    public Result detail() {
        HomeContext homeContext =  homeContextService.getById(1L);//人员情况的数据库表格只存储了一行数据
        Assert.notNull(homeContext, "人事情况页：已被删除");
        return Result.succ(homeContext);
    }

    @GetMapping("/IntroImg")
    public Result introImg(HttpServletRequest request) {
        ArrayList<String> imgNameList = homeContextService.getImgList();
        ArrayList<PostersDto> postersList = new ArrayList<PostersDto>();
        for (String name : imgNameList){
            String returnPath = request.getScheme() + "://"
                    + request.getServerName()+":"+request.getServerPort()
                    + "/img/intro/"+ name;
            postersList.add(new PostersDto(name,returnPath));
        }
        return Result.succ(200,"海报列表已加载",postersList);
    }

    @RequiresAuthentication
    @PostMapping("/HomeContexts/edit")
    public Result edit(@Validated @RequestBody HomeContext temp) {
        HomeContext homeContext = homeContextService.getById(1L);
        BeanUtil.copyProperties(temp, homeContext, "id","status");
        homeContextService.saveOrUpdate(homeContext);
        return Result.succ(null);
    }
}
