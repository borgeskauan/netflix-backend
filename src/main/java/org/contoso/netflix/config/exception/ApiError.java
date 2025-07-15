package org.contoso.netflix.config.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private Integer status;
    private String error;

    private String technicalMessage;
    private String friendlyMessage;

    private Map<String, String> validationErrors;

    public static ApiError fromHttpStatus(HttpStatus status) {
        return ApiError.builder()
                .status(status.value())
                .error(status.getReasonPhrase())
                .build();
    }
}