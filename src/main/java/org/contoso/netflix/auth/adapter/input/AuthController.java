package org.contoso.netflix.auth.adapter.input;

import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.UserResponse;
import org.contoso.netflix.auth.port.input.AuthUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody LoginRequest request) {
        return authUseCase.login(request);
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return authUseCase.register(request);
    }
}
