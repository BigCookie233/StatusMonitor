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
            if(serviceProvider.getEndpoint() != null) {
                try {
                    ResponseEntity<String> response = this.restTemplate.getForEntity(serviceProvider.getEndpoint(), String.class);
                    HttpStatusCode statusCode = response.getStatusCode();
                    if (statusCode.is2xxSuccessful() && !"Operational".equals(serviceProvider.getStatus())) {
                        serviceProvider.setStatus("Operational");
                        this.serviceProviderRepository.save(serviceProvider);
                    } else {
                        throw new RestClientException("Unexpected status code");
                    }
                } catch (RestClientException e) {
                    if (!"Down".equals(serviceProvider.getStatus())) {
                        serviceProvider.setStatus("Down");
                        this.serviceProviderRepository.save(serviceProvider);
                    }
                }
            }
        }
    }
}
