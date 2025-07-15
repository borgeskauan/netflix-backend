package org.contoso.netflix.auth.adapter.output;

import org.contoso.netflix.auth.domain.entity.NetflixUser;
import org.contoso.netflix.auth.port.input.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaClient userJpaClient;

    public UserJpaAdapter(UserJpaClient userJpaClient) {
        this.userJpaClient = userJpaClient;
    }

    @Override
    public NetflixUser save(NetflixUser netflixUser) {
        NetflixUserDatabase userEntity = mapToUserEntity(netflixUser)
                .withId(UUID.randomUUID().toString());

        NetflixUserDatabase savedEntity = userJpaClient.save(userEntity);
        return mapToNetflixUser(savedEntity);
    }

    @Override
    public Optional<NetflixUser> findByEmail(String email) {
        return userJpaClient.findByEmail(email)
                .map(this::mapToNetflixUser);
    }

    private NetflixUser mapToNetflixUser(NetflixUserDatabase userEntity) {
        return NetflixUser.builder()
                .id(UUID.fromString(userEntity.getId()))
                .email(userEntity.getEmail())
                .passwordHash(userEntity.getPasswordHash())
                .build();
    }

    private NetflixUserDatabase mapToUserEntity(NetflixUser netflixUser) {
        return NetflixUserDatabase.builder()
                .id(String.valueOf(netflixUser.getId()))
                .email(netflixUser.getEmail())
                .passwordHash(netflixUser.getPasswordHash())
                .build();
    }
}
