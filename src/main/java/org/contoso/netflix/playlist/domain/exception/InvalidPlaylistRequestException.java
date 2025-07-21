package org.contoso.netflix.playlist.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;
import org.contoso.netflix.config.adapter.input.exception.HttpStatusMapping;
import org.springframework.http.HttpStatus;

@HttpStatusMapping(HttpStatus.BAD_REQUEST)
public class InvalidPlaylistRequestException extends BusinessException {
    public InvalidPlaylistRequestException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
