package com.groot.education.dto;


import com.groot.education.model.Permission;
import com.groot.education.model.Role;
import com.groot.education.model.User;

import java.util.List;

public class LoggedUserDTO {

    public long id;

    public String token;

    public Role role;

    public List<Permission> permissions;

    public LoggedUserDTO() {}

    public LoggedUserDTO(long id, String token, Role role, List<Permission> permissions) {
        this.id = id;
        this.token = token;
        this.role = role;
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
