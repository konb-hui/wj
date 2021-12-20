package com.konb.wj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.konb.wj.pojo.Book;
import com.konb.wj.pojo.result.R;
import com.konb.wj.pojo.vo.BookQuery;
import com.konb.wj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-02-24
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/list/{tid}/{current}/{size}")
    public R list(@PathVariable String tid, @PathVariable Integer current,
                  @PathVariable Integer size, @RequestBody(required = false) BookQuery bookQuery) {

        String key = bookQuery.getKey();

        IPage<Book> books = new Page<>(current, size);

        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(wrapper -> wrapper
                    .like("title", key)
                    .or()
                    .like("author", key));
        }
        if (StringUtils.isNotEmpty(tid) && !"0".equals(tid)) {
            queryWrapper.eq("tid", tid);
        }

        this.bookService.page(books, queryWrapper);
        return R.ok().data("books", books);
    }

    @PostMapping("addOrUpdate")
    public R add(@RequestBody(required = false) Book book) {
        boolean saveOrUpdate = this.bookService.saveOrUpdate(book);
        if (!saveOrUpdate) {
            return R.error();
        }
        return R.ok();
    }

    @PostMapping("upload")
    public R upload(MultipartFile book, HttpServletRequest request) {
        String fileName = request.getHeader("fileName");
        System.out.println(book.getName());
        String url = this.bookService.upload(book, fileName);
        System.out.println(url);
        return R.ok().data("url", url);
    }

    public static void main(String[] args) {
        while (true) {
            Integer b = f();
        }
    }

    public static Integer f () {
        Integer a = 999;
        return a;
    }


}

