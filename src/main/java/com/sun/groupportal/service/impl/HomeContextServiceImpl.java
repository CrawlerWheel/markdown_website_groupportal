package com.sun.groupportal.service.impl;

import com.sun.groupportal.entity.HomeContext;
import com.sun.groupportal.mapper.HomeContextMapper;
import com.sun.groupportal.service.HomeContextService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author groupportal
 * @since 2022-07-13
 */
@Service
public class HomeContextServiceImpl extends ServiceImpl<HomeContextMapper, HomeContext> implements HomeContextService {

    @Override
    public ArrayList<String> getImgList() {
        ArrayList<String> imgNames = new ArrayList<String>();
        File file = new File("C:\\web\\img\\intro");
        //用数组把文件夹下的文件存起来
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                imgNames.add(files[i].getName());
//                System.out.println("文件夹:"+files[i].getPath());
            }
        }
        return imgNames;
    }
}
