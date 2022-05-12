package com.ruoyi.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘彦硕
 * @Description TODO
 * @Date 2020/3/24 10:58 上午
 */
@Component
@ConfigurationProperties(prefix = "gathering")
@Data
public class GatheringProperties {
    private String uploadPath;

    private Integer pageSize;
}
