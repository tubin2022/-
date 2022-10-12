package com.ecjtu.ferry.service;

import com.ecjtu.ferry.pojo.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    /**
     * 上传文件，主要是上传一些系统图片和用户头像
     * 由于没有远程文件服务器，这里用windows下d盘磁盘进行文件上传操作
     * @param file
     * @return
     */
    public ResultVO httpUpload(@RequestParam("file") MultipartFile file);
}
