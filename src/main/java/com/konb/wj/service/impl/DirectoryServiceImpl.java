package com.konb.wj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.konb.wj.config.FileConfig;
import com.konb.wj.pojo.Directory;
import com.konb.wj.mapper.DirectoryMapper;
import com.konb.wj.pojo.tree.TreeNode;
import com.konb.wj.service.DirectoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-03-10
 */
@Service
public class DirectoryServiceImpl extends ServiceImpl<DirectoryMapper, Directory> implements DirectoryService {

    @Autowired
    private FileConfig fileConfig;

    private final static String NO_PARENT = "0";

    @Override
    public List<TreeNode> getTree(String uid) {

        QueryWrapper<Directory> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("uid", uid);


        //获取目录
       List<Directory> directories = this.baseMapper.selectList(queryWrapper);
       //保存为tree
        List<TreeNode> tree = new ArrayList<>();
        Map<String, TreeNode> map = new HashMap<>();
        directories.forEach(directory -> {
            TreeNode node = new TreeNode();
            node.setValue(directory.getId());
            node.setLabel(directory.getName());
            map.put(directory.getId(), node);
        });

        directories.forEach(directory -> {
            TreeNode parentNode = map.get(directory.getParent());
            TreeNode node = map.get(directory.getId());
            if (parentNode != null) {
                map.get(parentNode.getValue()).addNode(node);
            }
            if (NO_PARENT.equals(directory.getParent())) {
                tree.add(node);
            }
        });
        return tree;
    }

    @Override
    public String getUrl(Directory directory) {
        String baseUrl = fileConfig.getJotterUrl();
        String url;
        if (NO_PARENT.equals(directory.getParent())) {
            url = baseUrl + directory.getName() + "/";
        } else {
            Directory d = this.baseMapper.selectById(directory.getParent());
            url = d.getPath() + directory.getName() + "/";
        }
        return url;
    }
}
