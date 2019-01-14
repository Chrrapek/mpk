package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.model.VehicleDTO;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController {
    private BusRepository busRepository;

    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
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

    @DeleteMapping("/remove")
    public void removeBus(@RequestBody VehicleDTO vehicleDTO) {
        busRepository.deleteById(vehicleDTO.getVehicleNumber());
        log.info(String.format("Removed bus of id %d", vehicleDTO.getVehicleNumber()));
    }

    @PatchMapping("/update")
    public ResponseEntity updateBus(@RequestBody VehicleDTO vehicleDTO) {
        BusEntity busToUpdate = busRepository.findByVehicleNumber(vehicleDTO.getVehicleNumber());
        if (vehicleDTO.getLowFloor() != null)
            busToUpdate.setLowFloor(vehicleDTO.getLowFloor());
        if (vehicleDTO.getArticulated() != null)
            busToUpdate.setArticulated(vehicleDTO.getArticulated());

        busRepository.save(busToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusEntity>> getAllBuses() {
        List<BusEntity> buses = busRepository.findAllByOrderByVehicleNumberAsc();

        return ResponseEntity.ok().body(buses);
    }

    @PostMapping("/failure")
    public ResponseEntity changeBreakdownStatus(@RequestBody VehicleDTO vehicleDTO) {
        BusEntity busToChange = busRepository.findByVehicleNumber(vehicleDTO.getVehicleNumber());
        busToChange.setVehicleBreakdown(!busToChange.getVehicleBreakdown());

        busRepository.save(busToChange);
        return ResponseEntity.ok().build();
    }
}
