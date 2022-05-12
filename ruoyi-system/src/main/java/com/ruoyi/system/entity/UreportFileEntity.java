package com.ruoyi.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * @description 自定义报表存储器实体
 * @author liufuwen
 * @since 20190917
 *
 */

@Data
public class UreportFileEntity {
	private Long id;
	private String name;
	private byte[] content;
	private Date createTime;
	private Date updateTime;
}
