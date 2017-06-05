package com.groot.education.service.impl;

import com.groot.education.model.Course;
import com.groot.education.model.User;
import com.groot.education.repository.CourseRepository;
import com.groot.education.repository.UserRepository;
import com.groot.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Override
    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        userRepository.save(user);
    }

    @Override
    public void modify(User user) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Course> findCourses() {
        return null;
    }
}
