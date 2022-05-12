package com.ruoyi.web.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.entity.PhotoUpload;
import com.ruoyi.system.service.PhotoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 【上传大头照】Controller
 * 
 * @author ruoyi
 * @date 2021-09-09
 */
@RestController
@RequestMapping("/photoUpload")
public class PhotoUploadController extends BaseController
{

    @Autowired
    private PhotoUploadService photoUploadService;

    /**
     * 获取【大头照】详细信息
     */
    @GetMapping(value = "/{personId}")
    public AjaxResult getInfo(@PathVariable("personId") Long personId)
    {
        return AjaxResult.success(photoUploadService.getOne(new QueryWrapper<PhotoUpload>().eq("person_id",personId)));
    }

    /**
     * 上传【大头照】
     */
    @PutMapping
    public AjaxResult add(@RequestBody PhotoUpload photoUpload)
    {
        return toAjax(photoUploadService.saveOrUpdate(photoUpload,
                new UpdateWrapper<PhotoUpload>().eq("person_id",photoUpload.getPersonId()).set("photo_url",photoUpload.getPhotoUrl())));
    }


}
