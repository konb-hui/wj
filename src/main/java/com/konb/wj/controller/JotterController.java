package com.konb.wj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.konb.wj.pojo.Jotter;
import com.konb.wj.pojo.User;
import com.konb.wj.pojo.result.R;

import com.konb.wj.pojo.vo.JotterVo;
import com.konb.wj.service.JotterService;
import com.konb.wj.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-02-25
 */
@RestController
@RequestMapping("/jotter")
public class JotterController {

    @Autowired
    private JotterService jotterService;

    @PostMapping("add")
    public R add(@RequestBody(required = false) Jotter jotter, HttpServletRequest request) {
        User user = JwtUtil.getUserByJwtToken(request);
        if (user == null) {
            return R.ok().message("请先登录");
        }

        jotter.setUid(user.getId());

        boolean add = this.jotterService.add(jotter);

        if (!add) {
            return R.error().message("新建失败");
        }

        return R.ok().message("新建成功");
    }

    @GetMapping("getJotter/{id}")
    public R getJotter(@PathVariable String id) {
        Jotter jotter = this.jotterService.getById(id);
        if (jotter == null) {
            return R.error().message("文件不存在");
        }
        return R.ok().data("jotter", jotter);
    }

    @PostMapping("update")
    public R update(@RequestBody(required = false) Jotter jotter) {
        boolean update = this.jotterService.updateById(jotter);
        if (!update) {
            return R.error().message("更新失败");
        }
        return R.ok().message("更新成功");
    }

    @PostMapping("getNewJotter")
    public R getNewJotter(@RequestBody(required = false)JotterVo jotterVo) {
        QueryWrapper<Jotter> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .orderByDesc("edit_time")
                .like("content",jotterVo.getKey())
                .or()
                .like("name", jotterVo.getKey())
                .eq("is_deleted", 0);
        IPage<Jotter> page = new Page<>();
        page.setSize(jotterVo.getSize());
        page.setCurrent(jotterVo.getCurrent());
        this.jotterService.page(page, queryWrapper);
        return R.ok().data("page", page);
    }

    @GetMapping("delete/{id}")
    public R delete(@PathVariable String id) {
        boolean delete = this.jotterService.removeById(id);
        if (! delete) {
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @GetMapping("getDeletedJotter")
    public R getDeletedJotter() {
        List<Jotter> list = this.jotterService.getDeletedJotter();
        return R.ok().data("list", list);
    }

    @GetMapping("recoverJotter/{id}")
    public R recoverJotter(@PathVariable String id) {
        this.jotterService.recoverJotter(id);
        return R.ok().message("恢复成功");
    }

}

