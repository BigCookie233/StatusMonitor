package io.github.bigcookie233.statusmonitor.controllers;

import io.github.bigcookie233.statusmonitor.entities.ServiceProvider;
import io.github.bigcookie233.statusmonitor.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatusApiController {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @GetMapping("api/list")
    public List<ServiceProvider> serviceProviderList() {
        return this.serviceProviderRepository.findAll();
    }
}
