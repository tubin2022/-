package com.lianwei.service.impl;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.FileService;
import com.lianwei.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 文件管理service实现类
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FtpUtil ftpUtil;

    @Override
    public ResultVO uploadFile(MultipartFile file) throws IOException {
        //图片保存路径：后期可以定义到常量类中，时间目录可以动态生成，以便复用和修改
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        String dateStr = sdf.format(date);
        String filePath = "/userFace/"+dateStr;
        //图片原名称
        String originalName = file.getOriginalFilename();
        //生成随机名称
        String fileName = UUID.randomUUID()+originalName.substring(originalName.lastIndexOf("."));
//        System.out.println(fileName);
//        try {
            //上传
            ftpUtil.uploadFile(filePath,fileName,file.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //可以返回图片相对路径
        String userFaceUrl = "http://"+ftpUtil.getHost()+":8087"+filePath+"/"+fileName;
//        System.out.println(userFaceUrl);
        return new ResultVO(userFaceUrl);
    }

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    @Override
    public ResultVO uploadFiles(MultipartFile[] files) throws IOException {
        List<String> results =  new ArrayList<>();
        for (int i =0;i<files.length;i++){
            ResultVO resultVO = this.uploadFile(files[i]);
            String data = (String) resultVO.getData();
            results.add(data);
        }
        ResultVO resultVO = new ResultVO(results);
        return resultVO;
    }
}
