package com.ruoyi.common.utils.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.uuid.UUID;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyExcelUtil {

    public static HorizontalCellStyleStrategy defaultStyles() {
        //TODO 默认样式
        //表头样式策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //表头前景设置淡蓝色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);

        //内容样式策略策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置背景颜色白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 设置垂直居中为居中对齐
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置左右对齐为靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 设置单元格上下左右边框为细边框
        contentWriteCellStyle.setBorderBottom(BorderStyle.MEDIUM);
        contentWriteCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        contentWriteCellStyle.setBorderRight(BorderStyle.MEDIUM);
        contentWriteCellStyle.setBorderTop(BorderStyle.MEDIUM);
        //创建字体对象
        WriteFont contentWriteFont = new WriteFont();
        //内容字体大小
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short) 14);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 初始化表格样式
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }


//    /**
//     * 生成excel模板
//     *
//     * @param response
//     * @param fileName    下载的文件名，
//     * @param sheetName   sheet名
//     * @param data        导出的数据
//     * @param model       导出的头
//     * @param heardHeight 头行高
//     */
//    public static void createTemplate(HttpServletResponse response, String fileName,
//                                      String sheetName, List<? extends Object> data,
//                                      Class<?> model, int heardHeight)  {
//
//        HorizontalCellStyleStrategy horizontalCellStyleStrategy = setMyCellStyle();
//        EasyExcel.write(getOutputStream(fileName, response, ExcelTypeEnum.XLSX), model).
//                excelType(ExcelTypeEnum.XLSX).sheet(sheetName)
//                .registerWriteHandler(new TemplateCellWriteHandler(heardHeight))
//                .registerWriteHandler(horizontalCellStyleStrategy)
//                .doWrite(data);
//    }

    /**
     * 生成excel
     *
     * @param excelName excel名称
     * @param sheetName excel sheet名称
     * @param bodyList  excel 主数据
     * @param headList  excel 头标题
     */
    public static AjaxResult generateExcel(String excelName, String sheetName, List<List<String>> bodyList, List<List<String>> headList) {

        HorizontalCellStyleStrategy horizontalCellStyleStrategy = defaultStyles();

        CustomCellWriteHandler handler = new CustomCellWriteHandler();

        //指定单元格样式
        //用来记录需要为第`key`行中的第`value.get(i)`列设置样式
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        CellColorSheetWriteHandler writeHandler = new CellColorSheetWriteHandler(map, IndexedColors.RED.getIndex());
        String fileName = UUID.randomUUID().toString().concat("_").concat(excelName) + ".xlsx";
        EasyExcel.write(RuoYiConfig.getDownloadPath() + fileName).excelType(ExcelTypeEnum.XLSX).sheet().sheetName(sheetName)
                .head(headList).automaticMergeHead(true)
                .registerWriteHandler(horizontalCellStyleStrategy)
                .registerWriteHandler(writeHandler)
                .registerWriteHandler(handler)
                .doWrite(bodyList);
        return AjaxResult.success(fileName);
    }

    /**
     * 根据模板导出
     *
     * @param reserveData 封装好的对象
     * @param inputStream 模板地址
     * @param fileName    表名
     * @return
     * @throws FileNotFoundException
     */
    public static AjaxResult generateExcelByTemplate(Object reserveData, InputStream inputStream, String fileName,String upload) throws FileNotFoundException {
        String downloadPath = upload + fileName;
        System.out.println(downloadPath);
        OutputStream out = new FileOutputStream(downloadPath);
        // 导出实现
        ExcelWriter excelWriter = EasyExcel.write(out).withTemplate(inputStream).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        excelWriter.fill(reserveData, writeSheet);
        excelWriter.finish();
        return AjaxResult.success(fileName);
    }

}
