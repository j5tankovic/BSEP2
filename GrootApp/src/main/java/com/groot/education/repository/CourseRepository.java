package com.groot.education.repository;

import com.groot.education.model.Announcement;
import com.groot.education.model.Course;
import com.groot.education.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAll();

    Optional<Course> findById(long id);

    List<Announcement> findAnnouncementsById(long id);

    List<User> findUsersById(long id);

}
