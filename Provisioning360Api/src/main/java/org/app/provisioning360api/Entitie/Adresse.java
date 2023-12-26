package org.app.provisioning360api.Entitie;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@ToString
public class Adresse {
    @Id
    private String id ;
    private String number ;
    private String route;
    private String complement ;
    private String stage;
    private String zipPostal;
    private String ville;
    private String pays;


}
