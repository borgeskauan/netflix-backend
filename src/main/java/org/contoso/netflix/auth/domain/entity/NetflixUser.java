package org.contoso.netflix.auth.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NetflixUser {
    private UUID id;
    private String email;
    private String passwordHash;
}
