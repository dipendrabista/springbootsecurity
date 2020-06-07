package com.punojsoft.springbootsecurity.bootsecurity.util;

import javax.servlet.http.HttpServletRequest;

public class AppUtils {
    public static String getAppURI(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
