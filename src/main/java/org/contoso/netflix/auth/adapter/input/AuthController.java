package org.contoso.netflix.auth.adapter.input;

import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.UserResponse;
import org.contoso.netflix.auth.port.input.AuthUseCase;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/guest")
    public UserResponse createGuestUser() {
        return authUseCase.createGuestUser();
    }
}
