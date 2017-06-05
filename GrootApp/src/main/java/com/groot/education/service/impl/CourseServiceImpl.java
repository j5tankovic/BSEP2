package com.groot.education.service.impl;

import com.groot.education.model.Announcement;
import com.groot.education.model.Course;
import com.groot.education.model.User;
import com.groot.education.repository.AnnouncementRepository;
import com.groot.education.repository.CourseRepository;
import com.groot.education.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             AnnouncementRepository announcementRepository) {
        this.courseRepository = courseRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Optional<Course> findById(long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void add(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<Announcement> findAnnouncement(long announcementId) {
        return announcementRepository.findById(announcementId);
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    @Override
    public Announcement editAnnouncement(Announcement original, Announcement toModify) {
        original.setTitle(toModify.getTitle());
        original.setContent(toModify.getContent());
        original.setAnnouncedOn(new Date());
        return announcementRepository.save(original);
    }

    @Override
    public void deleteAnnouncement(long id, long announcementId) {
        announcementRepository.delete(announcementId);
    }

    @Override
    public List<User> findUsers(long id) {
        return null;
    }
}
