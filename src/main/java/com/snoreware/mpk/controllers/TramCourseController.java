package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.TramCourseEntity;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.RouteDTO;
import com.snoreware.mpk.repos.TramCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tramCourse")
public class TramCourseController {
    private TramCourseRepository tramCourseRepository;

    public TramCourseController(TramCourseRepository tramCourseRepository) {
        this.tramCourseRepository = tramCourseRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add")
    private ResponseEntity addTramCourse(@RequestBody CourseDTO courseDTO) {
        TramCourseEntity tramCourse = new TramCourseEntity(
                courseDTO.getLowFloorNeeded(),
                courseDTO.getRouteNumber(),
                courseDTO.getManyWagonsNeeded());

        if (courseDTO.getRouteNumber() != null)
            tramCourse.setRoute(new RouteEntity(courseDTO.getRouteNumber()));
        if (courseDTO.getDriverId() != null)
            tramCourse.setDriver(new DriverEntity(courseDTO.getDriverId()));

        tramCourseRepository.save(tramCourse);

        log.info(String.format("Added new tram course on route %d", courseDTO.getRouteNumber()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    private ResponseEntity removeTramCourse(@RequestBody CourseDTO courseDTO) {
        tramCourseRepository.deleteById(courseDTO.getCourseId());

        log.info(String.format("Removed course with UUID %s", courseDTO.getCourseId()));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    private ResponseEntity<List<TramCourseEntity>> getAllTramCourses() {
        List<TramCourseEntity> tramCourses = tramCourseRepository.findAllByOrderByCourseIdDesc();

        return ResponseEntity.ok().body(tramCourses);
    }

    @PostMapping("/driver")
    private ResponseEntity assignTramDriver(@RequestBody DriverDTO driverDTO, @RequestParam UUID courseNumber) {
        DriverEntity assignedDriver = new DriverEntity(driverDTO.getDriverId());
        TramCourseEntity courseToUpdate = tramCourseRepository.findByCourseId(courseNumber);

        courseToUpdate.setDriver(assignedDriver);
        tramCourseRepository.save(courseToUpdate);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/route")
    private ResponseEntity assignRoute(@RequestBody RouteDTO routeDTO, @RequestParam UUID courseId) {
        RouteEntity route = new RouteEntity(routeDTO.getRouteNumber());
        TramCourseEntity courseToUpdate = tramCourseRepository.findByCourseId(courseId);

        courseToUpdate.setRoute(route);
        tramCourseRepository.save(courseToUpdate);
        return ResponseEntity.ok().build();
    }

}
