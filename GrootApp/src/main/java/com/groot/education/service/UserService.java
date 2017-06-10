package com.groot.education.service;


import com.groot.education.model.Course;
import com.groot.education.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);

    Optional<User> findByToken(String token);

    List<User> findAll();

    void add(User user);

    User edit(User original, User toEdit);

    void delete(long id);

    List<Course> findCourses();
}
