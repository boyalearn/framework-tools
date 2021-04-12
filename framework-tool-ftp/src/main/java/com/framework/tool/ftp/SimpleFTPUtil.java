package com.framework.tool.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SimpleFTPUtil {

    public static void main(String[] args) throws IOException {
        try {
            sendFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        downloadFile();
    }




    public static void sendFile() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.connect("192.168.110.62", 21);
        ftpClient.login("root", "123456");
        int replyCode = ftpClient.getReplyCode();
        ftpClient.setDataTimeout(120000);
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            System.out.println("FTP连接失败");
        } else {
            System.out.println("FTP连接成功");
        }
        //本地文件流
        InputStream in = new FileInputStream("D:\\ApacheMaven-3.6.3\\README.txt");
        //指定写入目录,注意:假如指定的目录不存在,会直接上传到根目录,假如存在，就上传到指定路径
//		ftpClient.changeWorkingDirectory("test2");
        //远程文件名
        String removePath = "text.txt";
        ftpClient.makeDirectory("test2");
        ftpClient.cwd("test2");
        //上传
        if (ftpClient.storeFile(removePath, in)) {
            System.out.println("文件上传成功");
        } else {
            System.out.println("文件上传失败");
        }
        //关闭文件流
        in.close();
        //关闭连接
        if (ftpClient != null) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    public static void downloadFile() throws IOException {
        // 默认失败
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.connect("192.168.110.62", 21);
        ftpClient.login("root", "123456");
        try {
            // 跳转到文件目录
            ftpClient.changeWorkingDirectory("test2");
            // 获取目录下文件集合
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                // 取得指定文件并下载
                OutputStream out = new FileOutputStream(new File("D:\\" + file.getName()));
                // 绑定输出流下载文件,需要设置编码集，不然可能出现文件为空的情况
                flag = ftpClient.retrieveFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), out);
                // 下载成功删除文件,看项目需求
                // ftp.deleteFile(new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
                System.out.println("success");
                out.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
