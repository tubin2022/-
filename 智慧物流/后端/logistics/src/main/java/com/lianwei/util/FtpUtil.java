package com.lianwei.util;

import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FtpUtil {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String basePath;

    /**
     * 上传文件
     * @param filePath 文件上传目录
     * @param fileName 文件上传后名称
     * @param inputStream 文件流
     * @return
     */
    public Boolean uploadFile(String filePath,String fileName, InputStream inputStream) {
        //1、目标上传路径
        String targetPath = basePath+filePath;
        //2、创建FTPClient对象
        FTPClient ftp = new FTPClient();
        try {
            //3、定义返回的状态码
            int reply;
            //4、连接ftp
            ftp.connect(host,port);
            //5、输入账号和密码进行登录
            ftp.login(username, password);
            //6、接受状态码(如果成功，返回230，如果失败返回503)
            reply = ftp.getReplyCode();
            //7、根据状态码检测ftp的连接,如果连接成功返回true，否则返回false
            if (!FTPReply.isPositiveCompletion(reply)) {
                //说明连接失败，需要断开连接
                ftp.disconnect();
                return false;
            }
            // 8、切换工作目录，成功返回true，否则返回false
            if(!ftp.changeWorkingDirectory(targetPath)){
                //切换不成功表示目录不存在，则创建该目录并切换进去
                boolean f = createDirectory(targetPath,ftp);
                ftp.changeWorkingDirectory(targetPath);
            }
            //9、设置被动模式（根据自己ftp的设置来决定，默认是主动模式）
            //模式错误可能会导致上传超时，文件大小为0的情况
            ftp.enterLocalPassiveMode();
            //10、把文件转换为二进制字符流的形式进行上传
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //11、上传文件到该目录
            ftp.storeFile(fileName,inputStream);
            //12、关闭输入流
            inputStream.close();
            // 13.退出ftp
            ftp.logout();
        }catch (Exception e){
//            e.printStackTrace();
        }finally {
            if (ftp.isConnected()) {
                try {
                    // 14.断开ftp的连接
                    ftp.disconnect();
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return true;
    }

    //创建多层目录文件
    public Boolean createDirectory(String remote, FTPClient ftp) throws IOException {
        boolean flag = true;
        StringTokenizer s = new StringTokenizer(remote, "/");
        s.countTokens();
        String pathName = "";
        while (s.hasMoreElements()) {
            pathName = pathName + "/" + (String) s.nextElement();
            try {
                ftp.makeDirectory(pathName);
            } catch (Exception e) {
//                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

}
