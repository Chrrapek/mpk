package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.TramCourseEntity;
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
import java.util.UUID;

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

    @DeleteMapping("/remove")
    public void removeTram(@RequestBody VehicleDTO vehicleDTO) {
        tramRepository.deleteById(vehicleDTO.getVehicleNumber());

        log.info(String.format("Removed tram of id %d", vehicleDTO.getVehicleNumber()));
    }

    @PatchMapping("/update")
    public ResponseEntity updateTram(@RequestBody VehicleDTO vehicleDTO) {
        TramEntity tramToUpdate = tramRepository.findByVehicleNumber(vehicleDTO.getVehicleNumber());
        if (vehicleDTO.getLowFloor() != null)
            tramToUpdate.setLowFloor(vehicleDTO.getLowFloor());
        if (vehicleDTO.getNumberOfWagons() != null)
            tramToUpdate.setNumberOfWagons(vehicleDTO.getNumberOfWagons());

        tramRepository.save(tramToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<TramDTO>> getAllTrams() {
        List<TramEntity> trams = tramRepository.findAllByOrderByVehicleNumberDesc();

        List<TramDTO> response = new ArrayList<>();
        for (TramEntity tramEntity : trams) {
            response.add(new TramDTO(
                    tramEntity.getVehicleNumber(),
                    tramEntity.getNumberOfWagons(),
                    tramEntity.getLowFloor(),
                    getUUIDList(tramEntity.getTramCourses()),
                    tramEntity.getVehicleBreakdown()));
        }

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/failure")
    public ResponseEntity changeBreakdownStatus(@RequestBody VehicleDTO vehicleDTO) {
        TramEntity tramToChange = tramRepository.findByVehicleNumber(vehicleDTO.getVehicleNumber());
        tramToChange.setVehicleBreakdown(!tramToChange.getVehicleBreakdown());

        tramRepository.save(tramToChange);
        return ResponseEntity.ok().build();
    }

    public List<UUID> getUUIDList(List<TramCourseEntity> busCourses) {
        List<UUID> result = new ArrayList<>();
        for (TramCourseEntity course : busCourses)
            result.add(course.getCourseId());

        return result;
    }
}
