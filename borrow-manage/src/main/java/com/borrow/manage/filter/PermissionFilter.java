package com.borrow.manage.filter;

import com.alibaba.fastjson.JSON;

import com.borrow.manage.dao.SysUserTokenDao;
import com.borrow.manage.enums.ExceptionCode;
import com.borrow.manage.model.XMap;
import com.borrow.manage.model.dto.SysUserToken;
import com.borrow.manage.provider.httprequest.HttpRequestHelper;
import com.borrow.manage.provider.httprequest.PostBodyRequestWrapper;
import com.borrow.manage.vo.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class PermissionFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(PermissionFilter.class);

    @Autowired
    SysUserTokenDao sysUserTokenDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String reqMethod = request.getMethod();
        if ("POST".equals(reqMethod)){
//            response.setHeader("Cache-Control", "no-cache");
//            response.setHeader("Pragma", "No-cache");
//            response.setDateHeader("Expires", 0);
//            response.setContentType("application/json;charset=utf-8");
//            response.setCharacterEncoding("utf-8");

            PostBodyRequestWrapper requestWrapper = new PostBodyRequestWrapper(request);
            String body = HttpRequestHelper.getRequestBody(requestWrapper);
            XMap xMap = JSON.parseObject(body,XMap.class);

            String token = xMap.getString("token");
            if (StringUtils.isEmpty(token)) {
                logger.info("token is null");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(ResponseResult.error(ExceptionCode.SYS_TOKEN_FAIL.getErrorCode()
                        ,ExceptionCode.SYS_TOKEN_FAIL.getErrorMessage())));
                return;
            }
            SysUserToken sysUserToken = sysUserTokenDao.selByToken(token);
            if (sysUserToken == null) {
                logger.info("ip={} token is not exist token={}",token);
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(ResponseResult.error(ExceptionCode.SYS_TOKEN_FAIL.getErrorCode()
                        ,ExceptionCode.SYS_TOKEN_FAIL.getErrorMessage())));
                return;
            }
            if (sysUserToken.getExpireTime().compareTo(new Date()) < 0) {
                logger.info("ip={} token is expire token={}",token);
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(ResponseResult.error(ExceptionCode.SYS_TOKEN_FAIL.getErrorCode()
                        ,ExceptionCode.SYS_TOKEN_FAIL.getErrorMessage())));
                return;
            }
            filterChain.doFilter(requestWrapper, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
