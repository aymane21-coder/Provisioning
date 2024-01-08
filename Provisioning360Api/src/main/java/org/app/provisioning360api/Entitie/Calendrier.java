package org.app.provisioning360api.Entitie;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection ="Calendrier")//collections in database
public class Calendrier {
    @Id
    private String id;
    private String Event;
    private Date date_acheter;
    private Date date_fin;

    public void setId(String id) {
        this.id = id;
    }



}
