package org.app.provisioning360api.Entitie;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Compte {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String Role;
    private String  Password;
    private boolean Status;
    @Indexed(unique = true)
    private String userId;
}
