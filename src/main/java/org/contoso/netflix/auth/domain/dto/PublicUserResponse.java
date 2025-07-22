package org.contoso.netflix.auth.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PublicUserResponse {
    private UUID id;

    private String name;
    private String email;

    private Boolean isGuest;
}
