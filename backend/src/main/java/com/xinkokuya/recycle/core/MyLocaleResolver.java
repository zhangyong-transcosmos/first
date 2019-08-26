package com.xinkokuya.recycle.core;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

public class MyLocaleResolver implements LocaleResolver {

    private static final String I18N_LANGUAGE = "language";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = Locale.getDefault();
        String i18nLanguage = request.getParameter(I18N_LANGUAGE);
        if (StringUtils.isEmpty(i18nLanguage)) {
            i18nLanguage = request.getHeader(I18N_LANGUAGE);
        }
        if (!StringUtils.isEmpty(i18nLanguage)) {
            String[] language = i18nLanguage.split("_");
            locale = new Locale(language[0], language[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        // TODO Auto-generated method stub
    }
}