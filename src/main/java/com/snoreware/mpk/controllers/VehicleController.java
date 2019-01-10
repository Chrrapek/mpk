package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.VehicleDTO;
import com.snoreware.mpk.repos.BusRepository;
import com.snoreware.mpk.repos.TramRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private BusRepository busRepository;
    private TramRepository tramRepository;

    public VehicleController(BusRepository busRepository, TramRepository tramRepository) {
        this.busRepository = busRepository;
        this.tramRepository = tramRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/addBus")
    public void addBus(@RequestBody VehicleDTO vehicleDTO) {
        BusEntity newBus = new BusEntity(vehicleDTO.isLowFloor(), vehicleDTO.isArticulated());
        busRepository.save(newBus);
        log.info(String.format("Added bus (ID: %d, articulated: %b, lowFloor: %b)",
                newBus.getVehicleNumber(),
                newBus.isArticulated(),
                newBus.isLowFloor()));
    }

    @DeleteMapping("/removeBus")
    public void removeBus(@RequestBody VehicleDTO vehicleDTO) {
        busRepository.deleteById(vehicleDTO.getVehicleNumber());
        log.info(String.format("Removed bus of id %d", vehicleDTO.getVehicleNumber()));
    }

    @PostMapping("/addTram")
    public void addTram(@RequestBody VehicleDTO vehicleDTO) {
        TramEntity newTram = new TramEntity(vehicleDTO.isLowFloor(), vehicleDTO.getNumberOfWagons());
        tramRepository.save(newTram);

        log.info(String.format("Added tram (ID: %d, numberOfWagons: %b, lowFloor: %b)",
                newTram.getVehicleNumber(),
                newTram.getNumberOfWagons(),
                newTram.isLowFloor()));
    }

    @DeleteMapping("/removeTram")
    public void removeTram(@RequestBody VehicleDTO vehicleDTO) {
        tramRepository.deleteById(vehicleDTO.getVehicleNumber());

        log.info(String.format("Removed tram of id %d", vehicleDTO.getVehicleNumber()));
    }
}
