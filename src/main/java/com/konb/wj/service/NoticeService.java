package com.konb.wj.service;

import com.konb.wj.pojo.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 通知单文件解析前对上传的文件进行处理
     * @param file 上传的通知单文件
     * @param name 文件名
     * @return notice
     */
    Notice beforeParseNoticeExcel(MultipartFile file, String name);

    /**
     * 传入beforeParseNoticeExcel处理后的结果，进行解析和保存
     * @param notice beforeParseNoticeExcel返回对象
     */
    void parseNoticeExcel(Notice notice);

    /**
     * 通过指定通知当保存路径解析通知单
     * @param noticeDirectory 通知单保存路径
     */
    void parseNoticeDirectory(File noticeDirectory);

    /**
     * 删除所有通知单及其记录，测试用
     */
    void deleteAll();

    /**
     * 获得数据库中格式异常的通知单
     * @return List<Notice>
     */
    List<Notice> getErrorNotice();
}
