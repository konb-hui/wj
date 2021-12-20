package com.konb.wj.utils;

import com.konb.wj.exception.WjException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author konb
 */
public class FileUtils {

    /**
     * 将上传的文件保存到新文件中
     * @param uploadFile 上传的文件 MultipartFile
     * @param saveFile 新保存的文件
     */
    public static void copyUploadFile(MultipartFile uploadFile, File saveFile) {
        byte[] data = new byte[1024];
        try(InputStream inputStream = uploadFile.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            OutputStream outputStream = new FileOutputStream(saveFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
            while (bufferedInputStream.read(data) > 0) {
                bufferedOutputStream.write(data);
                bufferedOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WjException(300, "文件读取失败");
        }
    }

    /**
     * 将文件保存到指定文件夹下
     * @param oldFile 目标文件
     * @param directoryPath 保存的文件夹路径
     * @return File
     */
    public static File copyFileToDirectory(File oldFile, String directoryPath) {

        File directory = new File(directoryPath);
        if (! directory.exists() && ! directory.mkdirs()) {
            throw new WjException(300, "[" + oldFile.getAbsolutePath() + "]文件读取失败");
        }

        File newFile = new File(directoryPath + oldFile.getName());
        byte[] data = new byte[1024];
        try (FileInputStream fileInputStream = new FileInputStream(oldFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(newFile);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {

            while (bufferedInputStream.read(data) > 0) {
                bufferedOutputStream.write(data);
                bufferedOutputStream.flush();
            }

        }catch (IOException e) {
            e.printStackTrace();
            throw new WjException(300, "[" + oldFile.getAbsolutePath() + "]文件读取失败");
        }
        return newFile;
    }

}
