package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.input.VehicleDTO;
import com.snoreware.mpk.model.output.TramDTO;
import com.snoreware.mpk.repos.TramCourseRepository;
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
    private TramCourseRepository tramCourseRepository;

    public TramController(TramRepository tramRepository, TramCourseRepository tramCourseRepository) {
        this.tramRepository = tramRepository;
        this.tramCourseRepository = tramCourseRepository;
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

    @GetMapping("/byStatus")
    public ResponseEntity<List<Long>> getFilteredTrams(@RequestParam boolean notBroken) {
        List<TramEntity> trams = tramRepository.findAllByOrderByVehicleNumberDesc();

        List<Long> response = new ArrayList<>();

        trams.forEach(tramEntity -> {
            if (notBroken && !tramEntity.getVehicleBreakdown() && tramEntity.getTramCourses().size() == 0)
                response.add(tramEntity.getVehicleNumber());
        });

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllTrams() {
        List<TramEntity> trams = tramRepository.findAllByOrderByVehicleNumberDesc();

        List<Long> response = new ArrayList<>();
        trams.forEach(tramEntity -> response.add(tramEntity.getVehicleNumber()));

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/number")
    public ResponseEntity<List<Long>> getOnlyArticulated(@RequestParam int minimalWagons) {
        List<TramEntity> buses = tramRepository.findByNumberOfWagonsGreaterThanEqual(minimalWagons);

        List<Long> result = new ArrayList<>();
        buses.forEach(tramEntity -> {
            if (tramEntity.getTramCourses().size() == 0)
                result.add(tramEntity.getVehicleNumber());
        });

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lowFloor")
    public ResponseEntity<List<Long>> getOnlyLowFloor() {
        List<TramEntity> buses = tramRepository.findByLowFloorOrderByVehicleNumberDesc(true);

        List<Long> result = new ArrayList<>();
        buses.forEach(tramEntity -> {
            if (tramEntity.getTramCourses().size() == 0)
                result.add(tramEntity.getVehicleNumber());
        });

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lowFloorAndNumber")
    public ResponseEntity<List<Long>> getLowFloorAndAtLeastWagons(@RequestParam int minimalWagons) {
        List<TramEntity> buses =
                tramRepository.findByNumberOfWagonsGreaterThanEqualAndLowFloor(minimalWagons, true);

        List<Long> result = new ArrayList<>();
        buses.forEach(tramEntity -> {
            if (tramEntity.getTramCourses().size() == 0)
                result.add(tramEntity.getVehicleNumber());
        });

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/failure/{id}")
    public ResponseEntity changeBreakdownStatus(@PathVariable Long id) {
        TramEntity tramToChange = tramRepository.findByVehicleNumber(id);
        tramToChange.setVehicleBreakdown(!tramToChange.getVehicleBreakdown());

        if (tramToChange.getVehicleBreakdown()) {
            List<UUID> courses = tramToChange.getUUIDList();
            courses.forEach(course -> tramCourseRepository.deleteById(course));
            tramToChange.setTramCourses(new ArrayList<>());
        }

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
