package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.dto.AppPersonQueryDTO;
import com.ruoyi.system.entity.AppPerson;
import com.ruoyi.system.vo.AppPersonVO;
import com.ruoyi.system.vo.AppPersonWxVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AppPersonMapper extends BaseMapper<AppPerson> {

    /**
     * 通过身份证前后四位和手机号码查询人员信息是否存在
     * @param startNum
     * @param endNum
     * @param mobile
     * @return
     */
    AppPersonVO getAppPersonByIdNum(@Param("startNum") String startNum, @Param("endNum") String endNum,
                                    @Param("mobile") String mobile);

    Integer selectPersonOpenId(@Param("openId") String openId);

    Integer updatePersonOpenId(@Param("personId") Long personId, @Param("openId") String openId);

    Integer insertPersonOpenId(@Param("personId") Long personId, @Param("openId") String openId,
                               @Param("symptoms") String symptoms);

    List<AppPersonWxVO> getList(@Param("queryDTO")AppPersonQueryDTO queryDTO);

    /**
     * 通过身份证前后四位和手机号码查询人员信息是否存在
     * @param startNum
     * @param endNum
     * @param name
     * @return
     */
    AppPersonVO getAppPersonByIdNumAndName(@Param("startNum") String startNum, @Param("endNum") String endNum,
                                    @Param("name") String name);

}
