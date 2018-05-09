package com.example;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    //创建一个Excel工作簿
    @Test
    public void test() throws IOException {

        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel工作蒲.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();
    }

    //创建一个sheet页
    @Test
    public void sheet() throws IOException {
        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建sheet页面
        wb.createSheet("第一个sheet页面");
        wb.createSheet("第二个sheet页面");

        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel带sheet.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();
    }

    //创建行和列
    @Test
    public void row() throws IOException {
        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建一个sheet页面
        Sheet sheet = wb.createSheet("学生信息sheet");
        //创建一行
        Row row = sheet.createRow(0);
        //创建一个单元格
        Cell cell = null;
        for(int i=0;i<5;i++){
            row.createCell(i).setCellValue("写入信息:单元格内容"+i);
        }

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel创建row.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        wb.close();
    }

    //创建带时间样式的
    @Test
    public void date() throws IOException {
        //定义一个工作蒲
        Workbook wb = new HSSFWorkbook();
        //创建sheet页面
        Sheet sheet = wb.createSheet("时间sheet页");
        //创建一行
        Row row = sheet.createRow(0);
        //创建一个单元格
        Cell cell = row.createCell(0);
        cell.setCellValue(new Date());

        CreationHelper creationHelper = wb.getCreationHelper();
        //设置单元格样式
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("YYYY-MM-DD hh:mm:ss"));
        cell = row.createCell(1);
        cell.setCellValue(new Date());
        //设置日期样式
        cell.setCellStyle(cellStyle);

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel带日期格式.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();
    }

    //合并单元格
    @Test
    public void test1() throws IOException {
        //定义一个工作蒲
        Workbook wb = new HSSFWorkbook();
        //创建sheet页面
        Sheet sheet = wb.createSheet("第一个sheet");

        //创建一行
        Row row = sheet.createRow(1);
        //设置行高
        row.setHeightInPoints(30);

        //创建一个单元格
        Cell cell = row.createCell(1);
        cell.setCellValue("合并单元格");

        //合并单元格（起始行,结束行,起始列,结束列）
        sheet.addMergedRegion(new CellRangeAddress(1,2,1,2));

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel合并单元格.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();
    }


    //设置行高及单元格对齐方式，字体、单元格背景色等单元格样式

    //注意新版本 中设置对齐方式 HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_BOTTOM 已经被弃用
    //新版本使用 HorizontalAlignment.CENTER,VerticalAlignment.CENTER 分别代表水平和垂直
    // 可通过 IndexedColors.YELLOW.getIndex()获取各种颜色的short 值

    @Test
    public void style() throws IOException {
        //定义一个工作蒲
        Workbook wb = new HSSFWorkbook();
        //创建sheet页面
        Sheet sheet = wb.createSheet("第一个sheet");

        //创建一行
        Row row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(30);

        //创建单元格
        createCell(wb,row,(short)0,HorizontalAlignment.CENTER,VerticalAlignment.CENTER);
        createCell(wb,row,(short)1,HorizontalAlignment.CENTER,VerticalAlignment.BOTTOM);
        createCell(wb,row,(short)2,HorizontalAlignment.LEFT,VerticalAlignment.BOTTOM);

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel样式.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();

    }
    private static void createCell(Workbook workbook, Row row, short column,HorizontalAlignment halign,VerticalAlignment valign){
        Cell cell = row.createCell(column);//创建单元格
        CellStyle cellStyle = workbook.createCellStyle();//创建样式

        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));  //设置为货币格式

        cell.setCellValue(new HSSFRichTextString("我是富文本"));//设置值

        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont(); //设置字体样式
        font.setColor((short)10);  //设置字体颜色
        font.setBold(true);  //设置为粗体
        font.setFontHeightInPoints((short)14); //设置字体大小
        font.setFontName("黑体"); //设置字体样式
        cellStyle.setFont(font);

        cellStyle.setBorderRight(BorderStyle.THIN); //设置边框样式
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        cellStyle.setAlignment(halign);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(valign);//设置单元格垂直方向对其方式

        cell.setCellStyle(cellStyle);

    }

    //合并单元格边框问题解决
    //合并单元格边框样式使用 RegionUtil 来设置
    //必须同时设置第一个单元格的边框样式
    @Test
    public void mergeCells() throws IOException {

        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建一个sheet
        Sheet sheet = wb.createSheet("基因检测收入明细");
        //创建行
        Row row = sheet.createRow(3);

        //创建单元格
        Cell cell = row.createCell(1);
        cell.setCellValue("江苏华生基因数据合并测试");

        //设置单元格样式
        CellStyle cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式

        //设置第一个单元格边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderTop(BorderStyle.THIN); //设置边框样式
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色


        //合并单元格（起始行,结束行,起始列,结束列）
        CellRangeAddress cra = new CellRangeAddress(3,4,1,2); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
        //设置合并单元格的边框样式
        RegionUtil.setBorderBottom(BorderStyle.THIN,cra,sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN,cra,sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,cra,sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,cra,sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);

        cell.setCellStyle(cellStyle);

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/mergeCells.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();
    }

    @Test
    //Excel公式 基本计算
    //采坑 ： cell.setCellFormula("SUM(C2:C4)" ); C2和C4 单元格从1开始而不是从0开始
    public void calculate() throws IOException {
        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建sheet页面
        Sheet sheet = wb.createSheet("第一个sheet");

        //创建第一行
        Row row = sheet.createRow(1);
        //创建单元格
        Cell cell = row.createCell(1);
        cell.setCellValue("A =" );
        cell = row.createCell(2);
        cell.setCellValue(2);

        //创建第二行
        row = sheet.createRow(2);
        //创建单元格
        cell = row.createCell(1);
        cell.setCellValue("B =");
        cell = row.createCell(2);
        cell.setCellValue(4);

        //创建第三行
        row = sheet.createRow(3);
        //创建单元格
        cell = row.createCell(1);
        cell.setCellValue("C =");
        cell = row.createCell(2);
        cell.setCellValue(6);

        //创建第三行
        row = sheet.createRow(4);
        //创建单元格
        cell = row.createCell(1);
        cell.setCellValue("Total =");
        //计算 和
        cell = row.createCell(2);
        // Create SUM formula

        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula("SUM(C2:C4)" );

//        cell = row.createCell(3);
//        cell.setCellValue("SUM(C2:C3)");

//        row = sheet.createRow(4);
//        cell = row.createCell(1);
//        cell.setCellValue("POWER =");
//        cell=row.createCell(2);
//        // Create POWER formula
//        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//        cell.setCellFormula("POWER(C2,C3)");
//        cell = row.createCell(3);
//        cell.setCellValue("POWER(C2,C3)");
//        row = sheet.createRow(5);
//        cell = row.createCell(1);
//        cell.setCellValue("MAX =");
//        cell = row.createCell(2);
//        // Create MAX formula
//        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//        cell.setCellFormula("MAX(C2,C3)");
//        cell = row.createCell(3);
//        cell.setCellValue("MAX(C2,C3)");
//        row = sheet.createRow(6);
//        cell = row.createCell(1);
//        cell.setCellValue("FACT =");
//        cell = row.createCell(2);
//        // Create FACT formula
//        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//        cell.setCellFormula("FACT(C3)");
//        cell = row.createCell(3);
//        cell.setCellValue("FACT(C3)");
//        row = sheet.createRow(7);
//        cell = row.createCell(1);
//        cell.setCellValue("SQRT =");
//        cell = row.createCell(2);
//        // Create SQRT formula
//        cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
//        cell.setCellFormula("SQRT(C5)");
//        cell = row.createCell(3);
//        cell.setCellValue("SQRT(C5)");
        wb.getCreationHelper()
                .createFormulaEvaluator()
                .evaluateAll();
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/Excel公式.xls");
        wb.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("fromula.xlsx written successfully");

    }

    @Test
    //实现基因检测业务日报表
    public void daily() throws IOException {
        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建一个sheet
        Sheet sheet = wb.createSheet("基因检测业务日报表");

        //创建标题行
        Row row = sheet.createRow(0);
        //创建单元格
        Cell cell = row.createCell(0);
        cell.setCellValue("基因检测业务日报表—总表");

        //设置单元格样式
        CellStyle cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        Font font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)14);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        CellRangeAddress cra = new CellRangeAddress(0,0,0,11);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //创建日期行
        row = sheet.createRow(1);
        //创建单元格
        cell = row.createCell(0);
        cell.setCellValue("2018年4月25日");
        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(1,1,0,11);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //创建第三行-头部
        row = sheet.createRow(2);
        //创建单元格
        cell = row.createCell(0);
        cell.setCellValue("序号");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,3,0,0);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //地区
        cell = row.createCell(1);
        cell.setCellValue("地区");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,3,1,1);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //订单编号
        cell = row.createCell(2);
        cell.setCellValue("订单编号");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,3,2,2);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //收入
        cell = row.createCell(3);
        cell.setCellValue("收入");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,2,3,5);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //成本
        cell = row.createCell(6);
        cell.setCellValue("成本");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,2,6,7);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //姓名
        cell = row.createCell(8);
        cell.setCellValue("姓名");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(2,3,8,8);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        //创建第四行-头部
        row = sheet.createRow(3);
        //创建单元格
        cell = row.createCell(3);
        cell.setCellValue("订单金额（元）");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellValue("到账金额（元）");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(5);
        cell.setCellValue("开票金额（元）");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("检验成本（元）");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(7);
        cell.setCellValue("积分（元）");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        //录入数据
        row = sheet.createRow(4);

        cell = row.createCell(0);
        cell.setCellValue(1);

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(1);
        cell.setCellValue("华东");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        //合并单元格（起始行,结束行,起始列,结束列）
        cra = new CellRangeAddress(4,6,1,1);
        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("GTON-20180410-1111");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        setCell(row,3,2400,wb,sheet);
        setCell(row,4,2400,wb,sheet);
        setCell(row,5,2400,wb,sheet);
        setCell(row,6,1400,wb,sheet);
        setCell(row,7,100,wb,sheet);

        row = sheet.createRow(5);

        cell = row.createCell(0);
        cell.setCellValue(2);

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("GTON-20180411-1123");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        setCell(row,3,3500,wb,sheet);
        setCell(row,4,3500,wb,sheet);

        row = sheet.createRow(6);

        cell = row.createCell(0);
        cell.setCellValue(3);

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue("GTON-20180412-431a");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        setCell(row,3,2400,wb,sheet);

        //总计行
        row = sheet.createRow(7);

        cell = row.createCell(0);
        cell.setCellValue("小计");

        //设置单元格样式
        cellStyle = wb.createCellStyle();

        //居中
        cellStyle.setAlignment(HorizontalAlignment.LEFT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(3);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula("SUM(D5:D7)" );


        //设置单元格样式
        cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("￥#,##0"));
        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        cell = row.createCell(4);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula("SUM(E5:E7)" );


        //设置单元格样式
        cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(wb.createDataFormat().getFormat("￥#,##0"));
        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/daily.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();

    }

    public static void setCell(Row row,int cellnum,double cellvalue,Workbook workbook,Sheet sheet){
        Cell cell = row.createCell(cellnum);

        //设置单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell.setCellValue(cellvalue);

        //居中
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式

        Font font = workbook.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)10);
        font.setFontName("黑体");
        cellStyle.setFont(font);

//        //合并单元格（起始行,结束行,起始列,结束列）
//        CellRangeAddress cra = new CellRangeAddress(2,3,0,0);
//        sheet.addMergedRegion(cra);

        cell.setCellStyle(cellStyle);
    }

    @Test
    //实现财务报表
    public void practice() throws IOException {

        //定义一个工作簿
        Workbook wb = new HSSFWorkbook();
        //创建一个sheet
        Sheet sheet = wb.createSheet("基因检测收入明细");

        //创建标题行
        Row row = sheet.createRow(1);

        //创建一个单元格
        Cell cell = row.createCell(0);
        cell.setCellValue("江苏华生基因数据科技股份有限公司基因检测收入明细(0424)");

        //合并单元格（起始行,结束行,起始列,结束列）
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,13));
        //居中
        CellStyle cellStyle = wb.createCellStyle();//创建样式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        Font font = wb.createFont(); //设置字体样式
        font.setBold(true);
        font.setFontHeightInPoints((short)16);
        font.setFontName("宋体");
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);

        //创建第三行
        row = sheet.createRow(3);

        //创建一个单元格
        cell = row.createCell(3);
        cell.setCellValue("检测报告");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)11);
        font.setFontName("宋体");
        cellStyle.setFont(font);

        //设置第一个单元格边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderTop(BorderStyle.THIN); //设置边框样式
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderBottom(BorderStyle.THIN); //设置边框样式
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        //合并单元格（起始行,结束行,起始列,结束列）
        CellRangeAddress cra = new CellRangeAddress(3,3,3,5); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);
        //设置合并单元格的边框样式
        RegionUtil.setBorderBottom(BorderStyle.THIN,cra,sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN,cra,sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,cra,sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,cra,sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(),cra,sheet);

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(6);
        cell.setCellValue("缴款方式（地服转存、银行转账、微信、支付宝）");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)11);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置第一个单元格边框样式
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderTop(BorderStyle.THIN); //设置边框样式
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        //合并单元格（起始行,结束行,起始列,结束列）
        CellRangeAddress cra1 = new CellRangeAddress(3,4,6,6); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra1);
        //设置合并单元格的边框样式
        RegionUtil.setBorderBottom(BorderStyle.THIN,cra1,sheet);
        RegionUtil.setBottomBorderColor(IndexedColors.BLACK.getIndex(),cra1,sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN,cra1,sheet);
        RegionUtil.setLeftBorderColor(IndexedColors.BLACK.getIndex(),cra1,sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN,cra1,sheet);
        RegionUtil.setTopBorderColor(IndexedColors.BLACK.getIndex(),cra1,sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN,cra1,sheet);
        RegionUtil.setRightBorderColor(IndexedColors.BLACK.getIndex(),cra1,sheet);

        cell.setCellStyle(cellStyle);

        //创建第四行
        row = sheet.createRow(4);
        //创建一个单元格
        cell = row.createCell(0);
        cell.setCellValue("发票代码");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)9);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(1);
        cell.setCellValue("发票号码");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)9);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(2);
        cell.setCellValue("购方企业名称");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)9);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(3);
        cell.setCellValue("姓名");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)11);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置第一个单元格边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderRight(BorderStyle.THIN); //设置边框样式
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderBottom(BorderStyle.THIN); //设置边框样式
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(4);
        cell.setCellValue("年龄");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)11);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置第一个单元格边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderRight(BorderStyle.THIN); //设置边框样式
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderBottom(BorderStyle.THIN); //设置边框样式
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        cell.setCellStyle(cellStyle);

        //创建一个单元格
        cell = row.createCell(5);
        cell.setCellValue("检测编号");

        cellStyle = wb.createCellStyle();//创建样式

        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置单元格垂直方向对其方式
        font = wb.createFont(); //设置字体样式
        font.setFontHeightInPoints((short)11);
        font.setColor(IndexedColors.RED.getIndex());  //设置字体颜色
        font.setFontName("宋体");
        cellStyle.setFont(font);
        //设置背景色
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置第一个单元格边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderRight(BorderStyle.THIN); //设置边框样式
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderBottom(BorderStyle.THIN); //设置边框样式
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        cell.setCellStyle(cellStyle);

        cell = row.createCell(6);
        cell.setCellValue("");

        cellStyle = wb.createCellStyle();//创建样式

        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色
        cellStyle.setBorderBottom(BorderStyle.THIN); //设置边框样式
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); //设置边框颜色

        cell.setCellStyle(cellStyle);

        //定义一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./src/main/resources/static/excels/财务报表.xls");
        //写入在输出流
        wb.write(fileOutputStream);
        //关闭输出流
        fileOutputStream.close();

    }

}





