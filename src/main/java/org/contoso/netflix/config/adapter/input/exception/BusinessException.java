package org.contoso.netflix.config.adapter.input.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private String technicalMessage;
    private final String friendlyMessage;

    public BusinessException(String friendlyMessage) {
        super(friendlyMessage);

        this.friendlyMessage = friendlyMessage;
    }

    public BusinessException(String friendlyMessage, String technicalMessage) {
        super(friendlyMessage);

        this.friendlyMessage = friendlyMessage;
        this.technicalMessage = technicalMessage;
    }

    public BusinessException(String friendlyMessage, Throwable cause) {
        super(friendlyMessage, cause);

        this.friendlyMessage = friendlyMessage;
        this.technicalMessage = cause != null ? cause.getMessage() : null;
    }
}
