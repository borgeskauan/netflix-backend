package org.contoso.netflix.auth.port.input;

import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.PublicUserResponse;
import org.contoso.netflix.auth.domain.dto.RegisterRequest;

public interface AuthUseCase {
    PublicUserResponse register(RegisterRequest registerRequest);

    PublicUserResponse login(LoginRequest loginRequest);

    PublicUserResponse createGuestUser();
}
