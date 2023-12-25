package org.app.provisioning360api.Repositories;

import org.app.provisioning360api.Entitie.Compte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CompteRepo extends MongoRepository<Compte,String> {
public  Compte findCompteByEmail(String email);
}
