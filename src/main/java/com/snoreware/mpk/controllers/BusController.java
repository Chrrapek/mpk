package com.snoreware.mpk.controllers;

import com.snoreware.mpk.MpkApplication;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.repos.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BusController {
    @Autowired
    BusRepository repository;
    private static final Logger log = LoggerFactory.getLogger(MpkApplication.class);

    @PostMapping("add")
    public void demo() {
        repository.save(new BusEntity(123L, true, true));
        repository.save(new BusEntity(167L, true, false));
        repository.save(new BusEntity(199L, false, true));
        repository.save(new BusEntity(100L, true, true));
        repository.save(new BusEntity(120L, false, false));

        repository.findByArticulated(true).forEach(articulated -> log.info(articulated.toString()));
    }
}
