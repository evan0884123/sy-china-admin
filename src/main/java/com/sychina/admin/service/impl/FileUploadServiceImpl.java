package com.sychina.admin.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.sychina.admin.config.AliyunConfig;
import com.sychina.admin.web.pojo.models.FileUploadModel;
import com.sychina.admin.web.pojo.models.response.ResultModel;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Administrator
 */
@Service
public class FileUploadServiceImpl {

    private OSS ossClient;

    private AliyunConfig aliyunConfig;

    /**
     *
     */
//    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /**
     * 文件上传
     *
     * @param uploadFile
     * @return
     */
    public ResultModel<FileUploadModel> upload(MultipartFile uploadFile) {
        // 校验图片格式
//        boolean isLegal = false;
//        for (String type : IMAGE_TYPE) {
//            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
//                isLegal = true;
//                break;
//            }
//        }
        // 封装Result对象，并且将文件的byte数组放置到result对象中
        FileUploadModel fileUploadModel = new FileUploadModel();
//        if (!isLegal) {
//            fileUploadModel.setStatus("error");
//            return ResultModel.failed(fileUploadModel);
//        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败
            fileUploadModel.setStatus("error");
            return ResultModel.failed(fileUploadModel);
        }
        fileUploadModel.setStatus("done");
        fileUploadModel.setResponse("success");
        // 文件路径需要保存到数据库
        fileUploadModel.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadModel.setUid(String.valueOf(System.currentTimeMillis()));
        return ResultModel.succeed(fileUploadModel);
    }

    /**
     * 生成路径以及文件名
     *
     * @param sourceFileName
     * @return
     */
    private String getFilePath(String sourceFileName) {
        return "images/" + formatter.format(LocalDateTime.now()) + "/"
                + System.currentTimeMillis() + RandomUtils.nextInt(100, 9999) + "."
                + StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * 查看文件列表
     *
     * @return
     */
    public ResultModel<List<OSSObjectSummary>> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();

        return ResultModel.succeed(sums);
    }

    /**
     * 删除文件
     *
     * @param objectName
     * @return
     */
    public ResultModel<FileUploadModel> delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        FileUploadModel fileUploadModel = new FileUploadModel();
        fileUploadModel.setName(objectName);
        fileUploadModel.setStatus("removed");
        fileUploadModel.setResponse("success");

        return ResultModel.succeed(fileUploadModel);
    }

    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     * @return
     */
    public ResultModel exportOssFile(HttpServletResponse response, String fileName) {

        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            // 浏览器以附件形式下载
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), fileName);
            // 读取文件内容。
            in = new BufferedInputStream(ossObject.getObjectContent());
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = in.read(buffer)) != -1) {
                out.write(buffer, 0, lenght);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return ResultModel.succeed();
    }

    @Autowired
    public void setOssClient(OSS ossClient) {
        this.ossClient = ossClient;
    }

    @Autowired
    public void setAliyunConfig(AliyunConfig aliyunConfig) {
        this.aliyunConfig = aliyunConfig;
    }
}
