package org.muses.jeeplatform.core.excel;

import org.apache.poi.hssf.usermodel.*;
import org.muses.jeeplatform.util.DateUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Nicky on 2017/8/1 0001.
 */
public class ExcelViewWrite extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        Date date = new Date();
        String filename = DateUtils.formatDate(date, "yyyyMMddHHmmss");
        HSSFSheet sheet;
        HSSFCell cell;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
        sheet = workbook.createSheet("sheet1");

        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = workbook.createFont();	//标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short)11);
        headerStyle.setFont(headerFont);
        int width = 20;short height=25*20;
        sheet.setDefaultColumnWidth(width);
        for(int i=0; i<len; i++){ //设置标题
            String title = titles.get(i);
            cell = getCell(sheet, 0, i);
            cell.setCellStyle(headerStyle);
            setText(cell,title);
        }
        sheet.getRow(0).setHeight(height);

        HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        List<HashMap<String,Object>> varList = (List<HashMap<String,Object>>) model.get("values");
        int varCount = varList.size();
        for(int i=0; i<varCount; i++){
            HashMap<String,Object> vpd = varList.get(i);
            for(int j=0;j<len;j++){
                String varstr = vpd.get("var"+(j+1)) != null ? vpd.get("var"+(j+1)).toString() : "";
                cell = getCell(sheet, i+1, j);
                cell.setCellStyle(contentStyle);
                setText(cell,varstr);
            }
        }
    }

   /* @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Date date = new Date();
        String filename = DateUtils.formatDate(date, "yyyyMMddHHmmss");
        response.setContentType("application/ms-excel");//or application/octet-stream
        response.setHeader("Content-Disposition", "attachment;filename="+filename+".xls");
        OutputStream outputStream = response.getOutputStream();

        HSSFSheet sheet;
        HSSFWorkbook hssfWorkbook =  (HSSFWorkbook)workbook;
        sheet = hssfWorkbook.createSheet();

        HSSFCellStyle headerStyle = hssfWorkbook.createCellStyle(); //标题样式
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont = hssfWorkbook.createFont();	//标题字体
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short)11);
        headerStyle.setFont(headerFont);

        int width = 20;short height=25*20;
        sheet.setDefaultColumnWidth(width);
        sheet.getRow(0).setHeight(height);

        HSSFCellStyle contentStyle = hssfWorkbook.createCellStyle(); //内容样式
        contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFRow row = sheet.createRow(0);
        List<String> titles = (List<String>) model.get("titles");
        int len = titles.size();
        for(int i=0; i<len; i++){ //设置标题
            String title = titles.get(i);
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(title);
        }
        //sheet.getRow(0).setHeight(height);

        List<HashMap<String,Object>> varList = (List<HashMap<String,Object>>) model.get("values");
        int varCount = varList.size();
        for(int i=0; i<varCount; i++){
            HashMap<String,Object> vpd = varList.get(i);
            for(int j=0;j<len;j++){
                String varstr = vpd.get("var"+(j+1)) != null ? vpd.get("var"+(j+1)).toString() : "";
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(contentStyle);
                cell.setCellValue(varstr);
            }
        }

        hssfWorkbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }*/
}
