package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.repos.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    private RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add/{id}")
    public ResponseEntity addRoute(@PathVariable Long routeNumber) {
        repository.save(new RouteEntity(routeNumber));

        log.info("Added route ", routeNumber);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeRoute(@PathVariable Long routeNumber) {
        repository.deleteById(routeNumber);

        log.info("Removed route ", routeNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllRoutes() {
        List<RouteEntity> routes = repository.findByOrderByRouteNumberAsc();
        List<Long> response = new ArrayList<>();
        for (RouteEntity routeEntity : routes) {
            response.add(routeEntity.getRouteNumber());
        }

        return ResponseEntity.ok().body(response);
    }
}
