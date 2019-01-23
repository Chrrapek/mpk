package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.StopOnRouteEntity;
import com.snoreware.mpk.model.output.OutStopDTO;
import com.snoreware.mpk.repos.StopsOnRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/routeStops")
public class StopsOnRouteController {
    private StopsOnRouteRepository repository;

    public StopsOnRouteController(StopsOnRouteRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/modify/{routeNumber}")
    @Transactional
    public ResponseEntity addOrModifyRoute(@RequestBody List<UUID> stopsFromDto,
                                           @PathVariable Long routeNumber) {
        RouteEntity route = new RouteEntity(routeNumber);
        repository.deleteByRoute(route);

        for (int i = 0; i < stopsFromDto.size(); i++) {
            StopOnRouteEntity newStop = new StopOnRouteEntity(
                    i + 1,
                    new StopEntity(stopsFromDto.get(i)),
                    route);

            repository.save(newStop);
        }
        log.info("Changed route " + routeNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{routeNumber}")
    public ResponseEntity<List<OutStopDTO>> getOneRoute(@PathVariable Long routeNumber) {
        List<StopOnRouteEntity> stops = repository.findByRoute(new RouteEntity(routeNumber));

        List<OutStopDTO> result = stops.stream()
                .filter(stop -> !stop.getStop().isStopBreakdown())
                .map(stop -> new OutStopDTO(
                        stop.getStopNumber(),
                        stop.getStop().getStopId(),
                        stop.getStop().getStopName()))
                .sorted(Comparator.comparingInt(OutStopDTO::getStopNumber))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }
}
