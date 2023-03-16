package com.sun.groupportal.service.impl;

import com.sun.groupportal.entity.Admin;
import com.sun.groupportal.mapper.AdminMapper;
import com.sun.groupportal.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
