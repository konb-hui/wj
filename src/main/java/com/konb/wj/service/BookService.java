package com.konb.wj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.konb.wj.pojo.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.konb.wj.pojo.vo.BookQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-02-24
 */
public interface BookService extends IService<Book> {
    /**
     * 书籍文件上传
     * @param book book对象
     * @param fileName 上传的文件名
     * @return String
     */
    String upload(MultipartFile book, String fileName);
}
