package com.http.demo.client.utils;

import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FileUtil {

    public static void write(InputStream inputStream, String path, String fileName) {
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            String filePath = "D:\\NineTechFile\\验证码\\机器二维码图片样本\\";
            if (!StringUtils.isEmpty(path)) {
                filePath += path + "\\";
            }
            File fileDir = new File(filePath);

            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            File file = new File(filePath + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            int inByte;
            while ((inByte = bis.read()) != -1) {
                bos.write(inByte);
            }
            bis.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            throw ex;
        } finally {

        }
    }

    public static File read(String path, String fileName) {
        try {

            String filePath = "D:\\NineTechFile\\验证码\\机器二维码图片样本\\";

            if (!StringUtils.isEmpty(path)) {
                filePath += path + "\\";
            }

            File fileDir = new File(filePath);

            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            File file = new File(filePath + fileName);
            return file;
        } catch (RuntimeException ex) {
            throw ex;
        } finally {
        }
    }

    public static void copy(File source, File target) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(target);
            byte[] b = new byte[1024];
            int len = 0;
            System.out.println("开始拷贝...");
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            System.out.println("拷贝完成...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(os)) {
                    os.close();
                }
                if (Objects.nonNull(is)) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
