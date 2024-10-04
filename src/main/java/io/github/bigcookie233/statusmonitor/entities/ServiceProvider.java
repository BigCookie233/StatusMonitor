package io.github.bigcookie233.statusmonitor.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID uuid;
    private String serviceName;
    @JsonIgnore
    private String serviceSecret;;
    @JsonIgnore
    private String endpoint;
    private String status;

    public ServiceProvider(String serviceName, String serviceSecret, String endpoint) {
        this.serviceName = serviceName;
        this.serviceSecret = serviceSecret;
        this.endpoint = endpoint;
    }

    public ServiceProvider() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getServiceSecret() {
        return serviceSecret;
    }

    public void setServiceSecret(String serviceSecret) {
        this.serviceSecret = serviceSecret;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
