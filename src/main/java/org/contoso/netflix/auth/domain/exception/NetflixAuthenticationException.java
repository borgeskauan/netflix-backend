package org.contoso.netflix.auth.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.UNAUTHORIZED)
public class NetflixAuthenticationException extends BusinessException {
    public NetflixAuthenticationException() {
        super("Invalid credentials");
    }
}
