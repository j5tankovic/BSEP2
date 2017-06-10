package com.groot.education.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique=true)
    private String name;

    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REMOVE})
    private List<Announcement> announcements;

    @PreRemove
    public void removeUserFromCourse() {
        for (User user: users) {
            user.getCourses().remove(this);
        }
        users.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        return name.equals(course.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setPeople(List<User> users) {
        this.users = users;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}
