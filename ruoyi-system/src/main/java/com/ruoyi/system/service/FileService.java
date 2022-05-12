package com.ruoyi.system.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ldp
 */
public interface FileService {


    /**
     * 上传图片
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);

    /**
     * 删除图片
     * @param path
     * @return
     */
    Integer deleteFile(String path);

    /**
     * 上传图片base64
     * @param fileContent
     * @return
     */
    String uploadFileBase(String fileContent);
}