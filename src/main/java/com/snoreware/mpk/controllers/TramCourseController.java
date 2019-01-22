package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.TramCourseEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.input.CourseDTO;
import com.snoreware.mpk.model.input.DriverDTO;
import com.snoreware.mpk.model.output.OutCourseDTO;
import com.snoreware.mpk.repos.TramCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        tramCourse.setRoute(new RouteEntity(courseDTO.getRouteNumber()));
        tramCourse.setDriver(new DriverEntity(courseDTO.getDriverId()));
        tramCourse.setTram(new TramEntity(courseDTO.getVehicleNumber()));

        tramCourseRepository.save(tramCourse);

        log.info(String.format("Added new tram course on route %d", courseDTO.getRouteNumber()));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity removeTramCourse(@PathVariable UUID id) {
        tramCourseRepository.deleteById(id);

        log.info(String.format("Removed course with UUID %s", id));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    private ResponseEntity<List<OutCourseDTO>> getAllTramCourses() {
        List<TramCourseEntity> tramCourses = tramCourseRepository.findAllByOrderByCourseIdDesc();
        List<OutCourseDTO> result = new ArrayList<>();

        result = tramCourses.stream()
                .map(OutCourseDTO::dtoFromTramCourse)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{courseId}/driver")
    private ResponseEntity assignTramDriver(@RequestBody DriverDTO driverDTO, @PathVariable UUID courseNumber) {
        DriverEntity assignedDriver = new DriverEntity(driverDTO.getDriverId());
        TramCourseEntity courseToUpdate = tramCourseRepository.findByCourseId(courseNumber);

        courseToUpdate.setDriver(assignedDriver);
        tramCourseRepository.save(courseToUpdate);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/route")
    private ResponseEntity assignRoute(@RequestParam Long routeNumber, @PathVariable UUID courseId) {
        RouteEntity route = new RouteEntity(routeNumber);
        TramCourseEntity courseToUpdate = tramCourseRepository.findByCourseId(courseId);

        courseToUpdate.setRoute(route);
        tramCourseRepository.save(courseToUpdate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/tram")
    private ResponseEntity changeTram(@RequestParam Long vehicleNumber, @PathVariable UUID courseId) {
        TramEntity tram = new TramEntity(vehicleNumber);
        TramCourseEntity courseToUpdate = tramCourseRepository.findByCourseId(courseId);

        courseToUpdate.setTram(tram);
        tramCourseRepository.save(courseToUpdate);
        return ResponseEntity.ok().build();
    }
}
