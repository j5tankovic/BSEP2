package com.groot.education.dto;


public class CourseUserDTO {

    String username;

    public CourseUserDTO() {

    }

    public CourseUserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
