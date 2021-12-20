package com.konb.wj.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.konb.wj.excel.listener.ExcelDataListener;
import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.Notice;
import com.konb.wj.pojo.User;
import com.konb.wj.pojo.result.R;
import com.konb.wj.pojo.vo.NoticeVo;
import com.konb.wj.service.ChangeBatchService;
import com.konb.wj.service.NoticeService;
import com.konb.wj.utils.JwtUtil;
import com.konb.wj.utils.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ChangeBatchService changeBatchService;

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("D:/xrk/2020-GY2335&GY2345&GY2417&GY2422-1号关于中东NOON客户订单的补充通知1 .doc"));
        HWPFDocument doc = new HWPFDocument(is);
        Range range = doc.getRange();
        TableIterator tableIterator = new TableIterator(range);
        Table table;
        TableRow row;
        TableCell cell;
        while (tableIterator.hasNext()) {
            table = tableIterator.next();
            int rowNum = table.numRows();
            for (int i = 0; i < rowNum; i ++) {
                row = table.getRow(i);
                int cellNum = row.numCells();
                for (int j = 0; j < cellNum; j ++) {
                    cell = row.getCell(j);
                    System.out.println(i + "-" + j + cell.text().trim());
                }
            }
        }
    }

    @PostMapping("upload/{name}")
    public R upload(MultipartFile file, HttpServletRequest request, @PathVariable String name) {
        User user = JwtUtil.getUserByJwtToken(request);
        if (user == null) {
            return R.error().message("请先登录");
        }

        Notice notice = this.noticeService.beforeParseNoticeExcel(file, name);

        this.noticeService.parseNoticeExcel(notice);

        return R.ok();
    }

    @PostMapping("uploadByPath")
    public R uploadByPath(@RequestBody NoticeVo noticeVo) {
        System.out.println(noticeVo.getExcelPath());
        File noticeDirectory = new File(noticeVo.getExcelPath());
        this.noticeService.parseNoticeDirectory(noticeDirectory);
        return R.ok();
    }

    @DeleteMapping("deleteAll")
    public R deleteAll() {
        this.noticeService.deleteAll();
        return R.ok();
    }

    @GetMapping("getErrorNotice")
    public R getErrorNotice() {
        List<Notice> errorNotices = this.noticeService.getErrorNotice();
        return R.ok().data("data", errorNotices);
    }

}

