package com.borrow.manage.api;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.service.UploadService;
import com.borrow.manage.vo.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxn
 * Date:2019/4/25
 * Time:22:25
 */
@RestController
public class UploadApi {

    @Autowired
    private UploadService uploadService;
    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult uploadFile(@RequestParam(value = "upload-images", required = false)
                                                 MultipartFile multipartFile,HttpServletRequest request) throws IOException {
        String uploadUrl = uploadService.upload(multipartFile, multipartFile.getOriginalFilename(),request);
        return ResponseResult.success(ExceptionCode.SUCCESS.getErrorMessage(),uploadUrl);
    }

}
