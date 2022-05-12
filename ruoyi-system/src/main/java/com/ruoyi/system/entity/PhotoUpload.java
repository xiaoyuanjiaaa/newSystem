package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: jianguo
 * @Date: 2021/9/9 18:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("上传大头照")
@TableName("photo_upload")
public class PhotoUpload {

    private static final long serialVersionUID =  5812627873079028226L;

    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;

    /**
     * 人员id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long personId;

    /**
     * 照片路径
     */
    private String photoUrl;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;
}
