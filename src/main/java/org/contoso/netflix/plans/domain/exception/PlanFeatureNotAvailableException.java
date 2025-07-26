package org.contoso.netflix.plans.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class PlanFeatureNotAvailableException extends BusinessException {
    public PlanFeatureNotAvailableException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
