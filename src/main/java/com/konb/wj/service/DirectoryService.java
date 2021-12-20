package com.konb.wj.service;

import com.konb.wj.pojo.Directory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.konb.wj.pojo.tree.TreeNode;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-03-10
 */
public interface DirectoryService extends IService<Directory> {

    /**
     * 获得当前用户对应的文件夹数
     * @param uid 用户ID
     * @return List<TreeNode>
     */
    List<TreeNode> getTree(String uid);

    /**
     * 获取文件夹路径
     * @param directory 文件夹对象
     * @return String
     */
    String getUrl(Directory directory);
}
