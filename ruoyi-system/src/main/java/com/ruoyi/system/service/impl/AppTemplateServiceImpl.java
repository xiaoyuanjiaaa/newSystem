package com.ruoyi.system.service.impl;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.system.entity.AppTemplate;
import com.ruoyi.system.mapper.AppTemplateMapper;
import com.ruoyi.system.service.IAppTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author  dll
 * @Date 2021-08-14 02:11
 */
@Service
public class AppTemplateServiceImpl extends ServiceImpl<AppTemplateMapper, AppTemplate> implements IAppTemplateService {

   @Autowired
   private AppTemplateMapper appTemplateMapper;

    @Override
    public PageInfo<AppTemplate> pageAppTemplate(PageDomain pageDomain) {

        PageHelper.startPage(pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
        List<AppTemplate> appTemplates = list();
        return new PageInfo<>(appTemplates);
    }
}
