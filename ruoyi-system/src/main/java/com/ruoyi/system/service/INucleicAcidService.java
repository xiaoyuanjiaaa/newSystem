package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.dto.NucleicAcidNumDTO;
import com.ruoyi.system.entity.MaterialInput;
import com.ruoyi.system.entity.NucleicAcid;
import com.ruoyi.system.entity.vo.NucleicAcidVo;

import java.util.List;

public interface INucleicAcidService extends IService<NucleicAcid> {

    List<NucleicAcid> getList(NucleicAcid nucleicAcid);

    /**
     * 发短信提醒
     * @return
     */
    List<NucleicAcid> getCountList();

    /**
     * 页面展示
     * @return
     */
    List<NucleicAcid> getPageList(String type);

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

    /**
     * 新冠疫情信息数
     * @return
     */
    NucleicAcidNumDTO getNucleicAcidNum();

    /**
     * 新冠疫情昨日新增人数
     * @return
     */
    NucleicAcidNumDTO getAddNucleicAcidNum();

}
