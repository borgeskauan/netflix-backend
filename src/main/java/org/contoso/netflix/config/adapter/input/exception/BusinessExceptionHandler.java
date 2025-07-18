package org.contoso.netflix.config.adapter.input.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        var httpStatus = ex.getClass().getAnnotation(HttpStatusMapping.class);
        if (httpStatus == null) {
            log.error("BusinessException without HttpStatusMapping: {}", ex.getMessage(), ex);
            return handleGenericException(ex);
        }

        var apiErro = ApiError.fromHttpStatus(httpStatus.value())
                .withFriendlyMessage(ex.getFriendlyMessage())
                .withTechnicalMessage(ex.getTechnicalMessage());

        return buildResponseEntity(apiErro, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        var apiError = ApiError.fromHttpStatus(BAD_REQUEST)
                .withFriendlyMessage("A validação dos campos falhou. Verifique os dados inseridos e tente novamente.")
                .withValidationErrors(errors);

        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingParams(MissingServletRequestParameterException ex) {
        var apiError = ApiError.fromHttpStatus(BAD_REQUEST)
                .withFriendlyMessage("O parâmetro '" + ex.getParameterName() + "' é obrigatório.")
                .withTechnicalMessage(ex.getMessage());

        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        var apiError = ApiError.fromHttpStatus(INTERNAL_SERVER_ERROR)
                .withFriendlyMessage("Um erro inesperado aconteceu. Por favor, tente novamente mais tarde.")
                .withTechnicalMessage(ex.getLocalizedMessage());

        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<ApiError> handleAuthenticationError(FeignException.Forbidden ex) {
        return buildResponseEntityForAuthenticationError(FORBIDDEN, ex);
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<ApiError> handleAuthenticationError(FeignException.Unauthorized ex) {
        return buildResponseEntityForAuthenticationError(UNAUTHORIZED, ex);
    }

    private ResponseEntity<ApiError> buildResponseEntityForAuthenticationError(HttpStatus httpStatus, FeignException ex) {
        var apiError = ApiError.fromHttpStatus(httpStatus)
                .withFriendlyMessage("Um erro de autenticação aconteceu. Por favor, verifique suas credenciais e tente novamente.")
                .withTechnicalMessage(ex.getLocalizedMessage());

        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError, Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
        log.info("Returning error response: {}", apiError);

        return ResponseEntity
                .status(HttpStatusCode.valueOf(apiError.getStatus()))
                .body(apiError);
    }
}
