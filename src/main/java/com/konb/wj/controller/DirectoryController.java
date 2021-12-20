package com.konb.wj.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.konb.wj.pojo.Directory;
import com.konb.wj.pojo.Jotter;
import com.konb.wj.pojo.User;
import com.konb.wj.pojo.result.R;
import com.konb.wj.pojo.tree.TreeNode;
import com.konb.wj.service.DirectoryService;
import com.konb.wj.service.JotterService;
import com.konb.wj.utils.JwtUtil;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private JotterService jotterService;

    @PostMapping("/add")
    public R add(@RequestBody Directory directory, HttpServletRequest request) {

        User user = JwtUtil.getUserByJwtToken(request);

        if (user == null) {
            return R.error().message("请先登录");
        }

        QueryWrapper<Directory> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("parent", directory.getParent())
                .eq("name", directory.getName());
        Directory d = this.directoryService.getOne(queryWrapper);
        if (d != null) {
            return R.error().message("文件夹已存在");
        }
        System.out.println(user);
        directory.setUid(user.getId());
        directory.setPath(this.directoryService.getUrl(directory));

        this.directoryService.save(directory);

        return R.ok();
    }

    @GetMapping("getDirectoryTree")
    public R getDirectoryTree(HttpServletRequest request) {
        User user = JwtUtil.getUserByJwtToken(request);
        List<TreeNode> root = new ArrayList<>();
        TreeNode node = new TreeNode();
        node.setLabel("Home");
        node.setValue("0");
        List<TreeNode> tree = this.directoryService.getTree(user.getId());
        node.setChildren(tree);
        root.add(node);
        return R.ok().data("tree", root);
    }

    @GetMapping("getDirectoryByParent/{parent}")
    public R getDirectoryByParent(@PathVariable String parent, HttpServletRequest request) {
        User user = JwtUtil.getUserByJwtToken(request);
        if (user == null) {
            return R.error().message("请先登录");
        }

        QueryWrapper<Directory> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("parent", parent)
                .eq("uid", user.getId());

        QueryWrapper<Jotter> jotterQueryWrapper = new QueryWrapper<>();
        jotterQueryWrapper
                .eq("parent", parent)
                .eq("uid", user.getId());

        List<Jotter> jotterList = this.jotterService.list(jotterQueryWrapper);

        List<Directory> directoryList = this.directoryService.list(queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("directory", directoryList);
        data.put("jotter", jotterList);
        return R.ok().data("data", data);
    }

    @GetMapping("getDirectoryIds/{parent}")
    public R getDirectoryIds(@PathVariable String parent) {
        QueryWrapper<Directory> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", parent);
        Directory directory = this.directoryService.getOne(queryWrapper);
        List<String> idList = new ArrayList<>();
        idList.add("0");
        String[] idArray = new String[0];
        if (directory != null) {
            idList.add(directory.getId());
            while (!"0".equals(directory.getParent())) {
                directory = this.directoryService.getById(directory.getParent());
                idList.add(directory.getId());
            }
            idArray = idList.toArray(new String[0]);
            ArrayUtils.reverse(idList.toArray());
        }
        return R.ok().data("list", idArray);
    }

}

