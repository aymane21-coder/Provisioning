package org.app.provisioning360api.Controlller;

import com.fasterxml.jackson.databind.JsonNode;
import org.app.provisioning360api.Entitie.Compte;
import org.app.provisioning360api.Entitie.Enseignant;
import org.app.provisioning360api.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CompteController {
    String UserID;
    @Value("${enseignant.url}")
    private String enseignantUrl;
    @Autowired
    private CompteService service;

    @PostMapping("/register")
    public Compte save(@RequestBody Compte compte) {
        compte.setUserId(UserID);
        return service.save(compte);
    }

    @PostMapping("/AddEnseignant")
    public ResponseEntity<?> createEnsignant(@RequestBody Enseignant body) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Enseignant> result = restTemplate.postForEntity(enseignantUrl+"/add", body, Enseignant.class);
            Enseignant json = result.getBody();
            UserID=json.getId();
            return new ResponseEntity<>(result.getStatusCodeValue() == 200 ? "Enseignant created successfully user Id : "+UserID : "Enseignant Not created successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateCompte/{id}")
    public ResponseEntity<Compte> updateCompte(@PathVariable String id, @RequestBody Compte updatedCompte) {
        // Implement logic to update Compte by ID with the provided updatedCompte
        Compte updated = service.updateCompte(id, updatedCompte);


        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/Updatedonne/{id}")
    public ResponseEntity<?> updateEnseignant(@PathVariable("id") String enseignantId, @RequestBody Enseignant updatedEnseignant) {
        try {
            String updateUrl = enseignantUrl + "/update/"+enseignantId; // Assuming the update endpoint follows this structure

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(updateUrl, updatedEnseignant);

            return new ResponseEntity<>("Enseignant updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error updating Enseignant, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompteById(@PathVariable("id") String id) {
        Compte compte = service.getCompteById(id);
        if (compte != null) {
            return new ResponseEntity<>(compte, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Compte not found for ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
