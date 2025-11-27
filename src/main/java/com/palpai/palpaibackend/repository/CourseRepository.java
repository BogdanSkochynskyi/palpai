package com.palpai.palpaibackend.repository;

import com.palpai.palpaibackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

