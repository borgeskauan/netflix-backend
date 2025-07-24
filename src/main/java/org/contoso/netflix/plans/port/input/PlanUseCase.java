package org.contoso.netflix.plans.port.input;

import org.contoso.netflix.plans.domain.dto.ChangePlanRequest;
import org.contoso.netflix.plans.domain.entity.Plan;

import java.util.List;

public interface PlanUseCase {
    Plan getUserPlanDetails(String userId);

    Plan changeUserPlan(String userId, ChangePlanRequest changePlanRequest);

    List<Plan> listAvailablePlans(String userId);
}
