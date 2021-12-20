package com.konb.wj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.konb.wj.config.FileConfig;
import com.konb.wj.pojo.Directory;
import com.konb.wj.pojo.Jotter;
import com.konb.wj.mapper.JotterMapper;
import com.konb.wj.pojo.vo.FolderSelector;
import com.konb.wj.service.DirectoryService;
import com.konb.wj.service.JotterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-02-25
 */
@Service
public class JotterServiceImpl extends ServiceImpl<JotterMapper, Jotter> implements JotterService {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private FileConfig fileConfig;

    @Override
    public List<FolderSelector> getFolderSelector() {
        QueryWrapper<Jotter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 1);
        List<Jotter> list = this.baseMapper.selectList(queryWrapper);
        List<FolderSelector> data = new ArrayList<>();
        Map<String, FolderSelector> map = new HashMap<>();

        //遍历查询到的所有文件夹，并将其放到map中去
        for (Jotter jotter : list) {
            FolderSelector selector = new FolderSelector();
            BeanUtils.copyProperties(jotter, selector);
            map.put(jotter.getId(), selector);
        }

        //通过parent属性，给对应的FolderSelector的children赋值
        for (Jotter jotter : list) {
            if (!"0".equals(jotter.getParent())) {
                map.get(jotter.getParent()).getChildren().add(map.get(jotter.getId()));
            }
        }

        //将所有第一级目录重新放到list中去
        for (Map.Entry<String, FolderSelector> entry : map.entrySet()) {
            if (entry.getValue().getChildren().size() > 0) {
                data.add(entry.getValue());
            }
        }

        return data;
    }

    @Override
    public boolean add(Jotter jotter) {
        Directory directory = this.directoryService.getById(jotter.getParent());
        if (directory == null) {
            jotter.setUrl(fileConfig.getJotterUrl() + jotter.getName());
        } else {
            jotter.setUrl(directory.getPath() + jotter.getName());
        }
        String path = jotter.getUrl().replace(fileConfig.getJotterUrl(), fileConfig.getJotterPath());
        System.out.println(path);
        path = path.replaceAll("/", Matcher.quoteReplacement(File.separator));
        jotter.setPath(path);
        return SqlHelper.retBool(this.baseMapper.insert(jotter));
    }

    @Override
    public void recoverJotter(String id) {
        this.baseMapper.recoverJotter(id);
    }

    @Override
    public List<Jotter> getDeletedJotter() {
        return this.baseMapper.getDeletedJotter();
    }

}
