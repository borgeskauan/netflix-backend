package org.contoso.netflix.auth.adapter.output;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Entity
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class NetflixUserDatabase {

    @Id
    private String id;

    private String email;
    private String passwordHash;
}
