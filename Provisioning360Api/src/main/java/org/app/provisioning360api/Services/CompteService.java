package org.app.provisioning360api.Services;


import org.app.provisioning360api.Entitie.Compte;
import org.app.provisioning360api.Repositories.CompteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompteService {
    @Autowired
    private CompteRepo Repo ;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public Compte save(Compte compte){
        compte.setStatus(false);
        Compte compte1 = Repo.findCompteByEmail(compte.getEmail());
        if(compte1!=null) throw new RuntimeException("User already exists");
        compte.setPassword(bCryptPasswordEncoder.encode(compte.getPassword()));

        return this.Repo.save(compte);
    }
}