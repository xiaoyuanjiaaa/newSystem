package com.ruoyi.ureport.provider;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.entity.UreportFileEntity;
import com.ruoyi.system.mapper.UreportFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;

import lombok.Setter;

/**
 * Mysql 报表存储
 * @author qiaolin
 * @version 2018年5月9日
 *
 */

@Setter
@Component
// 该注解可以利用其 prefix属性值 + 类的属性名 在yml中配置属性值
@ConfigurationProperties(prefix = "ureport.mysql.provider")
public class MySQLProvider implements ReportProvider{
	private static final String NAME = "mysql-provider";

	// 特定前缀，ureport底层会调用 getPrefix 方法来获取报表操作的Provier类
	private String prefix = "mysql:";

	// 是否禁用
	private boolean disabled;

	@Autowired
	private UreportFileMapper ureportFileMapper;

	@Override
	public InputStream loadReport(String file) {
		UreportFileEntity ureportFileEntity = ureportFileMapper.queryUreportFileEntityByName(getCorrectName(file));
		byte[] content = ureportFileEntity.getContent();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
		return inputStream;
	}

	@Override
	public void deleteReport(String file) {
		ureportFileMapper.deleteReportFileByName(getCorrectName(file));
	}

	@Override
	public List<ReportFile> getReportFiles() {
		List<UreportFileEntity> list = ureportFileMapper.queryReportFileList();
		List<ReportFile> reportList = new ArrayList<>();
		for (UreportFileEntity ureportFileEntity : list) {
			reportList.add(new ReportFile(ureportFileEntity.getName(), ureportFileEntity.getUpdateTime()));
		}
		return reportList	;
	}

	@Override
	public void saveReport(String file, String content) {
		file = getCorrectName(file);
		UreportFileEntity ureportFileEntity = ureportFileMapper.queryUreportFileEntityByName(file);
		Date currentDate = new Date();
		if(ureportFileEntity == null){
			ureportFileEntity = new UreportFileEntity();
			ureportFileEntity.setName(file);
			ureportFileEntity.setContent(content.getBytes());
			ureportFileEntity.setCreateTime(currentDate);
			ureportFileEntity.setUpdateTime(currentDate);
			ureportFileMapper.insertReportFile(ureportFileEntity);
		}else{
			ureportFileEntity.setContent(content.getBytes());
			ureportFileEntity.setUpdateTime(currentDate);
			ureportFileMapper.updateReportFile(ureportFileEntity);
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean disabled() {
		return disabled;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	/**
	 * 获取没有前缀的文件名
	 * @param name
	 * @return
	 */
	private String getCorrectName(String name){
		if(name.startsWith(prefix)){
			name = name.substring(prefix.length(), name.length());
		}
		return name;
	}
}