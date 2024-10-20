package io.github.bigcookie233.statusmonitor.controllers;

import io.github.bigcookie233.statusmonitor.Utils;
import io.github.bigcookie233.statusmonitor.entities.ServiceProvider;
import io.github.bigcookie233.statusmonitor.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class StatusApiController {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @GetMapping("api/list")
    public List<ServiceProvider> serviceProviderList() {
        return this.serviceProviderRepository.findAll();
    }

    @GetMapping("api/heartbeat")
    public ResponseEntity<Void> serviceProviderHeartbeat(@RequestParam String secret,
                                                         @RequestParam(required = false) String status) {
        ServiceProvider serviceProvider = this.serviceProviderRepository.findByServiceSecret(secret);
        if (serviceProvider != null) {
            serviceProvider.setLastHeartbeat(LocalDateTime.now());
            Utils.update(serviceProvider, Objects.requireNonNullElse(status, "operational"), this.serviceProviderRepository);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
