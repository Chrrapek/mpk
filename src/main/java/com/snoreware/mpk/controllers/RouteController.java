package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.model.RouteDTO;
import com.snoreware.mpk.repos.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {
    private RouteRepository repository;

    public RouteController(RouteRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add")
    public ResponseEntity addRoute(@RequestBody RouteDTO routeDTO) {
        repository.save(new RouteEntity(routeDTO.getRouteNumber()));

        log.info("Added route ", routeDTO.getRouteNumber());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity removeRoute(@RequestBody RouteDTO routeDTO) {
        repository.deleteById(routeDTO.getRouteNumber());

        log.info("Removed route ", routeDTO.getRouteNumber());
        return ResponseEntity.ok().build();
    }
}
