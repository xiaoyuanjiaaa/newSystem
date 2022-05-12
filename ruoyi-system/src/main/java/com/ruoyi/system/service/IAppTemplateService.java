package com.ruoyi.system.service;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.system.entity.AppTemplate;

/**
 * @Description
 * @Author  ddl
 * @Date 2021-08-14 01:54
 */
public interface IAppTemplateService extends IService<AppTemplate> {

    PageInfo pageAppTemplate(PageDomain pageDomain);
}
