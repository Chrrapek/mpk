package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.input.DriverDTO;
import com.snoreware.mpk.model.output.OutDriverDTO;
import com.snoreware.mpk.repos.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity deleteDriver(@PathVariable UUID uid) {
        repository.deleteById(uid);

        log.info("Deleted driver with id %d", uid);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{uid}")
    public ResponseEntity<Object> updateSalary(@RequestBody DriverDTO driver, @PathVariable UUID uid) {
        DriverEntity driverToUpdate = repository.findByDriverId(uid);
        if (driver.getName() != null) driverToUpdate.setName(driver.getName());
        if (driver.getSurname() != null) driverToUpdate.setSurname(driver.getSurname());
        if (driver.getSex() != null) driverToUpdate.setSex(driver.getSex());
        if (driver.getSalary() != null) driverToUpdate.setSalary(driver.getSalary());
        repository.save(driverToUpdate);

        log.info(String.format("Changed driver %s", driver.getDriverId()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutDriverDTO>> getAllDrivers() {
        List<DriverEntity> drivers = repository.findAllByOrderByDriverIdAsc();
        List<OutDriverDTO> result = new ArrayList<>();

        for (DriverEntity driverEntity : drivers) {
            result.add(new OutDriverDTO(
                    driverEntity.getDriverId(),
                    driverEntity.getName(),
                    driverEntity.getSurname()));
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getOneDriver(@PathVariable UUID id) {
        DriverEntity driver = repository.findByDriverId(id);
        DriverDTO result = new DriverDTO(
                driver.getDriverId(),
                driver.getName(),
                driver.getSurname(),
                driver.getSex(),
                driver.getSalary(),
                driver.getUUIDOfTramCourses(),
                driver.getUUIDOfBusCourses());

        return ResponseEntity.ok().body(result);
    }
}
