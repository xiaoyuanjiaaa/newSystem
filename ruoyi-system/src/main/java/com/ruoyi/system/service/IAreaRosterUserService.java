package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AreaRosterUserDTO;
import com.ruoyi.system.dto.AreaRosterUserQueryDTO;
import com.ruoyi.system.entity.AreaRosterUser;
import com.ruoyi.system.excel.AreaRosterUserExcel;
import com.ruoyi.system.mapper.AreaRosterUserMapper;
import com.ruoyi.system.vo.AreaRosterUserVO;
import com.ruoyi.system.vo.UserRosterVO;

import java.util.List;

public interface IAreaRosterUserService extends IService<AreaRosterUser> {

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public AreaRosterUser selectAreaRosterUserById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<AreaRosterUser> selectAreaRosterUserList(AreaRosterUser areaRosterUser);

    /**
     * 新增【请填写功能名称】
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 结果
     */
    public int insertAreaRosterUser(AreaRosterUser areaRosterUser);

    /**
     * 修改【请填写功能名称】
     *
     * @param areaRosterUser 【请填写功能名称】
     * @return 结果
     */
    public int updateAreaRosterUser(AreaRosterUser areaRosterUser);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteAreaRosterUserByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAreaRosterUserById(Long id);

    //增加片区排班人员
    public int addAreaRosterUser(AreaRosterUserDTO areaRosterUserDTO);

    PageInfo<AreaRosterUserVO> pageList(AreaRosterUserQueryDTO areaRosterUserQueryDTO);

    List<AreaRosterUserExcel> exportList(AreaRosterUserQueryDTO areaRosterUserQueryDTO);
}
