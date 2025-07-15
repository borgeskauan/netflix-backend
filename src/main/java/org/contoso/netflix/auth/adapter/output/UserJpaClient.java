package org.contoso.netflix.auth.adapter.output;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaClient extends JpaRepository<NetflixUserDatabase, UUID> {
    Optional<NetflixUserDatabase> findByEmail(String email);
}
