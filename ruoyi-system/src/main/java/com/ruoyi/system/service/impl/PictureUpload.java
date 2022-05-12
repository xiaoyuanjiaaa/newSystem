package com.ruoyi.system.service.impl;

//import com.ruoyi.system.service.ImageProvider;
import com.bstek.ureport.exception.ReportComputeException;
import com.bstek.ureport.provider.image.ImageProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 李东的代码
 * @author  XL
 * @create  2021/12/7 14:38
 **/
@Component
public class PictureUpload implements ImageProvider, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String baseWebPath;

    @Override
    public InputStream getImage(String path) {
//        System.out.println(path);
        try {
            if( path.startsWith("http:\\")){
                //返回一个输入流，该输入流的读取位置是传入进来的path，也就是界面上输入的地址
                File file=new File(path);
                InputStream inputStream=new FileInputStream(file);
                return inputStream;
            }else{
                path=baseWebPath+path;
                return new FileInputStream(path);
            }
        } catch (IOException e) {
            throw new ReportComputeException(e);
        }
    }

    @Override
    public boolean support(String path) {
        if(path.startsWith("http:\\")){
            return true;
        }else if(baseWebPath!=null && path.startsWith("http:\\")){
            return true;
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext instanceof WebApplicationContext){
            WebApplicationContext context=(WebApplicationContext)applicationContext;
            baseWebPath=context.getServletContext().getRealPath("http:\\");
        }
        this.applicationContext=applicationContext;
    }
}
