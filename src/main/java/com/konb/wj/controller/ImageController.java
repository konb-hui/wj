package com.konb.wj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.konb.wj.exception.WjException;
import com.konb.wj.pojo.Image;
import com.konb.wj.pojo.User;
import com.konb.wj.pojo.result.R;
import com.konb.wj.service.ImageService;
import com.konb.wj.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-03-25
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("add/{name}")
    public R add(MultipartFile file, HttpServletRequest request, @PathVariable String name) {
        Image image = new Image();
        User user = JwtUtil.getUserByJwtToken(request);
        if (user == null) {
            return R.error().message("请先登录");
        }
        image.setUid(user.getId());
        image.setName(name);
        this.imageService.upload(file, image);
        this.imageService.save(image);
        return R.ok().message("success");
    }

    @GetMapping("getImages/{size}/{current}")
    public R getImages(@PathVariable Integer current, @PathVariable Integer size) {
        IPage<Image> page = new Page<>();
        page.setSize(size);
        page.setCurrent(current);
        this.imageService.page(page, null);
        return R.ok().data("page", page);
    }

    @GetMapping("deleteImage/{id}")
    public R deleteImage(@PathVariable String id) {
        Image image = this.imageService.getById(id);
        if (image == null) {
            return R.error().message("当前图片不存在");
        }
        this.imageService.deleteImageById(image);
        return R.ok().message("删除成功");
    }

    @GetMapping("downloadImage/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) {
        Image image = this.imageService.getById(id);
        if (image == null) {
            throw new WjException(300, "当前图片不存在");
        }
        File file = new File(image.getPath());
        if (!file.exists()) {
            throw new WjException(300, "当前图片不存在");
        }
        try {
            InputStream inputStream = new FileInputStream(image.getPath());
            response.setContentType("image/jpeg");
            response.setHeader("content-disposition","attachment;filename="+file.getName());
            OutputStream outputStream = response.getOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(data)) > 0) {
                outputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

