package com.ruoyi.system.dto;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("新增AppVisitPlan对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppVisitPlanSaveDTO {
    @NotEmpty(message = "访客姓名不能为空")
    @ApiModelProperty(value = "访客姓名", required = true)
    private String personName;

    @ApiModelProperty(value = "访客personId", required = false)
    private Long personId;

    @NotEmpty(message = "苏康码不能为空")
    @ApiModelProperty(value = "苏康码", required = false)
    private String qrcode;

    @NotEmpty(message = "苏康码颜色不能为空")
    @ApiModelProperty(value = "苏康码颜色", required = false)
    private String qrcodeColor;

    @NotEmpty(message = "车牌号不能为空")
    @ApiModelProperty(value = "车牌号", required = false)
    private String carNo;

    @NotEmpty(message = "体温不能为空")
    @ApiModelProperty(value = "体温", required = false)
    private String temperature;

    @NotEmpty(message = "目的地json不能为空")
    @ApiModelProperty(value = "目的地json", required = false)
    private String destination;

    @ApiModelProperty(value = "目的地json", required = false)
    private String destinationName;

    @NotEmpty(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true)
    private String idNum;

    @ApiModelProperty(value = "手机号码", required = true)
    private String mobile;

    @ApiModelProperty(value = "座机号", required = false)
    private String landLine;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @NotEmpty(message = "备注不能为空")
    @ApiModelProperty(value = "图片地址", required = false)
    private String picUrl;

    @ApiModelProperty(value = "进出口类型1：进口，2：出口，3：内部", required = false)
    private Integer type;

    @NotEmpty(message = "创建者不能为空")
    @ApiModelProperty(value = "创建者", required = true)
    private String createBy;

    @ApiModelProperty(value = "是否发热，0不发热，1：发热", required = false)
    private Integer isHot;

    @ApiModelProperty(value = "是否去过高风险地区，0否，1：是", required = false)
    private Integer isHighRiskArea;

    @ApiModelProperty(value = "十大症状json数据字符串")
    private String symptoms;

    @ApiModelProperty(value = "大门")
    private Long door;

    @ApiModelProperty(value = "码类型")
    private Integer qcodeType;

    @ApiModelProperty(value = "目的地部门")
    private String destinationDeptName;

    @ApiModelProperty(value = "appPersonWxId", required = false)
    private Long appPersonWxId;

    @ApiModelProperty(value = "yjfzcode_color", required = false)
    private String yjfzcodeColor;

    @ApiModelProperty(value = "entrance_name", required = false)
    private String entranceName;

    @ApiModelProperty(value = "visited_person", required = false)
    private String visitedPerson;

}
