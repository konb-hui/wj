package com.konb.wj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.konb.wj.config.FileConfig;
import com.konb.wj.exception.WjException;
import com.konb.wj.pojo.Book;
import com.konb.wj.mapper.BookMapper;
import com.konb.wj.pojo.vo.BookQuery;
import com.konb.wj.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.konb.wj.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.regex.Matcher;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-02-24
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private FileConfig fileConfig;

    @Override
    public String upload(MultipartFile book, String fileName) {
        String path = fileConfig.getBookPath().replace("file:", "");
        File directory = new File(path);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new WjException(302, "文件上传失败");
        }
        File saveBook = new File(directory.getAbsolutePath() + "\\" + System.currentTimeMillis() + fileName);
        FileUtils.copyUploadFile(book, saveBook);
        String url = saveBook.getAbsolutePath().replace(path, fileConfig.getBookUrl()).replaceAll( Matcher.quoteReplacement(File.separator), "/");
        return url;
    }
}
