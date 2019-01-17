package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.model.input.VehicleDTO;
import com.snoreware.mpk.model.output.BusDTO;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @DeleteMapping("/remove/{id}")
    public void removeBus(@PathVariable Long id) {
        busRepository.deleteById(id);
        log.info(String.format("Removed bus of id %d", id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity updateBus(@RequestBody VehicleDTO vehicleDTO, @PathVariable Long id) {
        BusEntity busToUpdate = busRepository.findByVehicleNumber(id);
        if (vehicleDTO.getLowFloor() != null)
            busToUpdate.setLowFloor(vehicleDTO.getLowFloor());
        if (vehicleDTO.getArticulated() != null)
            busToUpdate.setArticulated(vehicleDTO.getArticulated());

        busRepository.save(busToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Long>> getAllBuses() {
        List<BusEntity> buses = busRepository.findAllByOrderByVehicleNumberAsc();

        List<Long> response = new ArrayList<>();
        for (BusEntity busEntity : buses) {
            response.add(busEntity.getVehicleNumber());
        }

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<Long>> getAllBuses(@RequestParam boolean notBroken) {
        List<BusEntity> buses = busRepository.findAllByOrderByVehicleNumberAsc();

        List<Long> response = new ArrayList<>();
        for (BusEntity busEntity : buses) {
            if (notBroken && !busEntity.getVehicleBreakdown())
                response.add(busEntity.getVehicleNumber());
        }

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/failure/{id}")
    public ResponseEntity changeBreakdownStatus(@PathVariable Long id) {
        BusEntity busToChange = busRepository.findByVehicleNumber(id);
        busToChange.setVehicleBreakdown(!busToChange.getVehicleBreakdown());

        busRepository.save(busToChange);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BusDTO> getOneBusInfo(@PathVariable Long id) {
        BusEntity bus = busRepository.findByVehicleNumber(id);
        BusDTO result = new BusDTO(
                bus.getVehicleNumber(),
                bus.getArticulated(),
                bus.getLowFloor(),
                bus.getUUIDList(),
                bus.getVehicleBreakdown());

        return ResponseEntity.ok().body(result);
    }
}
