package org.contoso.netflix.plans.domain.services;

import lombok.Getter;
import org.contoso.netflix.plans.domain.entity.Plan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
@ConfigurationProperties(prefix = "plans")
public class PlansDefinitionProvider {

    private final List<Plan> definition;

    @ConstructorBinding
    public PlansDefinitionProvider(List<Plan> definition) {
        this.definition = definition;
    }
}
