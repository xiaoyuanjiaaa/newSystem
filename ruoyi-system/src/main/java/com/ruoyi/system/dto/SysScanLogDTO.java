package com.ruoyi.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "职工扫码记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysScanLogDTO {

    /** 手机号 */
    @ApiModelProperty(value = "身份证号", required = true)
    private String idNum;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true)
    private String personName;

    @ApiModelProperty(value = "人口地址", required = true)
    private String entranceName;
    /**
     * 短信发送内容
     */
    @ApiModelProperty(value = "短信发送内容", required = true)
    private String jsonResult;

}
