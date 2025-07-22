package org.contoso.netflix.auth.adapter.input;

import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.PublicUserResponse;
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
    public PublicUserResponse login(@RequestBody LoginRequest request) {
        return authUseCase.login(request);
    }

    @PostMapping("/register")
    public PublicUserResponse register(@RequestBody RegisterRequest request) {
        return authUseCase.register(request);
    }

    @GetMapping("/guest")
    public PublicUserResponse createGuestUser() {
        return authUseCase.createGuestUser();
    }
}
