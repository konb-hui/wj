package com.konb.wj.service;

import com.konb.wj.pojo.Image;
import com.baomidou.mybatisplus.extension.service.IService;
import com.konb.wj.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-03-25
 */
public interface ImageService extends IService<Image> {

    /**
     * 上传图片
     * @param file 图片
     * @param image 图片对象
     */
    void upload(MultipartFile file, Image image);

    /**
     * 删除图片
     * @param image 图片对象
     */
    void deleteImageById(Image image);
}
