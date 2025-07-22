package org.contoso.netflix.reviews.domain.exceptions;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidReviewRequestException extends BusinessException {
    public InvalidReviewRequestException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
