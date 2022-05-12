package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.AppRosterUser;
import com.ruoyi.system.entity.AreaRosterUser;
import com.ruoyi.system.vo.AreaRosterUserVO;
import com.ruoyi.system.vo.UserRosterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface AreaRosterUserMapper extends BaseMapper<AreaRosterUser> {

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
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteAreaRosterUserById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAreaRosterUserByIds(Long[] ids);

    List<AreaRosterUserVO> selectListWrapper(@Param("ew") QueryWrapper<AreaRosterUser> queryWrapper);
}
