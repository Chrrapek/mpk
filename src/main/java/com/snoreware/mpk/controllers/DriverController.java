package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.input.DriverDTO;
import com.snoreware.mpk.model.output.OutDriverDTO;
import com.snoreware.mpk.repos.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "seniority_updater",
                procedureName = "seniority_updater")
})
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

    @GetMapping("/byStatus")
    public ResponseEntity<List<OutDriverDTO>> getFilteredDrivers(@RequestParam boolean onlyAvailable) {
        List<DriverEntity> drivers = repository.findAllByOrderByDriverIdAsc();
        List<OutDriverDTO> result = drivers.stream()
                .filter(driverEntity -> onlyAvailable
                    && driverEntity.getTramCourses().size() == 0
                    && driverEntity.getBusCourses().size() == 0)
                .map(driverEntity -> new OutDriverDTO(
                        driverEntity.getDriverId(),
                        driverEntity.getName(),
                        driverEntity.getSurname()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OutDriverDTO>> getAllDrivers() {
        List<DriverEntity> drivers = repository.findAllByOrderByDriverIdAsc();
        List<OutDriverDTO> result = new ArrayList<>();

        drivers.forEach(driverEntity -> result.add(new OutDriverDTO(
                driverEntity.getDriverId(),
                driverEntity.getName(),
                driverEntity.getSurname())));

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getOneDriver(@PathVariable UUID id) {
        DriverEntity driver = repository.findByDriverId(id);
        DriverDTO result = DriverDTO.dtoFromEntity(driver);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/resetSeniority/{inParam1}")
    public ResponseEntity resetSeniority(@PathVariable UUID inParam1) {
        repository.removeSeniority(inParam1);
        log.info("Set seniority to 0 for driver " + inParam1);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/experience")
    public ResponseEntity<List<OutDriverDTO>> getDriversMoreExperiencedThan(@RequestParam int seniority) {
        List<DriverEntity> drivers = repository.findAllBySeniorityGreaterThanEqual(seniority);

        List<OutDriverDTO> result = drivers.stream()
                .filter(driverEntity ->
                        driverEntity.getBusCourses().size() == 0
                                && driverEntity.getTramCourses().size() == 0)
                .map(driverEntity -> new OutDriverDTO(
                        driverEntity.getDriverId(),
                        driverEntity.getName(),
                        driverEntity.getSurname()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    void updateSeniority() {
        repository.updateSeniority();
        log.info("A seniority update occured (+1 to all assigned drivers)");
    }

}
