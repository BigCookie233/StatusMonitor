package io.github.bigcookie233.statusmonitor;

import io.github.bigcookie233.statusmonitor.entities.ServiceProvider;
import io.github.bigcookie233.statusmonitor.repositories.ServiceProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ScheduledTasks {
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    private RestTemplate restTemplate;

    public ScheduledTasks() {
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(cron = "0 */10 * * * ?")
    public void updateStatus() {
        for (ServiceProvider serviceProvider : this.serviceProviderRepository.findAll()) {
            if (serviceProvider.getEndpoint() != null) {
                try {
                    ResponseEntity<String> response = this.restTemplate.getForEntity(serviceProvider.getEndpoint(), String.class);
                    HttpStatusCode statusCode = response.getStatusCode();
                    if (statusCode.is2xxSuccessful()) {
                        Utils.update(serviceProvider, "operational", this.serviceProviderRepository);
                    } else {
                        throw new RestClientException("Unexpected status code");
                    }
                } catch (RestClientException exception) {
                    Utils.update(serviceProvider, "outage", this.serviceProviderRepository);
                }
            } else {
                if (serviceProvider.getLastHeartbeat() != null) {
                    Duration duration = Duration.between(serviceProvider.getLastHeartbeat(), LocalDateTime.now());
                    if (duration.toMinutes() >= 12) {
                        Utils.update(serviceProvider, "outage", this.serviceProviderRepository);
                    }
                }
            }
        }
    }


}
