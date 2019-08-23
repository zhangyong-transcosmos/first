package com.xinkokuya.recycle.core.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.xinkokuya.recycle.core.MessageService;
import com.xinkokuya.recycle.core.Result;
import com.xinkokuya.recycle.core.ResultCode;
import com.xinkokuya.recycle.core.ResultGenerator;
import com.xinkokuya.recycle.core.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        Result result = ResultGenerator.genFailResult(ResultCode.UNAUTHORIZED,
                MessageService.getMessage("error.default.auth_failure"));
        Utils.printResponse(response, result);
    }
}