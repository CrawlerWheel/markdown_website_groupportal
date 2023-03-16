package com.sun.groupportal.controller;

import com.sun.groupportal.common.lang.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class ImgController {

//    @Autowired
//    private static Environment env;
//    final static String PIC_PATH = env.getProperty("sun.groupportal.img-markdown-baseurl"); //图片存放的相对于项目的相对位置
//    final static String PIC_INTRO_PATH = env.getProperty("sun.groupportal.img-poster-baseurl"); //图片存放的相对于项目的相对位置

    @Value("${sun.groupportal.img-markdown-baseurl}")
    private String PIC_PATH;

    @Value("${sun.groupportal.img-poster-baseurl}")
    private String PIC_INTRO_PATH;

    @RequiresAuthentication
    @PostMapping("img/intro/upload")
    public Result uploadIntroPic(MultipartHttpServletRequest multiRequest, HttpServletRequest request){

//        String savePath = "src/main/resources/" + PIC_INTRO_PATH; // 存储路径

        String saveName = multiRequest.getFile("image").getOriginalFilename(); //获取图片名
        //生成随机数确保唯一性，并加上图片后缀
       // String saveName = UUID.randomUUID().toString() + randomName.substring(randomName.lastIndexOf("."),randomName.length());

        try {
            File fileToSave = new File(PIC_INTRO_PATH + File.separator + saveName);
            InputStream inputStream = multiRequest.getFile("image").getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, fileToSave);
            inputStream.close();
            //multiRequest.getFile("image").transferTo(fileToSave); //图片存储到服务端
            String returnPath = request.getScheme() + "://"
                    + request.getServerName()+":"+request.getServerPort()
                    + "/img/intro/"+ saveName;

            return Result.succ(200,"上传成功",saveName);

        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail(500,"上传失败",null);
    }
    @RequiresAuthentication
    @PostMapping("img/intro/delete")
    public Result deleteIntroPic(@Validated @RequestBody String imgName){
        File file = new File(PIC_INTRO_PATH+File.separator+imgName.substring(0,imgName.length()-1));
        //File file = new File("C:\\web\\img\\intro\\9c29098d-2755-450c-b4ae-91b8d5ba9ace.png");
        //System.out.println(file.getPath());
        if(file.delete()){
            return Result.succ(200,"图片删除成功",null);
        }else {
            return Result.succ(501,"图片删除失败",null);
        }
    }
    @RequiresAuthentication
    @PostMapping("img/upload")
    public Result uploadPic(MultipartHttpServletRequest multiRequest, HttpServletRequest request){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //生成日期格式
        String datePrefix = dateFormat.format(new Date()); //生成当前日期作为前缀
        String absolutePath = PIC_PATH +datePrefix; //转换成绝对路径
        File folder = new File(absolutePath); //生成带当前日期的文件夹路径

        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String randomName = multiRequest.getFile("image").getOriginalFilename(); //获取图片名
        //生成随机数确保唯一性，并加上图片后缀
        String saveName = UUID.randomUUID().toString() + randomName.substring(randomName.lastIndexOf("."),randomName.length());


        try {
            File fileToSave = new File(absolutePath + File.separator + saveName);
            multiRequest.getFile("image").transferTo(fileToSave); //图片存储到服务端
            String returnPath = request.getScheme() + "://"
                    + request.getServerName()+":"+request.getServerPort()
                    + "/img/" + datePrefix +"/"+ saveName;

            return Result.succ(200,"上传成功",returnPath);

        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail(500,"上传失败",null);
    }


}
