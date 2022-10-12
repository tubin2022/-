package com.ecjtu.ferry.controller;

import com.ecjtu.ferry.pojo.vo.ResultVO;
import com.ecjtu.ferry.service.Impl.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/file")
@Api(tags = "文件上传")
public class FileController {
   @Autowired
   private FileServiceImpl fileService;

    @ApiOperation("单个文件上传")
    @PostMapping("/upload")
    public ResultVO httpUpload(@RequestParam("file") MultipartFile file) {
        ResultVO resultVO = fileService.httpUpload(file);
        return resultVO;
    }
}
