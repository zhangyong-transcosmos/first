package com.lalafafa.first.core;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private static MessageSource messageSource;
    
    @Autowired
    public MessageService(MessageSource messageSource) {
        MessageService.messageSource = messageSource;
    }

    public static String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

    public static String getMessage(String key, String... arg) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] args = new Object[arg.length];
        for (int i = 0; i < arg.length; i++) {
            args[i] = arg[i];
        }
        return messageSource.getMessage(key, args, locale);
    }
}