package org.contoso.netflix.auth.port.input;

import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.UserResponse;

public interface AuthUseCase {
    UserResponse register(RegisterRequest registerRequest);

    UserResponse login(LoginRequest loginRequest);
}
