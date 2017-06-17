package com.groot.education.controller;

import com.groot.education.controller.exception.NotFoundException;
import com.groot.education.dto.AnnouncementDTO;
import com.groot.education.dto.CourseUserDTO;
import com.groot.education.model.Announcement;
import com.groot.education.model.Course;
import com.groot.education.model.User;
import com.groot.education.service.CourseService;
import com.groot.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Transactional
    @GetMapping("")
    public ResponseEntity findAll() {
        final List<Course> courses = courseService.findAll();

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity findOne(@PathVariable long id) {
        final Course course = courseService.findById(id).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ADD_COURSE')")
    public ResponseEntity addCourse(@RequestBody Course course,
                                    @AuthenticationPrincipal User user) {
        courseService.add(course);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('DELETE_COURSE')")
    public ResponseEntity deleteCourse(@PathVariable long id) {
        courseService.findById(id).orElseThrow(NotFoundException::new);
        courseService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/announcements")
    public ResponseEntity findAnnouncements(@PathVariable long id) {
        final Course course = courseService.findById(id).orElseThrow(NotFoundException::new);
        final List<Announcement> announcements = course.getAnnouncements();

        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/announcements/{announcementId}")
    public ResponseEntity findAnnouncement(@PathVariable long id,
                                           @PathVariable long announcementId) {
        courseService.findById(id).orElseThrow(NotFoundException::new);
        final Announcement announcement = courseService.findAnnouncement(announcementId).orElseThrow(NotFoundException::new);

        return new ResponseEntity<>(announcement, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{id}/announcements")
    @PreAuthorize("hasAnyAuthority('ADD_ANNOUNCEMENT')")
    public ResponseEntity addAnnouncement(@PathVariable long id,
                                          @RequestBody AnnouncementDTO announcementToAdd,
                                          @AuthenticationPrincipal User user) {
        Course course = courseService.findById(id).orElseThrow(NotFoundException::new);
        Announcement announcement = convertFromDTO(announcementToAdd);
        announcement.setAuthor(user);
        announcement.setCourse(course);
        courseService.addAnnouncement(announcement);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}/announcements/{announcementId}")
    @PreAuthorize("hasAnyAuthority('EDIT_ANNOUNCEMENT')")
    public ResponseEntity editAnnouncement(@PathVariable long id,
                                             @PathVariable long announcementId,
                                             @RequestBody AnnouncementDTO announcementDTO) {
        courseService.findById(id).orElseThrow(NotFoundException::new);
        final Announcement announcement = courseService.findAnnouncement(announcementId).orElseThrow(NotFoundException::new);
        final Announcement announcementToEdit = convertFromDTO(announcementDTO);
        final Announcement edited = courseService.editAnnouncement(announcement, announcementToEdit);

        return new ResponseEntity<>(edited, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}/announcements/{announcementId}")
    @PreAuthorize("hasAnyAuthority('DELETE_ANNOUNCEMENT')")
    public ResponseEntity deleteAnnouncement(@PathVariable long id,
                                             @PathVariable long announcementId) {
        courseService.findById(id).orElseThrow(NotFoundException::new);
        courseService.findAnnouncement(announcementId).orElseThrow(NotFoundException::new);
        courseService.deleteAnnouncement(id, announcementId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}/users")
    public ResponseEntity getPeople(@PathVariable long id) {
        final Course course = courseService.findById(id).orElseThrow(NotFoundException::new);
        final List<User> people = course.getUsers();

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{id}/users")
    @PreAuthorize("hasAnyAuthority('ADD_USER_TO_COURSE')")
    public ResponseEntity addUserToCourse(@PathVariable long id,
                                          @RequestBody CourseUserDTO courseUser) {
        final String username = courseUser.getUsername();

        final Course course = courseService.findById(id).orElseThrow(NotFoundException::new);
        final User user = userService.findByUsername(username).orElseThrow(NotFoundException::new);

        courseService.addUserToCourse(course, user);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    private Announcement convertFromDTO(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());
        announcement.setAnnouncedOn(new Date());
        return announcement;
    }

}
