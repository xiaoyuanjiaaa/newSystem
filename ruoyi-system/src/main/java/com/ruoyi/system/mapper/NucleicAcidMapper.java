package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.vo.NucleicAcidVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NucleicAcidMapper extends BaseMapper<NucleicAcid> {

    List<NucleicAcid> getCountList();


    List<NucleicAcid> getPageList(@Param("Monday") String Monday,@Param("Sunday") String Sunday,@Param("type") String type);

    /**
     * 核酸结果类型
     * @return
     */
    List<NucleicAcidVo> getNucleicAcid();

    /**
     * 抗体结果类型
     * @return
     */
    List<NucleicAcidVo> getAntibody();

}
