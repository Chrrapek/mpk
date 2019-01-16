package com.snoreware.mpk.controllers;

import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.model.input.StopDTO;
import com.snoreware.mpk.model.output.OutStopDTO;
import com.snoreware.mpk.repos.StopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/all")
    public ResponseEntity<List<OutStopDTO>> getAllStops(@RequestParam boolean notBroken) {
        List<StopEntity> stops = repository.findAllByOrderByStopNameDesc();
        List<OutStopDTO> result = new ArrayList<>();
        for (StopEntity stopEntity : stops) {
            if (notBroken && !stopEntity.isStopBreakdown())
                result.add(new OutStopDTO(
                    stopEntity.getStopId(),
                    stopEntity.getStopName()));
        }

        return ResponseEntity.ok().body(result);
    }
}
