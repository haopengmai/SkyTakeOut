package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping ("/upload")
    public Result<String> upload(MultipartFile file) throws IOException
    {
        log.info("上传了文件,{}",file);
        //获得原始文件名


        try {
            String originalFilename = file.getOriginalFilename();
            //获得原始文件的后缀，jpg,png等
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            //生成UUID
            String objectname = UUID.randomUUID().toString() + extension;
            String filepath = aliOssUtil.upload(file.getBytes(),objectname);
            return Result.success(filepath);
        } catch (IOException e) {
            log.error("Error uploading");
            return Result.success(MessageConstant.UPLOAD_FAILED);
        }
    }
}
