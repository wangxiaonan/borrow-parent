package com.borrow.manage.provider.httprequest;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class HttpRequestHelper {


    /**
     * 解析request用post提交的输入流信息
     * @param request
     * @return
     */
    public static String getRequestBody(ServletRequest request){
        StringBuilder sb = new StringBuilder();
        try (
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))
        ){
            String line = "";
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
