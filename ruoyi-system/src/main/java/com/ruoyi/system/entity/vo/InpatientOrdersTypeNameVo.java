package com.ruoyi.system.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("病程录医嘱类型")
public class InpatientOrdersTypeNameVo {
    /**
     * 医嘱类型
     */
    private String typeName;
}
