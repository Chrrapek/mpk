package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BusController {
    @Autowired
    BusRepository repository;
    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @GetMapping("add")
    public void demo() {
        repository.save(new BusEntity(true));
        repository.save(new BusEntity(false));
        repository.save(new BusEntity(false));
        repository.save(new BusEntity(true));
        repository.save(new BusEntity(false));

        repository.findByArticulated(true).forEach(bauer -> log.info(bauer.toString()));
    }
}
