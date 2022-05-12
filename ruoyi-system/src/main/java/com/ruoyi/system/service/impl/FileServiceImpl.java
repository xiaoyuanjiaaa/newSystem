package com.ruoyi.system.service.impl;

import com.ruoyi.common.fastdfs.BASE64DecodedMultipartFile;
import com.ruoyi.common.fastdfs.FastDFSClient;
import com.ruoyi.common.properties.GatheringProperties;
import com.ruoyi.system.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.IOException;

/**
 * @author user
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private GatheringProperties gatheringProperties;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 上传图片
     * @param file
     *
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            makeDir();
            String filePath = fastDFSClient.uploadFile(file);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除图片
     * @param path
     * @return
     */
    @Override
    public Integer deleteFile(String path) {
        fastDFSClient.deleteFile(path);
        return 1;
    }

    /**
     * 上传图片base64
     * @param fileContent
     * @return
     */
    @Override
    public String uploadFileBase(String fileContent) {
        if (StringUtils.isNotBlank(fileContent)) {
            BASE64Decoder decoder = new BASE64Decoder();
            String[] baseStr = fileContent.split(",");
            byte[] b = new byte[0];
            try {
                b = decoder.decodeBuffer(baseStr[1]);
                for(int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {
                        b[i] += 256;
                    }
                }
                MultipartFile file = new BASE64DecodedMultipartFile(b, baseStr[0]);
                return uploadFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private File makeDir(){
        File parent = new File(gatheringProperties.getUploadPath());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        return parent;
    }
}