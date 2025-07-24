package org.contoso.netflix.auth.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.UUID;

@Data
@With
@Builder
public class NetflixUser {
    private UUID id;

    private String name;
    private String email;
    private String passwordHash;

    private String planId;

    private Boolean isGuest;
}
