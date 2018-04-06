package de.doernbrack.example.kubernetes.zuul.configuration;

import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.kubernetes.KubernetesClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@ConfigurationProperties("spring.cloud.kubernetes.discovery")
public class KubernetesClientConfiguration {

    @Setter
    @Getter
    private Map<String, String> labels = new HashMap<>();

    @Bean
    public KubernetesClient kubernetesClient(KubernetesClientProperties clientProperties) {
        return new ContextAwareKubernetesClient(labels, clientProperties);
    }
}