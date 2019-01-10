package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.TramCourseEntity;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.repos.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {
    private CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/addTram")
    private ResponseEntity addCourse(@RequestBody CourseDTO courseDTO) {
        TramCourseEntity tramCourse = new TramCourseEntity(
                courseDTO.isLowFloorNeeded(),
                courseDTO.getCourseNumber(),
                courseDTO.isManyWagonsNeeded()
        );
        repository.save(tramCourse);

        log.info(String.format("Added new tram course with number %d", tramCourse.getCourseNumber()));

        return ResponseEntity.ok().build();
    }
}
