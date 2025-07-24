package org.contoso.netflix.plans.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Plan {
    private String id;
    private String name;
    private BigDecimal price;
    private PlanFeatures features;
}
