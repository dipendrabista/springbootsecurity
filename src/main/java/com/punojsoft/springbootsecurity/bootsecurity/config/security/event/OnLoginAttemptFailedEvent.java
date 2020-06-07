package com.punojsoft.springbootsecurity.bootsecurity.config.security.event;

import com.punojsoft.springbootsecurity.bootsecurity.model.User;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class OnLoginAttemptFailedEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale = Locale.ENGLISH;
    private final User user;
    private HttpServletResponse response;

    public OnLoginAttemptFailedEvent(Object source, String appUrl, Locale locale, User user, HttpServletResponse response) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
        this.response = response;
    }
    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
