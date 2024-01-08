package org.app.provisioning360api.Entitie;

import lombok.*;
import org.app.provisioning360api.Entitie.Adresse;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Document(collection ="student")//collections in database
@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private Date date_entrer;
    private String email;
    private Date date_de_naissance;
    private Integer statut;
    private String genre;
    private List<String> courslist ;
    @DBRef
    private Adresse adresse;
    @DBRef
    private Collection<Calendrier> calendriers;

    public void setId(String id) {
        id = id;}

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public void setDate_entrer(Date date_entrer) {
        this.date_entrer = date_entrer;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setListe_de_cours(ArrayList<String> liste_de_cours) {
        this.courslist = liste_de_cours;
    }

    public void setNom(String nom) {
        this.nom = nom;}

    public void setPrenom(String prenom) {
        this.prenom = prenom;}

    public void setStatut(Integer statut) {
        this.statut = statut;}

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;}

}
