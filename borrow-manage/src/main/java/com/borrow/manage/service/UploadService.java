package com.borrow.manage.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wxn
 * Date:2019/4/25
 * Time:21:38
 */
public interface UploadService {

    String upload(MultipartFile multipartFile, String fileName, HttpServletRequest request) throws IOException;

}
