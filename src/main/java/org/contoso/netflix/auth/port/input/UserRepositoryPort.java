package org.contoso.netflix.auth.port.input;

import org.contoso.netflix.auth.domain.entity.NetflixUser;

import java.util.Optional;

public interface UserRepositoryPort {
    NetflixUser save(NetflixUser netflixUser);

    Optional<NetflixUser> findByEmail(String email);
}
