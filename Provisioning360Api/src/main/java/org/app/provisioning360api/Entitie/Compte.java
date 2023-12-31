package org.app.provisioning360api.Entitie;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private double accountBalance;
    private String Role;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String  Password;
    private boolean Status;
    @Indexed(unique = true)
    private String userId;
    private String Tocken=null;
    private ArrayList<Course> coursesPurchased = new ArrayList<>();
}
