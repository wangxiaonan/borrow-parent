package com.borrow.manage.api;

import com.borrow.manage.dao.BorrowProductDao;
import com.borrow.manage.dao.UserInfoDao;
import com.borrow.manage.task.OverdueTask;
import com.borrow.manage.utils.UUIDProvider;
import com.borrow.manage.utils.id.IdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;


@RestController
public class AppInfo {
    private static final Logger logger = LoggerFactory.getLogger(AppInfo.class);
    private Properties buildInfo = null;
    private static String hostname = "unknown";

    @Autowired
    IdProvider idProvider;

    @Autowired
    OverdueTask overdueTask;

    @RequestMapping("/appver")
    public HashMap<String,String> appVersion(HttpServletRequest request) {
        System.out.println(idProvider.genId());
        System.out.println(UUIDProvider.uuid());
        HashMap<String,String> appver = new HashMap<String, String>(10);
        if (buildInfo != null) {
            for (Object key : buildInfo.keySet()) {
                appver.put(key.toString(), buildInfo.getProperty(key.toString()));
            }
        }

        // set runtime information
        appver.put("run.host", hostname);
        if (request.getHeader("X-Real-IP") != null) {
            appver.put("run.remote", request.getHeader("X-Real-IP"));
        } else {
            appver.put("run.remote", request.getRemoteHost());
        }

        appver.put("uuid", UUID.randomUUID().toString());
        overdueTask.overdueCal();
        return appver;
    }
}
