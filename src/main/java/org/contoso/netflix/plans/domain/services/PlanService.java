package org.contoso.netflix.plans.domain.services;

import org.contoso.netflix.auth.domain.exception.NetflixUserNotFoundException;
import org.contoso.netflix.auth.port.output.UserRepositoryPort;
import org.contoso.netflix.plans.domain.dto.ChangePlanRequest;
import org.contoso.netflix.plans.domain.entity.Plan;
import org.contoso.netflix.plans.domain.entity.Plans;
import org.contoso.netflix.plans.domain.exception.InvalidChangePlanRequestException;
import org.contoso.netflix.plans.port.input.PlanUseCase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanService implements PlanUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PlansDefinitionProvider plansDefinitionProvider;

    public PlanService(UserRepositoryPort userRepositoryPort, PlansDefinitionProvider plansDefinitionProvider) {
        this.userRepositoryPort = userRepositoryPort;
        this.plansDefinitionProvider = plansDefinitionProvider;
    }

    @Override
    public Plan getUserPlanDetails(String userId) {
        String planId = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NetflixUserNotFoundException("User not found"))
                .getPlanId();

        if (planId == null || planId.isEmpty()) {
            return defaultPlan(userId);
        }

        return findPlanByIdOrThrow(userId, planId);
    }

    @Override
    public Plan changeUserPlan(String userId, ChangePlanRequest changePlanRequest) {
        var user = userRepositoryPort.findById(userId)
                .orElseThrow(() -> new NetflixUserNotFoundException("User not found"));

        if (user.getIsGuest()) {
            throw new InvalidChangePlanRequestException("Guest users cannot change plans");
        }

        var newPlan = findPlanById(userId, changePlanRequest.getNewPlanId())
                .orElseThrow(() -> new InvalidChangePlanRequestException("Invalid plan ID"));

        userRepositoryPort.update(user.withPlanId(newPlan.getId()));

        return newPlan;
    }

    @Override
    public List<Plan> listAvailablePlans(String userId) {
        var extractedPlansDefinition = plansDefinitionProvider.getDefinition();
        if (extractedPlansDefinition == null || extractedPlansDefinition.isEmpty()) {
            throw new IllegalStateException("No plans available");
        }

        return extractedPlansDefinition;
    }

    private Plan defaultPlan(String userId) {
        return findPlanByIdOrThrow(userId, Plans.FREE.getName());
    }

    private Optional<Plan> findPlanById(String userId, String planId) {
        return listAvailablePlans(userId)
                .stream()
                .filter(plan -> plan.getId().equalsIgnoreCase(planId))
                .findFirst();
    }

    private Plan findPlanByIdOrThrow(String userId, String planId) {
        return findPlanById(userId, planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
    }
}
