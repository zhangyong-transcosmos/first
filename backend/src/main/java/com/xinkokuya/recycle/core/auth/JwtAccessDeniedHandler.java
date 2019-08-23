package com.xinkokuya.recycle.core.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.xinkokuya.recycle.core.MessageService;
import com.xinkokuya.recycle.core.Result;
import com.xinkokuya.recycle.core.ResultCode;
import com.xinkokuya.recycle.core.ResultGenerator;
import com.xinkokuya.recycle.core.utils.Utils;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = ResultGenerator.genFailResult(ResultCode.FORBIDDEN,
                MessageService.getMessage("error.default.forbidden", request.getRequestURI()));
        Utils.printResponse(response, result);
    }
}