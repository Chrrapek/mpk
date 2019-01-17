package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.RouteEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.StopOnRouteEntity;
import com.snoreware.mpk.model.input.StopsOnRouteDTO;
import com.snoreware.mpk.model.output.OutStopDTO;
import com.snoreware.mpk.repos.StopsOnRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity addOrModifyRoute(@RequestBody StopsOnRouteDTO dto, @PathVariable Long routeNumber) {
        RouteEntity route = new RouteEntity(routeNumber);
        repository.deleteByRoute(route);

        List<UUID> stopsFromDto = dto.getStops();

        for (int i = 1; i <= stopsFromDto.size(); i++) {
            StopOnRouteEntity newStop = new StopOnRouteEntity(
                    i,
                    new StopEntity(stopsFromDto.get(i)),
                    route);

            repository.save(newStop);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{routeNumber}")
    public ResponseEntity<List<OutStopDTO>> getOneRoute(@PathVariable Long routeNumber) {
        List<StopOnRouteEntity> stops = repository.findByRoute(new RouteEntity(routeNumber));
        List<OutStopDTO> result = new ArrayList<>();

        for (StopOnRouteEntity stop : stops) {
            if (!stop.getStop().isStopBreakdown())
                result.add(new OutStopDTO(
                        stop.getStopNumber(),
                        stop.getStop().getStopId(),
                        stop.getStop().getStopName()
                ));
        }

        result.sort(Comparator.comparingInt(OutStopDTO::getStopNumber));

        return ResponseEntity.ok().body(result);
    }
}
