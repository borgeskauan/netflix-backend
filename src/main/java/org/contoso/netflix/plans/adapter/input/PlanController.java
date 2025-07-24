package org.contoso.netflix.plans.adapter.input;

import org.contoso.netflix.plans.domain.dto.ChangePlanRequest;
import org.contoso.netflix.plans.domain.entity.Plan;
import org.contoso.netflix.plans.port.input.PlanUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans/{userId}")
public class PlanController {

    private final PlanUseCase planUseCase;

    public PlanController(PlanUseCase planUseCase) {
        this.planUseCase = planUseCase;
    }

    @GetMapping("/details")
    public Plan getUserPlanDetails(@PathVariable String userId) {
        return planUseCase.getUserPlanDetails(userId);
    }

    @PostMapping("/change")
    public Plan changeUserPlan(@PathVariable String userId, @RequestBody ChangePlanRequest changePlanRequest) {
        return planUseCase.changeUserPlan(userId, changePlanRequest);
    }

    @GetMapping("/available")
    public List<Plan> listAvailablePlans(@PathVariable String userId) {
        return planUseCase.listAvailablePlans(userId);
    }
}
