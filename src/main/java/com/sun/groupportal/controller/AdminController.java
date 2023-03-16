package com.sun.groupportal.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.groupportal.common.dto.AdminEditDto;
import com.sun.groupportal.common.dto.LoginDto;
import com.sun.groupportal.common.lang.Result;
import com.sun.groupportal.entity.Admin;
import com.sun.groupportal.service.AdminService;
import com.sun.groupportal.util.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    JwtUtils jwtUtils;

    @RequiresAuthentication
    @PostMapping("/Admin/edit")
    public Result editAdmin(@Validated @RequestBody AdminEditDto adminEditDto, HttpServletResponse response) {
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("username",adminEditDto.getUsername()));

        //System.out.println("old:"+admin.getPassword());

        if(admin == null){
            return Result.fail("用户不存在");
        }
        //Assert.notNull(admin, "用户不存在");

        if(!admin.getPassword().equals(SecureUtil.md5(adminEditDto.getPassword()))){
            return Result.fail("原始密码不正确");
        }

        admin.setPassword(SecureUtil.md5(adminEditDto.getPassword_new()));
        //修改密码
        //System.out.println("new:"+admin.getPassword());
        adminService.saveOrUpdate(admin);

        String jwt = jwtUtils.generateToken(admin.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                .put("id", admin.getId())
                .put("username", admin.getUsername())
                .map()
        );
    }
}
