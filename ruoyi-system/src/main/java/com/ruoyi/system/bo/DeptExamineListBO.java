package com.ruoyi.system.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@ApiModel("片区审核列表业务对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptExamineListBO {
    //用户名称
    private String nickName;

    //手机号
    private String phonenumber;
    //片区id
    private Integer deptId;
}
