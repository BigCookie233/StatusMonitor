package io.github.bigcookie233.statusmonitor.repositories;

import io.github.bigcookie233.statusmonitor.entities.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID> {
    ServiceProvider findByServiceSecret(String serviceSecret);
}
