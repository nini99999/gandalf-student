package com.poshist.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtils {
    public static void  toExcel(String[] titles, List<Object[]> dates, OutputStream out) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
        Sheet sheet = wb.createSheet(" sheet1");
        Row row = sheet.createRow(0);
        for(int i=0;i<titles.length;i++){
            row.createCell(i).setCellValue(titles[i]);
        }
        for(int i=0;i<dates.size();i++){
            row = sheet.createRow(i+1);
            for(int ii=0;ii<dates.get(i).length;ii++){
                if(null!=dates.get(i)[ii]) {
                    row.createCell(ii).setCellValue(dates.get(i)[ii].toString());
                }else {
                    row.createCell(ii).setCellValue("");
                }
            }
        }
        wb.write(out);
    }

    public static Workbook getWorkBook(MultipartFile file) throws IOException {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;

            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                //2007
                workbook = new XSSFWorkbook(is);
            }

        return workbook;

    }
}
