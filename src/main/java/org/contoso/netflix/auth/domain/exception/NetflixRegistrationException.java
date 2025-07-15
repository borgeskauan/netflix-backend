package org.contoso.netflix.auth.domain.exception;

import org.contoso.netflix.config.exception.BusinessException;
import org.contoso.netflix.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class NetflixRegistrationException extends BusinessException {
    public NetflixRegistrationException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
