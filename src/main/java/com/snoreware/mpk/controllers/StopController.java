package com.snoreware.mpk.controllers;

import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.model.input.StopDTO;
import com.snoreware.mpk.model.output.OutStopDTO;
import com.snoreware.mpk.repos.StopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/stop")
@RestController
public class StopController {
    private StopRepository repository;

    public StopController(StopRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/add")
    public ResponseEntity addStop(@RequestBody StopDTO stopDTO) {
        StopEntity stopToAdd = new StopEntity(stopDTO.getStopName());
        repository.save(stopToAdd);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{uid}")
    public ResponseEntity removeStop(@PathVariable UUID uid) {
        repository.deleteById(uid);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{uid}")
    public ResponseEntity updateStop(@RequestBody StopDTO stopDTO, @PathVariable UUID uid) {
        StopEntity stopToUpdate = repository.findByStopId(uid);
        stopToUpdate.setStopName(stopDTO.getStopName());

        repository.save(stopToUpdate);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/failure/{uid}")
    public ResponseEntity changeStopBreakdownStatus(@PathVariable UUID uid) {
        StopEntity stopToUpdate = repository.findByStopId(uid);
        stopToUpdate.setStopBreakdown(!stopToUpdate.isStopBreakdown());

        repository.save(stopToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<OutStopDTO>> getFilteredStops(@RequestParam boolean notBroken) {
        List<StopEntity> stops = repository.findAllByOrderByStopNameDesc();
        List<OutStopDTO> result = stops.stream()
                .filter(stopEntity -> notBroken && !stopEntity.isStopBreakdown())
                .map(stopEntity -> new OutStopDTO(stopEntity.getStopId(), stopEntity.getStopName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutStopDTO>> getAllStops() {
        List<StopEntity> stops = repository.findAllByOrderByStopNameDesc();
        List<OutStopDTO> result = stops.stream()
                .map(stopEntity -> new OutStopDTO(
                    stopEntity.getStopId(),
                        stopEntity.getStopName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{stopId}")
    public ResponseEntity<StopDTO> getSingleStop(@PathVariable UUID stopId) {
        StopEntity stop = repository.findByStopId(stopId);
        StopDTO result = new StopDTO(stop.getStopId(), stop.getStopName(), stop.isStopBreakdown());

        return ResponseEntity.ok().body(result);
    }
}
