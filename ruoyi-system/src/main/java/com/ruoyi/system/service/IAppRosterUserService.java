package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppRosterQueryDTO;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.excel.AppRosterUserExcel;
import com.ruoyi.system.vo.UserRosterVO;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 13:14
 */
public interface IAppRosterUserService extends IService<AppRosterUser> {

    PageInfo<UserRosterVO> pageList(AppRosterQueryDTO appRosterQueryDTO);

    List<AppRosterUserExcel> exportList(AppRosterQueryDTO appRosterQueryDTO);
}
