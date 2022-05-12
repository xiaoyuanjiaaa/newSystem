package com.ruoyi.system.dto;

import com.ruoyi.common.core.page.PageDomain;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: jianguo
 * @Date: 2021/9/10 8:46
 */
@Data
@ApiModel(value = "健康填报")
public class AppEchelonDTO extends PageDomain {

    /** 梯队名称 */
    private String echelonName;

}
