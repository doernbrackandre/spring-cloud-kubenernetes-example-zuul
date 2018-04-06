package de.idealo.requestrouter.kube.configuration;

import io.fabric8.kubernetes.client.KubernetesClient;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.kubernetes.KubernetesClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties("spring.cloud.kubernetes.discovery")
public class KubernetesClientAutoConfiguration {

    @Setter
    @Getter
    private Map<String, String> labels = new HashMap<>();

    @Bean
    public KubernetesClient kubernetesClient(KubernetesClientProperties clientProperties) {
        return new ContextAwareKubernetesClient(labels, clientProperties);
    }
}