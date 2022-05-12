package com.ruoyi.system.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import com.alibaba.excel.annotation.write.style.OnceAbsoluteMerge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: jianguo
 * @Date: 2022/3/10 17:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@OnceAbsoluteMerge(firstColumnIndex = 5, lastColumnIndex = 9)
public class ReserveData {

    /**
     * 医院名称
     */
    public static final String hospitalName = "无锡市第五人民医院";

    private static final String tableName = "电子预检分诊流调表明细单";

    /**
     * 序号
     */
    @ExcelProperty(value = {hospitalName,tableName,"序号"})
    private String serial;
    /**
     * 时间
     */
    @ExcelProperty(value = {hospitalName,tableName,"时间"})
    private String createTime;
    /**
     * 名字
     */
    @ExcelProperty(value = {hospitalName,tableName,"名字"})
    private String personName;
    /**
     * 体温
     */
    @ExcelProperty(value = {hospitalName,tableName,"体温"})
    private String symptoms;
    /**
     * 手机号码
     */
    @ExcelProperty(value = {hospitalName,tableName,"手机号码"})
    private String mobile;
    /**
     * 身份证号
     */
    @ExcelProperty(value = {hospitalName,tableName,"身份证号"})
    private String idNum;
    /**
     * 流行病史
     */
    @ExcelProperty(value = {hospitalName,tableName,"流行病史"})
    private String epidemic;
    /**
     * 接触史
     */
    @ExcelProperty(value = {hospitalName,tableName,"接触史"})
    private String contact;
    /**
     * 高风险岗位人员
     */
    @ExcelProperty(value = {hospitalName,tableName,"高风险岗位人员"})
    private String highRisk;
    /**
     * 十大临床症状
     */
    @ExcelProperty(value = {hospitalName,tableName,"十大临床症状"})
    private String clinical;
    /**
     * 就诊专科
     */
    @ExcelProperty(value = {hospitalName,tableName,"就诊专科"})
    private String department;
    /**
     * 是否确认
     */
    @ExcelProperty(value = {hospitalName,tableName,"是否确认"})
    private String confirm;
    /**
     * 是否代填
     */
    @ExcelProperty(value = {hospitalName,tableName,"是否代填"})
    private String replace;



}
