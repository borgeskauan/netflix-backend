package org.contoso.netflix.auth.adapter.output;

import org.contoso.netflix.auth.domain.entity.NetflixUser;
import org.contoso.netflix.auth.port.output.UserRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaClient userJpaClient;
    // TODO: Usar mapper com o MapStruct para converter entre entidades e DTOs, já que possuem nomes similares.

    public UserJpaAdapter(UserJpaClient userJpaClient) {
        this.userJpaClient = userJpaClient;
    }

    @Override
    public NetflixUser save(NetflixUser netflixUser) {
        NetflixUserDatabase userEntity = mapToUserEntity(netflixUser);
        if (userEntity.getId() == null || userEntity.getId().isEmpty()) {
            userEntity.setId(UUID.randomUUID().toString());
        }

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
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .passwordHash(userEntity.getPasswordHash())
                .isGuest(userEntity.getIsGuest())
                .build();
    }

    private NetflixUserDatabase mapToUserEntity(NetflixUser netflixUser) {
        var id = netflixUser.getId() != null ? netflixUser.getId().toString() : null;

        return NetflixUserDatabase.builder()
                .id(id)
                .name(netflixUser.getName())
                .email(netflixUser.getEmail())
                .passwordHash(netflixUser.getPasswordHash())
                .isGuest(netflixUser.getIsGuest())
                .build();
    }
}
