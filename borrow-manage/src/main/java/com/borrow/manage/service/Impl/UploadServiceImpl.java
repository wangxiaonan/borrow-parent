package com.borrow.manage.service.Impl;

import com.borrow.manage.config.Properties;
import com.borrow.manage.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by wxn
 * Date:2019/4/25
 * Time:21:39
 */
@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public String upload(MultipartFile multipartFile, String fileName, HttpServletRequest request) throws IOException {
        String path = Properties.IMAGE_URL;
        File file = new File(path);
        if (!file.exists()) {//如果文件不存在
            file.mkdirs();
        }
        String baseUrl = path + File.separator + fileName;
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(baseUrl));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return baseUrl;
    }


}
