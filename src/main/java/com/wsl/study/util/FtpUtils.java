package com.wsl.study.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * create by WSL_SILVA
 * 日期: 2018/8/14 0014
 * 用途：
 * 描述:
 */
public class FtpUtils {

    //ftp服务器ip
    private static final String FTP_ADDRESS = "47.93.223.141";

//    端口号
    private static final int FTP_PORT = 21;
    //用户名
    private static final String USER_NAME = "ftpuser";
    //密码
    private static final String PASSWORD = "123456";
    //pic src
    private static final String FTP_BASEPATH = "upload/img";


    public static FTPClient getFtpClient(){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try {
            ftpClient.connect(FTP_ADDRESS,FTP_PORT);
            ftpClient.login(USER_NAME,PASSWORD);
            return ftpClient;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     * @param originFileName
     * @param inputStream
     * @return
     */
    public static boolean uploadFile(String originFileName, InputStream inputStream){
        boolean success = false;
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("UTF-8");
        try{
            int reply;
            ftpClient.connect(FTP_ADDRESS,FTP_PORT);
            ftpClient.login(USER_NAME,PASSWORD);
            reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                return success;
            }
            ftpClient.changeWorkingDirectory("/");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 切换到根目录
            ftpClient.changeWorkingDirectory("/");
            String[] pah = FTP_BASEPATH.split("/");
            // 分层创建目录
            for (String pa : pah) {
                System.out.println(pa);
                ftpClient.makeDirectory(pa);
                // 切到到对应目录
                ftpClient.changeWorkingDirectory(pa);
            }
            ftpClient.storeFile(originFileName,inputStream);
            inputStream.close();
            ftpClient.logout();
            success = true;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }




    public static void main(String[] args){
        File file = new File("D:\\Users\\Administrator\\Desktop\\代码统一规范.html");
        if(file.exists()){
            String originName = file.getName();
            try {
                InputStream inputStream = new FileInputStream(file);
                boolean flag = uploadFile(originName,inputStream);
                System.out.println(flag);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

