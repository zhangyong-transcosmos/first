package com.lalafafa.first.web.auth;

import com.lalafafa.first.core.Result;
import com.lalafafa.first.core.ResultGenerator;
import com.lalafafa.first.model.User;
import com.lalafafa.first.service.common.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class AuthController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
    	String token = this.userService.login(user);
        Map tokenMap = new HashMap<String, String>(); 
        tokenMap.put("token", token);
    	return ResultGenerator.genSuccessResult(tokenMap);
    }
    
    @PostMapping("/logout")
    public Result logout(String token) {
    	this.userService.logout(token);
    	return ResultGenerator.genSuccessResult();
    }
}
