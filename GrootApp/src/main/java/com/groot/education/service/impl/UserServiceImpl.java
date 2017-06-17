package com.groot.education.service.impl;

import com.groot.education.model.Course;
import com.groot.education.model.Role;
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
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Override
    public void add(User user) {
        setPermissionsAccordingToRole(user);
        userRepository.save(user);
    }

    @Override
    public User edit(User original, User toEdit) {
        original.setName(toEdit.getName());
        original.setSurname(toEdit.getSurname());

        return userRepository.save(original);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public List<Course> findCourses() {
        return null;
    }

    private void setPermissionsAccordingToRole(User user) {
        Role role = user.getRole();
        user.setPermissions(role.getPermissions());
    }
}
