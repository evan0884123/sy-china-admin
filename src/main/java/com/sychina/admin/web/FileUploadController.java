package com.sychina.admin.web;

import com.aliyun.oss.model.OSSObjectSummary;
import com.sychina.admin.service.impl.FileUploadServiceImpl;
import com.sychina.admin.web.pojo.models.FileUploadModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/file")
@Api(tags = {"文件上传"})
public class FileUploadController {

    private FileUploadServiceImpl fileUploadService;

    @PostMapping("/upload")
    @ApiOperation("上传")
    public ResultModel<FileUploadModel> upload(@RequestParam("file") MultipartFile uploadFile) {

        return fileUploadService.upload(uploadFile);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除")
    public ResultModel<FileUploadModel> delete(@RequestParam("fileName") String fileName) {

        return fileUploadService.delete(fileName);
    }

    @RequestMapping("/list")
    @ApiOperation("删除")
    public ResultModel<List<OSSObjectSummary>> list() {

        return fileUploadService.list();
    }

    @PostMapping("file/download")
    @ApiOperation("下载")
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {

        this.fileUploadService.exportOssFile(response, fileName);
    }

    @Autowired
    public void setFileUploadService(FileUploadServiceImpl fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
