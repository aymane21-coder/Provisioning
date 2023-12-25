package org.app.provisioning360api.Entitie;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Enseignant {
    @Id
    private String Id ;
    private String nom;
    private String prenom;
    private String email;
    private String genre;
    private String dateNaissance;
    private String dateEntrer;
    private Boolean activated;


}