package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppEchelonDTO;
import com.ruoyi.system.dto.AppEchelonUserDTO;
import com.ruoyi.system.entity.AppEchelon;

import java.util.List;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 20:10
 */
public interface IAppEchelonService extends IService<AppEchelon> {


    /**
     * 分页查询
     * @param appEchelonDTO
     * @return
     */
    IPage<AppEchelon> listPage(AppEchelonDTO appEchelonDTO);

    /**
     * 删除梯队
     * @param idOne
     * @return
     */
    boolean removeByIdsAll(List<String> idOne);

    /**
     * 将人员加入梯队
     * @param appEchelonUserDTO
     * @return
     */
    boolean saveUser(AppEchelonUserDTO appEchelonUserDTO);

    /**
     * 查询梯队详情，包括内部包含的人员
     * @param id
     * @return
     */
    AppEchelon getByIdAndUser(Long id);

    /**
     * 删除梯队中的人员
     * @param id
     * @return
     */
    boolean deleteEchelonUser(Long id);

    /**
     * 判断当前修改人数是否小于已有人数
     * @param appEchelon
     * @return
     */
    boolean judge(AppEchelon appEchelon);

    /**
     * 修改梯队信息时同时修改人员信息
     * @param appEchelon
     * @return
     */
    boolean saveOrUpdateAndUser(AppEchelon appEchelon);
}
