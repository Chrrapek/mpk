package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.VehicleEntity;
import com.snoreware.mpk.model.input.VehicleDTO;
import com.snoreware.mpk.model.output.BusDTO;
import com.snoreware.mpk.repos.BusCourseRepository;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bus")
public class BusController {
    private BusRepository busRepository;
    private BusCourseRepository busCourseRepository;

    public BusController(BusRepository busRepository, BusCourseRepository busCourseRepository) {
        this.busRepository = busRepository;
        this.busCourseRepository = busCourseRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add")
    public void addBus(@RequestBody VehicleDTO vehicleDTO) {
        BusEntity newBus = new BusEntity(vehicleDTO.getLowFloor(), vehicleDTO.getArticulated());
        busRepository.save(newBus);
        log.info(String.format("Added bus (ID: %d, articulated: %b, lowFloor: %b)",
                newBus.getVehicleNumber(),
                newBus.getArticulated(),
                newBus.getLowFloor()));
    }

    @DeleteMapping("/remove/{id}")
    public void removeBus(@PathVariable Long id) {
        busRepository.deleteById(id);
        log.info(String.format("Removed bus with id %d", id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updateBus(@RequestBody VehicleDTO vehicleDTO, @PathVariable Long id) {
        BusEntity busToUpdate = busRepository.findByVehicleNumber(id);
        if (vehicleDTO.getLowFloor() != null)
            busToUpdate.setLowFloor(vehicleDTO.getLowFloor());
        if (vehicleDTO.getArticulated() != null)
            busToUpdate.setArticulated(vehicleDTO.getArticulated());

        busRepository.save(busToUpdate);
        log.info("Updated bus with id" + id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllBuses() {
        List<BusEntity> buses = busRepository.findAllByOrderByVehicleNumberAsc();

        List<Long> response = buses.stream()
                .map(VehicleEntity::getVehicleNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/articulated")
    public ResponseEntity<List<Long>> getOnlyArticulated() {
        List<BusEntity> buses = busRepository.findByArticulatedOrderByVehicleNumberDesc(true);

        List<Long> result = buses.stream()
                .filter(busEntity -> busEntity.getBusCourses().size() == 0)
                .map(VehicleEntity::getVehicleNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lowFloor")
    public ResponseEntity<List<Long>> getOnlyLowFloor() {
        List<BusEntity> buses = busRepository.findByLowFloorOrderByVehicleNumberDesc(true);

        List<Long> result = buses.stream()
                .filter(busEntity -> busEntity.getBusCourses().size() == 0)
                .map(VehicleEntity::getVehicleNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/lowFloorAndArt")
    public ResponseEntity<List<Long>> getLowFloorAndArticulated() {
        List<BusEntity> buses = busRepository.findByLowFloorAndArticulated(true, true);

        List<Long> result = buses.stream()
                .filter(busEntity -> busEntity.getBusCourses().size() == 0)
                .map(VehicleEntity::getVehicleNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<Long>> getAllBuses(@RequestParam boolean notBroken) {
        List<BusEntity> buses = busRepository.findAllByOrderByVehicleNumberAsc();

        List<Long> response = buses.stream()
                .filter(busEntity -> notBroken
                        && !busEntity.getVehicleBreakdown()
                        && busEntity.getBusCourses().size() == 0)
                .map(VehicleEntity::getVehicleNumber)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/failure/{id}")
    public ResponseEntity changeBreakdownStatus(@PathVariable Long id) {
        BusEntity busToChange = busRepository.findByVehicleNumber(id);
        busToChange.setVehicleBreakdown(!busToChange.getVehicleBreakdown());

        if (busToChange.getVehicleBreakdown()) {
            List<UUID> courses = busToChange.getUUIDList();
            courses.forEach(course -> busCourseRepository.deleteById(course));
            busToChange.setBusCourses(new ArrayList<>());
        }

        busRepository.save(busToChange);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getOneBusInfo(@PathVariable Long id) {
        BusEntity bus = busRepository.findByVehicleNumber(id);
        BusDTO result = BusDTO.dtoFromEntity(bus);

        return ResponseEntity.ok().body(result);
    }
}
