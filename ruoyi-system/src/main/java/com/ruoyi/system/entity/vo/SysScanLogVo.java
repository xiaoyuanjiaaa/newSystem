package com.ruoyi.system.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "扫码记录")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysScanLogVo {

    /**
     * 日志ID
     */
    @TableField("id" )
    private Long id;

    /**
     * 短信发送内容
     */
    @TableField("id_num" )
    private String idNum;

    /**
     * 短信发送状态
     */
    @TableField("person_name" )
    private String personName;

    /**
     * 短信发送时间
     */
    @TableField("json_result" )
    private String jsonResult;

    /**
     * 创建时间
     */
    @TableField("create_time" )
    private Date createTime;

}
