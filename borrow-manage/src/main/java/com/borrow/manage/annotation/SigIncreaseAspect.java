package com.risk.app.annotation;

import com.csyy.common.JSONUtils;
import com.risk.app.executor.Retryer;
import com.risk.app.provider.RequestObject;
import com.risk.app.provider.datasource.AbstractRuleDataSource;
import com.risk.app.vo.BigDataTagVo;
import com.risk.common.enums.BdOfCrdEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by wangxindong on 18/5/24
 */
@Aspect
@Component
public class InfoIncreaseAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "bigDataRuleDataSource")
    private AbstractRuleDataSource dataSource;



    @Pointcut("@annotation(com.risk.app.annotation.InfoIncrease)")
    private void anyMethod() {

    }

    @Around("anyMethod()")
    private Object increase(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object o = joinPoint.getArgs()[0];
            if (o instanceof RequestObject){
                RequestObject requestObject = (RequestObject) o;
                if (null != requestObject.getSilenceRules() || !requestObject.getSilenceRules().isEmpty()){
                    //lambda在数据量小的时候效率没有执行for循环快
//                    List<String> params = requestObject.getSilenceRules().keySet()
//                            .stream()
//                            .map(BdOfCrdEnum::findParamsByEnumName)
//                            .flatMap(List::stream)
//                            .distinct()
//                            .collect(Collectors.toList());

                    List<String> params = new ArrayList<>();
                    for (String e: requestObject.getSilenceRules().keySet()) {
                        for (String a :BdOfCrdEnum.findParamsByEnumName(e)) {
                            if (!params.contains(a)){
                                params.add(a);
                            }
                        }
                    }
                    Map<String,List<String>> param = new HashMap<>();
                    param.put(BdOfCrdEnum.DEFAULT.name(),params);
                    dataSource.getData(requestObject,param.entrySet().iterator().next(),new Retryer.RetryMeta());
                    requestObject.getRuleResult().values().stream().flatMap(List::stream).forEach(e ->
                        requestObject.getParam().put(e.getTagCode(),e.getTagValue())
                    );
                }
            }

        } catch (Throwable throwable) {
            logger.error("increase is error", throwable);
        }
        return joinPoint.proceed();
    }
}
