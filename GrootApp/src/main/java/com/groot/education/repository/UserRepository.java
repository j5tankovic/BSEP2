package com.groot.education.repository;

import com.groot.education.model.Course;
import com.groot.education.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);

    List<Course> findCoursesById(long id);

}
