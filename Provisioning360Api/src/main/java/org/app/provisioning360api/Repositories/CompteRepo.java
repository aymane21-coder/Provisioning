package org.app.provisioning360api.Repositories;

import org.app.provisioning360api.Entitie.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CompteRepo extends MongoRepository<Compte,String> {
  Compte findCompteByEmail(String email);
    List<Compte> findByRole(String role);
}
