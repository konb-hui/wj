package com.konb.wj.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author konb
 */
@Configuration
@Data
public class FileConfig {

    @Value("${file.excel.path}")
    private String jotterPath;

    @Value("${file.excel.url}")
    private String jotterUrl;

    @Value("${file.excel.resourceHandler}")
    private String jotterResourceHandler;

    @Value("${file.image.url}")
    private String imageUrl;

    @Value("${file.image.path}")
    private String imagePath;

    @Value("${file.image.resourceHandler}")
    private String imageResourceHandler;

    @Value("${file.book.resourceHandler}")
    private String bookResourceHandler;

    @Value("${file.book.path}")
    private String bookPath;

    @Value("${file.book.url}")
    private String bookUrl;

}
