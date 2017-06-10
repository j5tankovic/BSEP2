package com.groot.education.dto;

import com.groot.education.model.Role;

public class UserDTO {

    private long id;
    private String name;
    private String surname;
    private String username;
    private Role role;

    public UserDTO() {

    }

    public UserDTO(long id, String name, String surname, String username, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
