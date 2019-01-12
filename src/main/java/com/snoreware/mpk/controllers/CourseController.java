package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusCourseEntity;
import com.snoreware.mpk.entities.TramCourseEntity;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.repos.BusCourseRepository;
import com.snoreware.mpk.repos.TramCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    private BusCourseRepository busCourseRepository;
    private TramCourseRepository tramCourseRepository;

    public CourseController(BusCourseRepository busCourseRepository,
                            TramCourseRepository tramCourseRepository) {
        this.busCourseRepository = busCourseRepository;
        this.tramCourseRepository = tramCourseRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/addTram")
    private ResponseEntity addTramCourse(@RequestBody CourseDTO courseDTO) {
        TramCourseEntity tramCourse = new TramCourseEntity(
                courseDTO.getLowFloorNeeded(),
                courseDTO.getRouteNumber(),
                courseDTO.getManyWagonsNeeded());
        tramCourseRepository.save(tramCourse);

        log.info(String.format("Added new tram course on route %d", courseDTO.getRouteNumber()));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/addBus")
    private ResponseEntity addBusCourse(@RequestBody CourseDTO courseDTO) {
        BusCourseEntity busCourse = new BusCourseEntity(
                courseDTO.getLowFloorNeeded(),
                courseDTO.getRouteNumber(),
                courseDTO.getArticulatedNeeded());
        busCourseRepository.save(busCourse);

        log.info(String.format("Added new bus course on route %d", courseDTO.getRouteNumber()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteBus")
    private ResponseEntity removeBusCourse(@RequestBody CourseDTO courseDTO) {
        busCourseRepository.deleteById(courseDTO.getCourseId());

        log.info(String.format("Removed course with UUID %s", courseDTO.getCourseId()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteTram")
    private ResponseEntity removeTramCourse(@RequestBody CourseDTO courseDTO) {
        tramCourseRepository.deleteById(courseDTO.getCourseId());

        log.info(String.format("Removed course with UUID %s", courseDTO.getCourseId()));

        return ResponseEntity.ok().build();
    }
}
