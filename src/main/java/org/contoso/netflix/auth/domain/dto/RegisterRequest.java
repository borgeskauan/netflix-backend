package org.contoso.netflix.auth.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;
}
