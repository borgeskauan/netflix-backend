package org.contoso.netflix.auth.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidRegistrationRequestException extends BusinessException {
    public InvalidRegistrationRequestException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
