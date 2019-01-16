package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.input.VehicleDTO;
import com.snoreware.mpk.model.output.TramDTO;
import com.snoreware.mpk.repos.TramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tram")
public class TramController {
    private TramRepository tramRepository;

    public TramController(TramRepository tramRepository) {
        this.tramRepository = tramRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add")
    public void addTram(@RequestBody VehicleDTO vehicleDTO) {
        TramEntity newTram = new TramEntity(vehicleDTO.getLowFloor(), vehicleDTO.getNumberOfWagons());
        tramRepository.save(newTram);

        log.info(String.format("Added tram (ID: %d, numberOfWagons: %b, lowFloor: %b)",
                newTram.getVehicleNumber(),
                newTram.getNumberOfWagons(),
                newTram.getLowFloor()));
    }

    @DeleteMapping("/remove/{id}")
    public void removeTram(@PathVariable Long id) {
        tramRepository.deleteById(id);

        log.info(String.format("Removed tram of id %d", id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updateTram(@RequestBody VehicleDTO vehicleDTO, @PathVariable Long id) {
        TramEntity tramToUpdate = tramRepository.findByVehicleNumber(id);
        if (vehicleDTO.getLowFloor() != null)
            tramToUpdate.setLowFloor(vehicleDTO.getLowFloor());
        if (vehicleDTO.getNumberOfWagons() != null)
            tramToUpdate.setNumberOfWagons(vehicleDTO.getNumberOfWagons());

        tramRepository.save(tramToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllTrams(@RequestParam boolean notBroken) {
        List<TramEntity> trams = tramRepository.findAllByOrderByVehicleNumberDesc();

        List<Long> response = new ArrayList<>();
        for (TramEntity tramEntity : trams) {
            if (notBroken && !tramEntity.getVehicleBreakdown())
                response.add(tramEntity.getVehicleNumber());
        }

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/failure/{id}")
    public ResponseEntity changeBreakdownStatus(@PathVariable Long id) {
        TramEntity tramToChange = tramRepository.findByVehicleNumber(id);
        tramToChange.setVehicleBreakdown(!tramToChange.getVehicleBreakdown());

        tramRepository.save(tramToChange);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TramDTO> getOneBusInfo(@PathVariable Long id) {
        TramEntity bus = tramRepository.findByVehicleNumber(id);
        TramDTO result = new TramDTO(
                bus.getVehicleNumber(),
                bus.getNumberOfWagons(),
                bus.getLowFloor(),
                bus.getUUIDList(),
                bus.getVehicleBreakdown());

        return ResponseEntity.ok().body(result);
    }
}
