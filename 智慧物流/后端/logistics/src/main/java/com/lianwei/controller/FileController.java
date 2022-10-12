package com.lianwei.controller;

import com.lianwei.domain.vo.ResultVO;
import com.lianwei.service.FileService;
import com.lianwei.util.FtpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("file")
@Api(tags = "文件上传")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    @ApiOperation("上传单个文件")
    public ResultVO uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        ResultVO resultVO = fileService.uploadFile(file);
        return resultVO;
    }

    @PostMapping("/uploadFiles")
    @ApiOperation("上传单个文件")
    @ApiImplicitParam(name = "files",value = "多个文件，",paramType = "formData",allowMultiple=true,required = true,dataType = "file")
    public ResultVO uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {
        ResultVO resultVO = fileService.uploadFiles(files);
        return resultVO;
    }
}
