package com.ruoyi.web.controller.app;


import com.ruoyi.common.core.domain.BaseInfo;
import com.ruoyi.common.core.domain.model.ResultVO;
import com.ruoyi.common.enums.FailEnums;
import com.ruoyi.common.enums.SuccessEnums;
import com.ruoyi.common.utils.statics.ConstantDic;
import com.ruoyi.system.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author ldp
 */
@RestController
@RequestMapping("api/file")
@Api(tags = {"文件服务器相关接口"})
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("上传图片")
    @PostMapping("/uploadFile")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file) {
        String result = fileService.uploadFile(file);
        return new ResultVO(SuccessEnums.UPLOAD_FILE_SUCCESS, result);
    }

    @ApiOperation("上传图片base64")
    @PostMapping("/uploadFileBase")
    public ResultVO uploadFileBase(@RequestBody @Valid BaseInfo info) throws IOException {
        if(!info.getId().contains(ConstantDic.COMMA)){
            return new ResultVO(FailEnums.EVENT_CHECK_IMAGE);
        }
        String result = fileService.uploadFileBase(info.getId());
        return new ResultVO(SuccessEnums.UPLOAD_FILE_SUCCESS, result);
    }

    @ApiOperation("删除图片")
    @DeleteMapping("/deleteFile")
    public ResultVO deleteFile(String path) {
        Integer result = fileService.deleteFile(path);
        return new ResultVO(SuccessEnums.DELETE_FILE_SUCCESS, result);
    }
}