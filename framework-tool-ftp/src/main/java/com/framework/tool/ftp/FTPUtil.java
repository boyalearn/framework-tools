package com.framework.tool.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Vector;

public class FTPUtil {

    static private Session session = null;

    static private Channel channel = null;
    static private int timeout = 60000; //超时数,一分钟

    private static String userName = "root";

    private static String password = "123456";

    private static String host = "192.168.110.62";

    private static Integer port = 21;

    public static ChannelSftp getChannel(String username, String password, String ip, int port) throws JSchException {
        JSch jsch = new JSch(); // 创建JSch对象
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession(username, ip, port);
        System.out.println("Session created...");
        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        System.out.println("Session connected, Opening Channel...");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        System.out.println("Connected successfully to ip :{}, ftpUsername is :{}, return :{}");
        return (ChannelSftp) channel;
    }

    public static ChannelSftp getChannel(String ip, int port) throws JSchException {
        JSch jsch = new JSch(); // 创建JSch对象
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession( ip);
        System.out.println("Session created...");
        if (password != null) {
            session.setPassword(password); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        System.out.println("Session connected, Opening Channel...");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        System.out.println("Connected successfully to ip :{}, ftpUsername is :{}, return :{}");
        return (ChannelSftp) channel;
    }

    /**
     * 关闭channel和session
     *
     * @throws Exception
     */
    public static void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    public static void sendFile(String dstDirPath, File file) throws SftpException, JSchException {

        ChannelSftp channelSftp = null;
        String dstFilePath; // 目标文件名(带路径)，如： D:\\file\\file.doc,这个路径应该是远程目标服务器下要保存的路径
        try {
            // 一、 获取channelSftp对象
            channelSftp = getChannel(userName, password, host, port);
            // 二、 判断远程路径dstDirPath是否存在(通道配置的路径)
            try {
                Vector dir = channelSftp.ls(dstDirPath);
                if (dir == null) { // 如果路径不存在，则创建
                    channelSftp.mkdir(dstDirPath);
                }
            } catch (SftpException e) { // 如果dstDirPath不存在，则会报错，此时捕获异常并创建dstDirPath路径
                channelSftp.mkdir(dstDirPath); // 此时创建路o如果再报错，即创建失败，则抛出异常
                e.printStackTrace();
            }
            // 三、 推送文件
            try {

                dstFilePath = dstDirPath + "\\" + file.getName();
                // 推送: dstFilePath——传送过去的文件路径(全路径),采用默认的覆盖式推送
                channelSftp.put(new FileInputStream(file), dstFilePath); // jsch触发推送操作的方法
            } catch (SftpException | FileNotFoundException e) {
                System.out.println("An error occurred during sftp push, send data fail, the target path is :{}");
            }
        } finally {
            // 处理后事
            if (channelSftp != null)
                channelSftp.quit();
            try {
                closeChannel();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) throws JSchException, SftpException {
        File file = new File("D:\\ApacheMaven-3.6.3\\README.txt");
        sendFile("/user/file", file);
    }
}
