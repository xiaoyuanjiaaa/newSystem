package com.ruoyi.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.ruoyi.system.dto.AppPersonQueryDTO;
import com.ruoyi.system.dto.AppPersonSaveDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppPersonWxVO;

import java.util.List;


/**
 * 人员基本信息
 * @author mac
 */
public interface AppPersonService extends IService<AppPerson> {

    /**
     * 添加人员基本信息
     * @param dto
     * @return
     */
    AppPersonVO saveAppPerson(AppPersonSaveDTO dto);

    AppPerson getAppPerson(Long personId);

    /**
     * 通过身份证从查询人员信息
     * @param idNum
     * @return
     */
    AppPersonVO getAppPersonByIdNum(String idNum, String mobile);

    /**
     * 通过手机号从查询人员信息
     * @param queryDTO
     * @return
     */
    AppPersonVO getAppPerson(AppPersonQueryDTO queryDTO);

    /**
     * 查询添加人员信息
     * @param idNum
     * @param name
     * @param mobile
     * @return
     */
    Long getPersonInfo(Long parentPersonId,String idNum, String name, String mobile, String qrCode, String level);


    /**
     * 查询添加人员信息
     * @param idNum
     * @param name
     * @param mobile
     * @return
     */
    Long getPersonInfo(String idNum, String name, String mobile, Long deptId);

    /**
     * 查询添加人员与openId关系
     * @param personId
     * @param openId
     * @return
     */
    Integer addPersonOpenId(Long personId, String openId, String symptoms);

    PageInfo<AppPersonWxVO> getList(AppPersonQueryDTO queryDTO);


    /**
     * 根据身份证姓名查询预检分诊信息
     * @param idNum
     * @param name
     * @return
     */
    Long getPersonInfoForPublicApi(String idNum, String name);

}
