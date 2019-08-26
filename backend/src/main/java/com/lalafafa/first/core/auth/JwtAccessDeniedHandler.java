package com.lalafafa.first.core.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.lalafafa.first.core.MessageService;
import com.lalafafa.first.core.Result;
import com.lalafafa.first.core.ResultCode;
import com.lalafafa.first.core.ResultGenerator;
import com.lalafafa.first.core.utils.Utils;

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