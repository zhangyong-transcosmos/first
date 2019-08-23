package com.xinkokuya.recycle.core.auth;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xinkokuya.recycle.dto.UserDto;
import com.xinkokuya.recycle.service.common.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库取得用户信息
        UserDto user = this.userService.getUserInfoByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Username not found: '%s'", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}