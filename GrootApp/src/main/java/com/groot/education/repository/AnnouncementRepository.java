package com.groot.education.repository;

import com.groot.education.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnouncementRepository  extends JpaRepository<Announcement, Long>{

    Optional<Announcement> findById(long id);
}
