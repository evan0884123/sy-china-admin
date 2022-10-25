package com.sychina.admin.web.router.system;

import com.aliyun.oss.model.OSSObjectSummary;
import com.sychina.admin.aop.Access;
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
    @Access
    public ResultModel<FileUploadModel> upload(@RequestParam("file") MultipartFile uploadFile) {

        return fileUploadService.upload(uploadFile);
    }

//    @PostMapping("/delete")
    @ApiOperation("删除")
    @Access
    public ResultModel<FileUploadModel> delete(@RequestParam("fileName") String fileName) {

        return fileUploadService.delete(fileName);
    }

//    @PostMapping("/list")
    @ApiOperation("获取文件list")
    @Access
    public ResultModel<List<OSSObjectSummary>> list() {

        return fileUploadService.list();
    }

//    @PostMapping("file/download")
    @ApiOperation("下载")
    @Access
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {

        this.fileUploadService.exportOssFile(response, fileName);
    }

    @Autowired
    public void setFileUploadService(FileUploadServiceImpl fileUploadService) {
        this.fileUploadService = fileUploadService;
    }
}
