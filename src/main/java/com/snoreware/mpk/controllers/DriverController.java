package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.repos.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    private DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("/add")
    public ResponseEntity addBus(@RequestBody DriverDTO driverDTO) {
        DriverEntity newDriver = new DriverEntity(
                driverDTO.getName(),
                driverDTO.getSurname(),
                driverDTO.getSex(),
                driverDTO.getSalary()
        );
        repository.save(newDriver);

        log.info(String.format("Added driver (Name: %s, Surname: %s, id: %s)",
                newDriver.getName(),
                newDriver.getSurname(),
                newDriver.getDriverId()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteDriver(@RequestBody DriverDTO driver) {
        repository.deleteById(driver.getDriverId());

        log.info("Deleted driver with id %d", driver.getDriverId());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Object> updateSalary(@RequestBody DriverDTO driver) {
        DriverEntity driverToUpdate = repository.findByDriverId(driver.getDriverId());
        if (driver.getName() != null) driverToUpdate.setName(driver.getName());
        if (driver.getSurname() != null) driverToUpdate.setSurname(driver.getSurname());
        if (driver.getSex() != null) driverToUpdate.setSex(driver.getSex());
        if (driver.getSalary() != null) driverToUpdate.setSalary(driver.getSalary());
        repository.save(driverToUpdate);

        log.info(String.format("Changed driver %s", driver.getDriverId()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverEntity>> getAllDrivers() {
        List<DriverEntity> drivers = repository.findAllByOrderByDriverIdAsc();

        return ResponseEntity.ok().body(drivers);
    }
}
