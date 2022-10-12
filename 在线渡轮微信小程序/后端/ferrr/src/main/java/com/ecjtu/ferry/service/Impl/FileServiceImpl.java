package com.ecjtu.ferry.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FileServiceImpl implements IFileService {
    private String uploadFilePath = "D:\\images\\";

    /**
     * 上传文件，主要是上传一些系统图片和用户头像
     * 由于没有远程文件服务器，这里用windows下d盘磁盘进行文件上传操作
     * @param file
     * @return
     */
    @Override
    public ResultVO httpUpload(MultipartFile file) {
        JSONObject object=new JSONObject();
        //判断文件是否为空
        if(file.isEmpty()){
            object.put("error","空文件");
            ResultVO resultVO = new ResultVO(object);
            return resultVO;
        }
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //判断文件上传的类型，由于本系统中上传文件主要是上传图片，这里只允许上传图片类型的文件
        if(!(suffixName.equals(".png")||suffixName.equals(".jpeg")||suffixName.equals(".jpg"))){
            object.put("error","图片格式错误");
            ResultVO resultVO = new ResultVO(object);
            return resultVO;
        }
        //重命名文件
        fileName = UUID.randomUUID().toString().replace("-","")+suffixName;
        log.info("上传文件名称为：{},后缀名为：{}",fileName,suffixName);
        File fileTempObj = new File(uploadFilePath+"/"+fileName);
        if(!fileTempObj.getParentFile().exists()){
            fileTempObj.getParentFile().mkdirs();
        }
        //上传文件
        if(fileTempObj.exists()){
            object.put("error","文件已存在");
            ResultVO resultVO = new ResultVO(object);
            return resultVO;
        }

        try{
            file.transferTo(fileTempObj);
        } catch (Exception e) {
            log.error("发生错误:{}",e);
            object.put("error",e.getMessage());
            ResultVO resultVO = new ResultVO(object);
            return resultVO;
        }
        //上传成功返回图片的访问路径
        object.put("success","上传成功");
        String url = "http://127.0.0.1:8888/image/"+fileName;
        object.put("url",url);
        ResultVO resultVO = new ResultVO(object);
        return resultVO;
    }
}
