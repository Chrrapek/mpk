package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.model.BusDTO;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class BusController {
    @Autowired
    BusRepository repository;
    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("addBus")
    public void addBus(@RequestBody BusDTO busDTO) {
        BusEntity newBus = new BusEntity(busDTO.isLowFloor(), busDTO.isArticulated());
        repository.save(newBus);
        log.info(String.format("Added bus (ID: %d, articulated: %b, lowFloor: %b)",
                newBus.getVehicleNumber(),
                newBus.isArticulated(),
                newBus.isLowFloor()));
    }

    @DeleteMapping("removeBus")
    public void removeBus(@RequestParam(value = "id") Long busId) {
        repository.deleteById(busId);
        log.info(String.format("Removed bus of id %d", busId));
    }
}
