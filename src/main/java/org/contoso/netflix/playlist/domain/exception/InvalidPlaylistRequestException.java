package org.contoso.netflix.playlist.domain.exception;

import org.contoso.netflix.config.adapter.input.exception.BusinessException;

public class InvalidPlaylistRequestException extends BusinessException {
    public InvalidPlaylistRequestException(String friendlyMessage) {
        super(friendlyMessage);
    }
}
