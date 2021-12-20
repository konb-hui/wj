package com.konb.wj.service.impl;

import com.konb.wj.config.FileConfig;
import com.konb.wj.exception.WjException;
import com.konb.wj.pojo.Image;
import com.konb.wj.mapper.ImageMapper;
import com.konb.wj.service.ImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.konb.wj.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-03-25
 */
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private FileConfig fileConfig;

    @Override
    public void upload(MultipartFile file, Image image) {
        File directory = new File(fileConfig.getImagePath().replace("file:", "") + image.getUid());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new WjException(302, "文件上传失败");
        }
        File saveImage = new File(directory.getAbsolutePath() + "\\" + System.currentTimeMillis() + "." + image.getName().split("\\.")[1]);
        FileUtils.copyUploadFile(file, saveImage);
        image.setName(saveImage.getName());
        image.setPath(saveImage.getAbsolutePath());
        image.setUrl(fileConfig.getImageUrl() + image.getUid() + "/" + saveImage.getName());
    }

    @Override
    public void deleteImageById(Image image) {
        String path = image.getPath();
        File file = new File(path);
        System.out.println(path);
        if (!file.exists() || !file.delete()) {
            throw new WjException(300, "删除图片出错");
        }
        this.baseMapper.deleteById(image.getId());
    }

    public static void main(String[] args) {
        File directory = new File("D:/logo/");
        System.out.println(directory.getAbsolutePath());
    }

}
