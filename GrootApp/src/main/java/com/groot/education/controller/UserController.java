package com.groot.education.controller;

import com.groot.education.controller.exception.NotFoundException;
import com.groot.education.dto.UserDTO;
import com.groot.education.model.Announcement;
import com.groot.education.model.Course;
import com.groot.education.model.User;
import com.groot.education.service.CourseService;
import com.groot.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;
    private CourseService courseService;

    @Autowired
    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Transactional
    @GetMapping("")
    public ResponseEntity findAll() {
        final List<User> users = userService.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADD_USER')")
    public ResponseEntity addUser(@RequestBody User userToAdd) {
        userService.add(userToAdd);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_USER')")
    public ResponseEntity deleteUser(@PathVariable long id) {
        userService.findById(id).orElseThrow(NotFoundException::new);
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('UPDATE_PROFILE')")
    public ResponseEntity editProfile(@PathVariable long id,
                                      @RequestBody UserDTO userDTO) {
        User original = userService.findById(id).orElseThrow(NotFoundException::new);
        User toEdit = convertFromDTO(userDTO);
        userService.edit(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/courses")
    public ResponseEntity getCourses(@PathVariable long id) {
        final User user = userService.findById(id).orElseThrow(NotFoundException::new);
        final List<Course> courses = user.getCourses();

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    private User convertFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        return user;
    }


}
