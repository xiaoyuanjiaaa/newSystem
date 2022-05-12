package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.entity.PhotoUpload;
import com.ruoyi.system.mapper.PhotoUploadMapper;
import com.ruoyi.system.service.PhotoUploadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:16
 */
@Service
@Transactional
public class PhotoUploadServiceImpl extends ServiceImpl<PhotoUploadMapper, PhotoUpload> implements PhotoUploadService {


}
