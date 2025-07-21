package org.contoso.netflix.auth.domain.services;

import org.contoso.netflix.auth.domain.exception.InvalidRegistrationRequestException;
import org.contoso.netflix.auth.port.input.UserRepositoryPort;
import org.contoso.netflix.auth.domain.PasswordUtil;
import org.contoso.netflix.auth.domain.dto.LoginRequest;
import org.contoso.netflix.auth.domain.dto.RegisterRequest;
import org.contoso.netflix.auth.domain.dto.UserResponse;
import org.contoso.netflix.auth.domain.entity.NetflixUser;
import org.contoso.netflix.auth.domain.exception.NetflixAuthenticationException;
import org.contoso.netflix.auth.port.input.AuthUseCase;
import org.contoso.netflix.playlist.ports.input.PlaylistUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PlaylistUseCase playlistUseCase;

    public AuthService(UserRepositoryPort userRepositoryPort, PlaylistUseCase playlistUseCase) {
        this.userRepositoryPort = userRepositoryPort;
        this.playlistUseCase = playlistUseCase;
    }

    @Override
    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        validateRegisterRequest(registerRequest);

        String passwordHash = PasswordUtil.hashPassword(registerRequest.getPassword());

        NetflixUser netflixUser = NetflixUser.builder()
                .id(registerRequest.getGuestId())
                .name(registerRequest.getName())
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
    @Transactional
    public UserResponse createGuestUser() {
        NetflixUser netflixUser = NetflixUser.builder()
                .name("Guest User")
                .isGuest(true)
                .build();

        return saveNetflixUser(netflixUser);
    }

    private void validateRegisterRequest(RegisterRequest registerRequest) {
        if (userRepositoryPort.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new InvalidRegistrationRequestException("User already exists with this email");
        }

        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new InvalidRegistrationRequestException("Email cannot be empty");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new InvalidRegistrationRequestException("Passwords do not match");
        }
    }

    private UserResponse saveNetflixUser(NetflixUser netflixUser) {
        var savedUser = userRepositoryPort.save(netflixUser);

        boolean isNewUser = netflixUser.getId() == null || netflixUser.getId().toString().isEmpty();
        postProcessCreatedUser(savedUser, isNewUser);

        return mapToUserResponse(savedUser);
    }

    private void postProcessCreatedUser(NetflixUser savedUser, boolean isNewUser) {
        if (isNewUser) {
            playlistUseCase.createSystemPlaylistsForUser(savedUser.getId().toString());
        }
    }

    private static UserResponse mapToUserResponse(NetflixUser user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isGuest(user.getIsGuest())
                .build();
    }
}
