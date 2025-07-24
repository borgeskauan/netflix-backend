package org.contoso.netflix.auth.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class NetflixUserNotFoundException extends BusinessException {
    public NetflixUserNotFoundException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
