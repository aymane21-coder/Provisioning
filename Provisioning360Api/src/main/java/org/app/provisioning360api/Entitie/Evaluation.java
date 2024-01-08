package org.app.provisioning360api.Entitie;


import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@EntityScan
@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Evaluation{
    @Id
    private String id;
    private String note;
    private String dateEvaluation;


}