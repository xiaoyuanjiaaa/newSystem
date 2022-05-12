package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 10:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("防疫物资实体")
@TableName("app_asset")
public class AppAsset extends BaseEntity{

    /** $column.columnComment */
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /** 物资分类 */
    private String assetType;

    /** 物资名称 */
    private String assetName;

    /** 物资数量 */
    private Long assetNum;

    /** 阈值 */
    private Long threshold;

    /** 储备天数 */
    private Long reserveDays;
}
