package com.lianwei.service;

import com.lianwei.domain.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件管理service
 */
public interface FileService {
    /**
     * 上传单个文件
     * @param file
     * @return
     */
    public ResultVO uploadFile(MultipartFile file) throws IOException;

    /**
     * 上传多个文件
     * @param files
     * @return
     */
    public ResultVO uploadFiles(MultipartFile[] files) throws IOException;
}
