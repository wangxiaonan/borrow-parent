package com.risk.app.filter;

import com.alibaba.fastjson.JSON;
import com.csyy.core.constant.Constants;
import com.risk.common.constant.PlatformConstant;
import com.risk.common.enums.ExceptionCode;
import com.risk.common.utils.IpUtils;
import com.risk.common.utils.PostRequestWrapper.HttpRequestHelper;
import com.risk.common.utils.PostRequestWrapper.PostBodyReaderHttpServletRequestWrapper;
import com.risk.source.dao.RiskPlatformDao;
import infra.common.base.ResponseResult;
import j.m.XMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpServletRequest对象的输入流只能读取一次，且不能set回去
 * 所以自己实现一个PostBodyReaderHttpServletRequestWrapper用来set读取过一次的输入流供后续操作使用
 * Created by wangxindong on 18/7/3
 */
public class WhiteIpFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(WhiteIpFilter.class);
    @Autowired
    RiskPlatformDao riskPlatformDao;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String reqMethod = request.getMethod();
        if ("POST".equals(reqMethod)){
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding(Constants.Charset.UTF8);

            PostBodyReaderHttpServletRequestWrapper requestWrapper = new PostBodyReaderHttpServletRequestWrapper(request);
            String body = HttpRequestHelper.getRequestBody(requestWrapper);
            XMap xMap = XMap.fromJSON(body);

            String key = xMap.getString(PlatformConstant.SECRET_KEY);
            String ip = IpUtils.getHostIp();
            if (!StringUtils.isEmpty(key) && riskPlatformDao.checkWriteIp(ip, key)){
                filterChain.doFilter(requestWrapper, response);
            }else {
                logger.info("ip={} 白名单验签失败 body={}",ip,body);
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(ResponseResult.error(ExceptionCode.INSPECTION_SIGN_FAILED.getErrorCode()
                        ,ExceptionCode.INSPECTION_SIGN_FAILED.getErrorMessage())));
                return;
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
