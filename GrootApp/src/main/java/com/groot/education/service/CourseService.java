package com.groot.education.service;


import com.groot.education.model.Announcement;
import com.groot.education.model.Course;
import com.groot.education.model.User;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Optional<Course> findById(long id);

    List<Course> findAll();

    void add(Course course);

    void delete(long id);

    Optional<Announcement> findAnnouncement(long announcementId);

    void addAnnouncement(Announcement announcement);

    Announcement editAnnouncement(Announcement original, Announcement toModify);

    void deleteAnnouncement(long id, long announcementId);

    List<User> findUsers(long id);

    void addUserToCourse(Course course, User user);
}
