package org.contoso.netflix.auth.port.output;

import org.contoso.netflix.auth.domain.entity.NetflixUser;

import java.util.Optional;

public interface UserRepositoryPort {
    NetflixUser save(NetflixUser netflixUser); // TODO: It might be better if we had a separate save and update method, for readability.

    Optional<NetflixUser> findByEmail(String email);
}
