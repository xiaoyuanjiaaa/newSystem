package com.ruoyi.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("门铃码图片地址表")
public class DoorPic implements Serializable {
    /**
     * 主键id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id",type= IdType.AUTO)
    private Long id;
    /** 用户id */
    @TableField("user_id" )
    private Long userId;

    /** 门铃码图片地址 */
    @TableField("qrcode_url" )
    private String qrcodeUrl;

    /** 创建时间 */
    @TableField("create_time" )
    private LocalDateTime createTime;

    /** 修改时间 */
    @TableField("update_time" )
    private LocalDateTime updateTime;

}
