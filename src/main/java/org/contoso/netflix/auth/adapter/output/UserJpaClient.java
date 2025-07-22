package org.contoso.netflix.auth.adapter.output;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaClient extends JpaRepository<NetflixUserDatabase, String> {
    Optional<NetflixUserDatabase> findByEmail(String email);
}
