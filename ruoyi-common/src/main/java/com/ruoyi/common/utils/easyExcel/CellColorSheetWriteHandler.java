package com.ruoyi.common.utils.easyExcel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author james
 * @version 1.0
 * @description: 设置单元格格式
 * @date 2021/10/16 15:46
 */
public class CellColorSheetWriteHandler implements CellWriteHandler {

    /**
     * map
     * key：第i行
     * value：第i行中单元格索引集合
     */
    private HashMap<Integer, List<Integer>> map;

    /**
     * 颜色
     */
    private Short colorIndex;

    /**
     * 有参构造
     */
    public CellColorSheetWriteHandler(HashMap<Integer, List<Integer>> map, Short colorIndex) {
        this.map = map;
        this.colorIndex = colorIndex;
    }

    /**
     * 无参构造
     */
    public CellColorSheetWriteHandler() {

    }

    /**
     * 在创建单元格之前调用
     */
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
    }

    /**
     * 在单元格创建后调用
     */
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer integer, Boolean aBoolean) {

    }

    /**
     * 在单元上的所有操作完成后调用
     */
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        Sheet sheet = writeSheetHolder.getSheet();

        /**
         * 考虑到导出数据量过大的情况，不对每一行的每一个单元格进行样式设置，只设置必要行中的某个单元格的样式
         */
        //当前行的第i列
        int i = cell.getColumnIndex();
        //不处理第一行
        if (0 != cell.getRowIndex()) {
            List<Integer> integers = map.get(cell.getRowIndex());
            if (integers != null && integers.size() > 0) {
                if (integers.contains(i)) {
                    // 根据单元格获取workbook
                    Workbook workbook = cell.getSheet().getWorkbook();
                    //设置行高
                    writeSheetHolder.getSheet().getRow(cell.getRowIndex()).setHeight((short) (1.4 * 256));
                    writeSheetHolder.getSheet().setDefaultColumnWidth(200);
                    // 单元格策略
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


                    // 创建字体实例
                    WriteFont cellWriteFont = new WriteFont();
                    // 设置字体大小
                    cellWriteFont.setFontName("宋体");
                    cellWriteFont.setFontHeightInPoints((short) 14);
                    //设置字体颜色
                    cellWriteFont.setColor(colorIndex);
                    //单元格颜色
                    contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                    contentWriteCellStyle.setWriteFont(cellWriteFont);

                    CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, contentWriteCellStyle);
                    //设置当前行第i列的样式
                    cell.getRow().getCell(i).setCellStyle(cellStyle);
                }
            }
        }
    }

}


