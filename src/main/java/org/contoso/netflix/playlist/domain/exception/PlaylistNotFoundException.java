package org.contoso.netflix.playlist.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.NOT_FOUND)
public class PlaylistNotFoundException extends BusinessException {
    public PlaylistNotFoundException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
