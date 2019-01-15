package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusCourseEntity;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.RouteDTO;
import com.snoreware.mpk.repos.BusCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/busCourse")
@RestController
public class BusCourseController {

    private BusCourseRepository busCourseRepository;
    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    public BusCourseController(BusCourseRepository busCourseRepository) {
        this.busCourseRepository = busCourseRepository;
    }

    @GetMapping("/all")
    private ResponseEntity<List<BusCourseEntity>> getAllBusCourses() {
        List<BusCourseEntity> busCourses = busCourseRepository.findAllByOrderByCourseIdDesc();

        return ResponseEntity.ok().body(busCourses);
    }

    @PostMapping("/add")
    private ResponseEntity addBusCourse(@RequestBody CourseDTO courseDTO) {
        BusCourseEntity busCourse = new BusCourseEntity(
                courseDTO.getLowFloorNeeded(),
                courseDTO.getRouteNumber(),
                courseDTO.getArticulatedNeeded());

        if (courseDTO.getRouteNumber() != null)
            busCourse.setRoute(new RouteEntity(courseDTO.getRouteNumber()));
        if (courseDTO.getDriverId() != null)
            busCourse.setDriver(new DriverEntity(courseDTO.getDriverId()));

        busCourseRepository.save(busCourse);

        log.info(String.format("Added new bus course on route %d", courseDTO.getRouteNumber()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    private ResponseEntity removeBusCourse(@RequestBody CourseDTO courseDTO) {
        busCourseRepository.deleteById(courseDTO.getCourseId());

        log.info(String.format("Removed course with UUID %s", courseDTO.getCourseId()));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/driver")
    private ResponseEntity assignBusDriver(@RequestBody DriverDTO driverDTO, @RequestParam UUID courseId) {
        DriverEntity assignedDriver = new DriverEntity(driverDTO.getDriverId());
        BusCourseEntity courseToUpdate = busCourseRepository.findByCourseId(courseId);

        courseToUpdate.setDriver(assignedDriver);
        busCourseRepository.save(courseToUpdate);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/route")
    private ResponseEntity assignRoute(@RequestBody RouteDTO routeDTO, @RequestParam UUID courseId) {
        RouteEntity route = new RouteEntity(routeDTO.getRouteNumber());
        BusCourseEntity courseToUpdate = busCourseRepository.findByCourseId(courseId);

        courseToUpdate.setRoute(route);
        busCourseRepository.save(courseToUpdate);
        return ResponseEntity.ok().build();
    }
}
