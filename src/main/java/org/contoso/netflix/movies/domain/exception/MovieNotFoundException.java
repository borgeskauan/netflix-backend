package org.contoso.netflix.movies.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class MovieNotFoundException extends BusinessException {
    public MovieNotFoundException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
