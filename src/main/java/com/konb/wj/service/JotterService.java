package com.konb.wj.service;

import com.konb.wj.pojo.Jotter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.konb.wj.pojo.vo.FolderSelector;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-02-25
 */
public interface JotterService extends IService<Jotter> {

    /**
     * 获取用于显示的笔记结构
     * @return List<FolderSelector>
     */
    List<FolderSelector> getFolderSelector();

    /**
     * 新建一个笔记
     * @param jotter 笔记对象
     * @return boolean
     */
    boolean add(Jotter jotter);

    /**
     * 还原已删除的笔记
     * @param id 笔记的ID
     */
    void recoverJotter(String id);

    /**
     * 获取删除的笔记
     * @return List<Jotter>
     */
    List<Jotter> getDeletedJotter();
}
