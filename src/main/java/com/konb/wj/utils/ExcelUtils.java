package com.konb.wj.utils;

import com.alibaba.excel.EasyExcel;
import com.konb.wj.excel.listener.ExcelSearchListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author konb
 */
public class ExcelUtils {

    public static final String XSL_EXCEL_SUFFIX = ".xls";

    public static final String XSLX_EXCEL_SUFFIX = ".xlsx";

    public static final String NOTICE_PATTEN = ".*补充通知（\\d{3,}）\\.(xls|xlsx)$";

    public static boolean isExcelFile(File file) {
        String name = file.getName();
        return name.endsWith(XSL_EXCEL_SUFFIX) || name.endsWith(XSLX_EXCEL_SUFFIX);
    }

    public static boolean isChangeNotice(File file) {
        String name = file.getName();
        return Pattern.matches(NOTICE_PATTEN, name);
    }

    public static void main(String[] args) {
        String directoryPath = "D:/测评报告";
        String key = "水平串扰";
        File directory = new File(directoryPath);
        File[] reports = directory.listFiles();
        assert reports != null;
        for (File report : reports) {
            EasyExcel
                    .read(report.getAbsolutePath(), new ExcelSearchListener(key, report.getAbsolutePath()))
                    .sheet("问题追踪")
                    .doRead();
        }
    }

    public static void saveData(String line) throws IOException {
        File file = new File("D:/excelData.txt");
        if (! file.exists() && file.createNewFile()) {
            return;
        }
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(line);
        fileWriter.flush();
        fileWriter.close();
    }

}
