package org.app.provisioning360api.Controlller;

import com.fasterxml.jackson.databind.JsonNode;
import org.app.provisioning360api.Entitie.Compte;
import org.app.provisioning360api.Entitie.Enseignant;
import org.app.provisioning360api.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
            ResponseEntity<Enseignant> result = restTemplate.postForEntity(enseignantUrl, body, Enseignant.class);
            Enseignant json = result.getBody();
            UserID=json.getId();
            return new ResponseEntity<>(result.getStatusCodeValue() == 200 ? "Enseignant created successfully user Id : "+UserID : "Enseignant Not created successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
