package org.contoso.netflix.auth.domain.services;

import org.contoso.netflix.auth.domain.exception.NetflixRegistrationException;
import org.contoso.netflix.auth.port.input.UserRepositoryPort;
import org.contoso.netflix.auth.domain.PasswordUtil;
import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.UserResponse;
import org.contoso.netflix.auth.domain.entity.NetflixUser;
import org.contoso.netflix.auth.domain.exception.NetflixAuthenticationException;
import org.contoso.netflix.auth.port.input.AuthUseCase;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;

    public AuthService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);

        String passwordHash = PasswordUtil.hashPassword(registerRequest.getPassword());

        NetflixUser netflixUser = NetflixUser.builder()
                .id(registerRequest.getGuestId())
                .email(registerRequest.getEmail())
                .passwordHash(passwordHash)
                .isGuest(false)
                .build();

        return saveNetflixUser(netflixUser);
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        NetflixUser user = userRepositoryPort.findByEmail(loginRequest.getEmail())
                .orElseThrow(NetflixAuthenticationException::new);

        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new NetflixAuthenticationException();
        }

        return mapToUserResponse(user);
    }

    @Override
    public UserResponse createGuestUser() {
        NetflixUser netflixUser = NetflixUser.builder()
                .isGuest(false)
                .build();

        return saveNetflixUser(netflixUser);
    }

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        if (userRepositoryPort.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new NetflixRegistrationException("User already exists with this email");
        }

        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new NetflixRegistrationException("Email cannot be empty");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new NetflixRegistrationException("Passwords do not match");
        }
    }

    private UserResponse saveNetflixUser(NetflixUser netflixUser) {
        var savedUser = userRepositoryPort.save(netflixUser);

        return mapToUserResponse(savedUser);
    }

    private static UserResponse mapToUserResponse(NetflixUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isGuest(user.getIsGuest())
                .build();
    }
}
