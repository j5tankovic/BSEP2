package com.groot.education.repository.customInterfaces;


import com.groot.education.model.Course;

import java.util.List;

public interface UserRepositoryCustom {

    List<Course> findCourses();
}
