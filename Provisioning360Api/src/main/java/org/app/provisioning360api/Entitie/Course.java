package org.app.provisioning360api.Entitie;
import lombok.*;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@EntityScan
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course {
    @Id
    private String id;
    private String typeCour;
    private String nomCour;
    private String CategorieCour;
    private double prixCour;

    private String descriptionCour;

    private Evaluation evaluation;

    private String idEnseignant;


}