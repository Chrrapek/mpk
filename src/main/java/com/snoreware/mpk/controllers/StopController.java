package com.snoreware.mpk.controllers;

import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.repos.StopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/remove")
    public ResponseEntity removeStop(@RequestBody StopDTO stopDTO) {
        if (stopDTO.getStopId() != null) repository.deleteById(stopDTO.getStopId());
        else if (stopDTO.getStopName() != null) repository.deleteByStopName(stopDTO.getStopName());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity updateStop(@RequestBody StopDTO stopDTO) {
        StopEntity stopToUpdate;
        stopToUpdate = getInstanceOrReturnNull(stopDTO);

        if (stopToUpdate != null)
            if (stopDTO.getStopName() != null)
                stopToUpdate.setStopName(stopDTO.getStopName());
            else return ResponseEntity.badRequest().build();
        else return ResponseEntity.badRequest().build();

        repository.save(stopToUpdate);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/failure")
    public ResponseEntity changeStopBreakdownStatus(@RequestBody StopDTO stopDTO) {
        StopEntity stopToUpdate;
        stopToUpdate = getInstanceOrReturnNull(stopDTO);

        if (stopToUpdate != null) stopToUpdate.setStopBreakdown(!stopToUpdate.isStopBreakdown());
        else return ResponseEntity.badRequest().build();

        repository.save(stopToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<StopEntity>> getAllStops() {
        List<StopEntity> stops = repository.findAllByOrderByStopNameDesc();

        return ResponseEntity.ok().body(stops);
    }

    private StopEntity getInstanceOrReturnNull(StopDTO stopDTO) {
        StopEntity stopToUpdate;
        if (stopDTO.getStopId() != null) stopToUpdate = repository.findByStopId(stopDTO.getStopId());
        else if (stopDTO.getStopName() != null) stopToUpdate = repository.findByStopName(stopDTO.getStopName());
        else return null;

        return stopToUpdate;
    }
}
