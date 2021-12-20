package com.konb.wj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.konb.wj.pojo.LibraryType;
import com.konb.wj.pojo.result.R;
import com.konb.wj.service.LibraryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/libraryType")
public class LibraryTypeController {

    @Autowired
    private LibraryTypeService libraryTypeService;

    @GetMapping("list")
    public R list() {
        List<LibraryType> data = this.libraryTypeService.list(null);
        return R.ok().data("items", data);
    }

    @PostMapping("add")
    public R add(@RequestBody(required = false)LibraryType libraryType) {
        QueryWrapper<LibraryType> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("name", libraryType.getName());

        LibraryType type = this.libraryTypeService.getOne(queryWrapper);
        if (type != null) {
            return R.error().message("当前类型已存在");
        }
        boolean add = this.libraryTypeService.save(libraryType);
        if (!add) {
            return R.error().message("添加失败");
        }

        return R.ok();
    }

}

