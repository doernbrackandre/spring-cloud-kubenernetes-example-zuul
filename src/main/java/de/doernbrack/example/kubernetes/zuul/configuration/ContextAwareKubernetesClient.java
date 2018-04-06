package de.doernbrack.example.kubernetes.zuul.configuration;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.internal.EndpointsOperationsImpl;
import io.fabric8.kubernetes.client.dsl.internal.ServiceOperationsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.kubernetes.KubernetesClientProperties;

import java.util.Map;

@RequiredArgsConstructor
public class ContextAwareKubernetesClient extends DefaultKubernetesClient {

    private final Map<String, String> labels;
    private final KubernetesClientProperties clientProperties;

    @Override
    public MixedOperation<Service, ServiceList, DoneableService, Resource<Service, DoneableService>> services() {
        ServiceOperationsImpl serviceOperations = new ServiceOperationsImpl(httpClient,
            getConfiguration(), getNamespace());
        serviceOperations.withLabels(labels);

        return serviceOperations;
    }

    @Override
    public MixedOperation<Endpoints, EndpointsList, DoneableEndpoints, Resource<Endpoints, DoneableEndpoints>> endpoints() {
        if (StringUtils.isEmpty(getNamespace())) {
            EndpointsOperationsImpl endpointsOperations = new EndpointsOperationsImpl(httpClient,
                getConfiguration(), getNamespace());
            endpointsOperations.withLabels(labels);

            return (MixedOperation<Endpoints, EndpointsList, DoneableEndpoints, Resource<Endpoints, DoneableEndpoints>>) endpointsOperations
                .inAnyNamespace();
        }

        return super.endpoints();
    }

    @Override
    public String getNamespace() {
        return clientProperties.getNamespace();
    }
}