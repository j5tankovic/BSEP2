package com.groot.education.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {

    STUDENT(new Permission[]{Permission.EDIT_PROFILE}),

    TEACHER(new Permission[]{Permission.EDIT_PROFILE,
            Permission.ADD_ANNOUNCEMENT,
            Permission.EDIT_ANNOUNCEMENT,
            Permission.DELETE_ANNOUNCEMENT}),

    ADMIN(new Permission[]{Permission.ADD_USER,
            Permission.DELETE_USER,
            Permission.ADD_COURSE,
            Permission.DELETE_COURSE});

    private final Permission[] permissions;

    Role(Permission[] permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions(){
        return new ArrayList<>(Arrays.asList(permissions));
    }

}
