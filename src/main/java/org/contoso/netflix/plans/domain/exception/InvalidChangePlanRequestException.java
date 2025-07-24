package org.contoso.netflix.plans.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidChangePlanRequestException extends BusinessException {
    public InvalidChangePlanRequestException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
