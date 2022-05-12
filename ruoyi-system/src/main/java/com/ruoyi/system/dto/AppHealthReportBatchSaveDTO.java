package com.ruoyi.system.dto;

import com.ruoyi.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@ApiModel("新增AppHealthReport对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppHealthReportBatchSaveDTO {

    @ApiModelProperty(value = "需要批量添加的personId", required = true)
    private List<Long> personId;

    @ApiModelProperty(value = "填报内容", required = true)
    private String reportContent;

    @ApiModelProperty(value = "核酸未采备注", required = false)
    private String remark;

    @ApiModelProperty(value = "填报来源：1.小程序填报；2.代为填报；3.批量填报；4无需填报", required = false)
    private Integer appSource;

}
