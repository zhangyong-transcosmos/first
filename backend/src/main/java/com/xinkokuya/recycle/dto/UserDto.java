package com.xinkokuya.recycle.dto;

import java.util.List;

import com.xinkokuya.recycle.model.User;

public class UserDto extends User {

    private List<String> roles;

    private List<String> permissions;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}