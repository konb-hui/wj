package com.konb.wj.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.konb.wj.config.FileConfig;
import com.konb.wj.excel.listener.ExcelDataListener;
import com.konb.wj.exception.WjException;
import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.Notice;
import com.konb.wj.mapper.NoticeMapper;
import com.konb.wj.service.ChangeBatchService;
import com.konb.wj.service.ChangeTypeBatchService;
import com.konb.wj.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.konb.wj.utils.ExcelUtils;
import com.konb.wj.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private ChangeBatchService changeBatchService;

    @Autowired
    private ChangeTypeBatchService changeTypeBatchService;


    private String excelPath;

    @Override
    public Notice beforeParseNoticeExcel(MultipartFile file, String name) {
        File directory = new File(getExcelPath());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new WjException(302, "文件上传失败");
        }

        File saveExcel = new File(directory.getAbsolutePath() + "\\" + name);
        FileUtils.copyUploadFile(file, saveExcel);

        return beforeParseNoticeExcel(saveExcel);
    }

    private Notice beforeParseNoticeExcel(File excelFile) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", excelFile.getName());
        Notice notice = this.getOne(queryWrapper);
        if (notice != null) {
            throw new WjException(302, "已存在相同文件");
        }
        notice = new Notice();
        notice.setTemplate(excelFile.getAbsolutePath());
        notice.setPath(excelFile.getAbsolutePath());
        notice.setName(excelFile.getName());
        notice.setUrl(fileConfig.getJotterUrl() + notice.getName());
        return notice;
    }

    @Override
    public void parseNoticeExcel(Notice notice) {
        System.out.println("---------------------------" + notice.getTemplate());
        List<ChangeBatch> list = new ArrayList<>();
        ExcelDataListener excelDataListener = new ExcelDataListener(list);
        EasyExcel
                .read(notice.getTemplate(), excelDataListener)
                .extraRead(CellExtraTypeEnum.MERGE)
                .sheet()
                .headRowNumber(7)
                .doRead();

        Notice tempNotice = excelDataListener.getNotice();
        notice.setTemplate(tempNotice.getTemplate());
        notice.setNoticeTime(tempNotice.getNoticeTime());
        this.save(notice);
        this.changeBatchService.saveChangeBatches(notice.getId(), list);
    }

    @Override
    public void parseNoticeDirectory(File noticeDirectory) {

        if (! noticeDirectory.exists() || ! noticeDirectory.isDirectory()) {
            throw new WjException(300, "当前文件夹不存在");
        }
        File[] notices = noticeDirectory.listFiles();

        if (notices == null || notices.length < 1) {
            return;
        }

        for (File notice : notices) {

            if (notice.isDirectory()) {
                this.parseNoticeDirectory(notice);
            }

            //非excel文件和补充通知格式的无视
            if (!ExcelUtils.isExcelFile(notice) || !ExcelUtils.isChangeNotice(notice)) {
                continue;
            }
            File newFile = FileUtils.copyFileToDirectory(notice, getExcelPath());
            Notice n = new Notice();
            try {
                n = this.beforeParseNoticeExcel(newFile);
            } catch (WjException e) {
                if (e.getCode() == 302) {
                    continue;
                }
            }
            this.parseNoticeExcel(n);
        }

    }

    @Override
    public void deleteAll() {
        File excelDirectory = new File(getExcelPath());
        File[] excelFiles = excelDirectory.listFiles();
        assert excelFiles != null;
        for (File file : excelFiles) {
            if (! file.delete()) {
                throw new WjException(300, "文件删除失败");
            }
        }
        this.changeBatchService.remove(null);
        this.changeTypeBatchService.remove(null);
        this.remove(null);
    }

    @Override
    public List<Notice> getErrorNotice() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("template")
                .or()
                .isNull("notice_time");
        return null;
    }

    private String getExcelPath() {
        if (StringUtils.isEmpty(excelPath)) {
            excelPath = fileConfig.getJotterPath().replace("file:", "");
        }
        return excelPath;
    }
}
