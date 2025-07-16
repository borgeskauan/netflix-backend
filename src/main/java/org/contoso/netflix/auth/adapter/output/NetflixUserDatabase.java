package org.contoso.netflix.auth.adapter.output;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NetflixUserDatabase {

    @Id
    private String id;

    private String name;
    private String email;
    private String passwordHash;

    private Boolean isGuest;
}
