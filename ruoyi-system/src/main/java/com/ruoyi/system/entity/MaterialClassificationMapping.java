package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("material_classification_mapping")
public class MaterialClassificationMapping {

    private static final long serialVersionUID = 4422274781212335158L;

    private Integer id;

    private String kindCode;


}
