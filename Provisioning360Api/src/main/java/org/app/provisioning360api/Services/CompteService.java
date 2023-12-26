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


    public Compte updateCompte(String id, Compte updatedCompte)
    {
        Compte existingCompte = Repo.findById(id).orElse(null);
        existingCompte.getRole();
        if (existingCompte != null) {
            // Update fields of the existingCompte with updatedCompte data
            existingCompte.setEmail(updatedCompte.getEmail());
            existingCompte.setRole(updatedCompte.getRole());
            String encryptedPassword = bCryptPasswordEncoder.encode(existingCompte.getPassword());
            existingCompte.setPassword(encryptedPassword);


            return Repo.save(existingCompte);
        } else {

            return null;
        }
    }
    public Compte getCompteById(String id)
    {
        return Repo.findById(id).orElse(null);
    }







}
