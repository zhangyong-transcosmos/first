package com.lalafafa.first.core.auth;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lalafafa.first.dto.UserDto;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(UserDto user) {
        boolean enabled = user.getStatus() == 1 ? true : false;
        Collection<? extends GrantedAuthority> authorities = mapToGrantedAuthorities(user.getRoles());
        return new JwtUser(user.getUserId(), user.getUsername(), user.getPassword(), enabled, user.getUpdateTime(), authorities);
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}