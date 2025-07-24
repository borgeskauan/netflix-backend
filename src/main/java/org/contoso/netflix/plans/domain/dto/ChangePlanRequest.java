package org.contoso.netflix.plans.domain.dto;

import lombok.Data;

@Data
public class ChangePlanRequest {
    private String newPlanId;
}
