package com.borrow.manage.provider;

import com.alibaba.fastjson.JSON;
import com.borrow.manage.model.RestBaseParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wxn on 16-10-19.
 */
public class RestHttpClientService {
    private static ConcurrentHashMap<String,RestHttpClientService> concurrentMap = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final HostnameVerifier PROMISCUOUS_VERIFIER = (s, sslSession ) -> true;

    private RestBaseParam restBaseParam;
    private RestTemplate restTemplate;
    public RestHttpClientService(RestBaseParam restBaseParam){
        this.restBaseParam = restBaseParam;
        this.restTemplate = new RestTemplate();
        //验证主机名和服务器验证方案的匹配是可接受的
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(){
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if(connection instanceof HttpsURLConnection){
                    ((HttpsURLConnection) connection).setHostnameVerifier(PROMISCUOUS_VERIFIER);
                    ((HttpsURLConnection) connection).setSSLSocketFactory(trustSelfSignedSSL());
                }
                super.prepareConnection(connection, httpMethod);
            }
        };
        requestFactory.setReadTimeout(restBaseParam.getReadTimeout() > 0 ? restBaseParam.getReadTimeout(): 20000);
        requestFactory.setConnectTimeout(restBaseParam.getConnectTimeout()> 0 ? restBaseParam.getConnectTimeout():5000);
        restTemplate.setRequestFactory(requestFactory);
        List<ClientHttpRequestInterceptor> interceptorsTimeout = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorsTimeout.add(new HeaderRequestInterceptor());
        restTemplate.setInterceptors(interceptorsTimeout);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        restTemplate.getMessageConverters().set(1,stringConverter);
    }


    private static class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpRequest wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set("Accept-charset", "utf-8");
            return execution.execute(wrapper, body);
        }
    }

    /**
     * POST 请求　请求参数ｋｅy,value 类型：application/x-www-form-urlencoded　
     * @param apiurl
     * @param paramMap
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postForObjectMultiValue(String apiurl, Map<String,Object> paramMap, Class<T> clazz){

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            map.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

        return restTemplate.postForObject(apiurl,request,clazz);
    }

    /**
     * POST 请求　请求参数ｋｅy,value 类型：application/x-www-form-urlencoded　
     * @param apiurl
     * @param paramMap
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postForObjectMultiValue(String apiurl, Map<String,Object> paramMap, Class<T> clazz,MediaType mediaType){

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            map.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

        return restTemplate.postForObject(apiurl,request,clazz);
    }

    /**
     * POST 请求　请求参数ｋｅy,value 类型：application/x-www-form-urlencoded　,请求头可自定义参数值
     * @param apiurl
     * @param paramMap
     * @param clazz
     * @param headerMap
     * @param <T>
     * @return
     */
    public <T> T postForObjectMultiValue(String apiurl, Map<String,Object> paramMap, Class<T> clazz,Map<String,String> headerMap){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        if (headerMap != null){
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpHeaders.set(entry.getKey(),entry.getValue());
            }
        }
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            map.add(entry.getKey(), String.valueOf(entry.getValue()));
        }


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, httpHeaders);

        return restTemplate.postForObject(apiurl,request,clazz);
    }

    /**
     * POST 请求,请求参数: 对象形式 请求配型：application/json
     * @param apiurl
     * @param body
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postForObjectJson(String apiurl, Object body, Class<T> clazz){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(body),headers);
        return restTemplate.postForObject(apiurl,entity,clazz);
    }

    /**
     * POST 请求,请求参数: 对象形式 请求配型：application/json
     * @param apiurl 接口地址
     * @param body Json格式的字符串
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T postForObjectJson(String apiurl, String body, Class<T> clazz){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(body,headers);
        return restTemplate.postForObject(apiurl,entity,clazz);
    }

    /**
     * POST 请求,请求参数: 对象形式 请求配型：application/json ,请求头可自定义参数值
     * @param apiurl
     * @param body
     * @param clazz
     * @param headerMap
     * @param <T>
     * @return
     */
    public <T> T postForObjectJson(String apiurl, Object body, Class<T> clazz,Map<String,String> headerMap){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (headerMap != null){
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.set(entry.getKey(),entry.getValue());
            }
        }

        HttpEntity<String> entity = new HttpEntity<String>(body==null?null:JSON.toJSONString(body),headers);
        return restTemplate.postForObject(apiurl,entity,clazz);
    }

    /**
     * POST ,请求参数: 对象形式 请求配型：application/json ,返回数据包括　http状态码　响应数据等。无特殊需要建议不使用。
     * @param apiurl
     * @param request
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> postForEntityJson(String apiurl, Object request, Class<T> responseType){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request),headers);
        return restTemplate.postForEntity(apiurl,entity,responseType);
    }
    public <T> ResponseEntity<T> postForEntityJson(String apiurl, Object request, Class<T> responseType,Map<String,String> headerMap){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (headerMap != null){
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.set(entry.getKey(),entry.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<String>(JSON.toJSONString(request),headers);
        return restTemplate.postForEntity(apiurl,entity,responseType);
    }


    /**
     * GET　请求 REST TEMPLATE 默认方式
     * @param apiurl
     * @param params
     * @param responseType
     * @param <T>
     * @return
     */
    public <T> T getForObject(String apiurl, Map<String, String> params,Class<T> responseType){
        return restTemplate.getForObject(apiurl, responseType,params);
    }

    /**
     * GET ,请求头可自定义参数值
     * @param apiurl
     * @param params
     * @param responseType
     * @param headerMap
     * @param <T>
     * @return
     */
    public <T> T getForObject(String apiurl, Map<String, String> params,Class<T> responseType,Map<String,String> headerMap){
        HttpHeaders headers = new HttpHeaders();
        if (headerMap != null){
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.set(entry.getKey(),entry.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiurl,HttpMethod.GET,entity,responseType,params);
        return responseEntity.getBody();
    }

    public <T> T getForObject(String apiurl,Class<T> responseType,Map<String,String> headerMap){
        HttpHeaders headers = new HttpHeaders();
        if (headerMap != null){
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                headers.set(entry.getKey(),entry.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(apiurl,HttpMethod.GET,entity,responseType);
        return responseEntity.getBody();
    }


    public static RestHttpClientService buildRestHttpClientService(String restbeankey){

        if (concurrentMap.containsKey(restbeankey)) {
            return concurrentMap.get(restbeankey);
        }
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        Object warpObj = wac.getBean(restbeankey);
        if (warpObj == null)
            return null;
        RestHttpClientService httpClientService = (RestHttpClientService) warpObj;
        concurrentMap.put(restbeankey,httpClientService);
        return httpClientService;

    }

    public SSLSocketFactory trustSelfSignedSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            return ctx.getSocketFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Exception occurred ", ex);
        }
    }
}
