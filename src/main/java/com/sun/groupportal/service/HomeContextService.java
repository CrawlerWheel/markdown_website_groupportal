package com.sun.groupportal.service;

import com.sun.groupportal.entity.HomeContext;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
public interface HomeContextService extends IService<HomeContext> {

    abstract ArrayList<String> getImgList();
}
