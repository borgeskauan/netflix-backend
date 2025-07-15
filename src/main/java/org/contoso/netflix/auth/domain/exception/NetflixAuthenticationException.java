package org.contoso.netflix.auth.domain.exception;

import org.contoso.netflix.config.exception.BusinessException;
import org.contoso.netflix.config.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.UNAUTHORIZED)
public class NetflixAuthenticationException extends BusinessException {
    public NetflixAuthenticationException() {
        super("Invalid credentials");
    }
}
