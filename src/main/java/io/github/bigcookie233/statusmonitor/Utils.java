package io.github.bigcookie233.statusmonitor;

import io.github.bigcookie233.statusmonitor.entities.ServiceProvider;
import io.github.bigcookie233.statusmonitor.repositories.ServiceProviderRepository;

import java.time.LocalDateTime;

public class Utils {
    public static void update(ServiceProvider serviceProvider, String status, ServiceProviderRepository serviceProviderRepository) {
        if (!status.equals(serviceProvider.getStatus())) {
            serviceProvider.setStatus(status);
            serviceProvider.setLastUpdate(LocalDateTime.now());
            serviceProviderRepository.save(serviceProvider);
        }
    }
}
