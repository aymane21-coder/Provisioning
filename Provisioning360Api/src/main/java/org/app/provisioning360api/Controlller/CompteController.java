package org.app.provisioning360api.Controlller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import org.app.provisioning360api.Entitie.Compte;
import org.app.provisioning360api.Entitie.Course;
import org.app.provisioning360api.Entitie.Enseignant;
import org.app.provisioning360api.Entitie.Student;
import org.app.provisioning360api.Repositories.CompteRepo;
import org.app.provisioning360api.Security.SecurityParams;
import org.app.provisioning360api.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
//@RequestMapping("/compte")
public class CompteController {
    String UserID;
    @Value("${enseignant.url}")
    private String enseignantUrl;
    @Value("${Cours.url}")
    private String Coursurl;
    @Value("${Student.url}")
    private String studenturl;
    @Autowired
    private CompteService service;


    // Get all Comptes
    @GetMapping("/comptes")
    public ResponseEntity<?> getAllComptes() {
        try {
            List<Compte> comptes = service.findAll(); // Assuming you have a service method to get all comptes

            if (!comptes.isEmpty()) {
                return new ResponseEntity<>(comptes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Comptes found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching all Comptes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public Compte save(@RequestBody Compte compte) {
        compte.setUserId(UserID);
        compte.setCoursesPurchased(new ArrayList<>());
        return service.save(compte);
    }

    @PostMapping("/AddEnseignant")
    public ResponseEntity<?> createEnsignant(@RequestBody Enseignant body) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Enseignant> result = restTemplate.postForEntity(enseignantUrl + "/add", body, Enseignant.class);
            Enseignant json = result.getBody();
            UserID = json.getId();
            return new ResponseEntity<>(result.getStatusCodeValue() == 200 ? "Enseignant created successfully user Id : " + UserID : "Enseignant Not created successfully", HttpStatus.OK);
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

            String updateUrl = enseignantUrl + "/update/" + enseignantId; // Assuming the update endpoint follows this structure

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

    @GetMapping("/enseignant/{id}")
    public ResponseEntity<?> getEnseignantById(@PathVariable("id") String enseignantId) {
        try {
            String getByIdUrl = enseignantUrl + "/get/" + enseignantId; // Endpoint to get a single Enseignant by ID

            RestTemplate restTemplate = new RestTemplate();
            Enseignant enseignant = restTemplate.getForObject(getByIdUrl, Enseignant.class);

            if (enseignant != null) {
                return new ResponseEntity<>(enseignant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Enseignant not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching Enseignant by ID", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all Enseignants
    @GetMapping("/enseignants")
    public ResponseEntity<?> getAllEnseignants() {
        try {
            String getAllUrl = enseignantUrl + "/getAll"; // Endpoint to get all Enseignants

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Enseignant[]> response = restTemplate.getForEntity(getAllUrl, Enseignant[].class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Enseignant[] enseignants = response.getBody();
                return new ResponseEntity<>(Arrays.asList(enseignants), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Enseignants found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error fetching all Enseignants", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //COURS
    @PostMapping("/ajoutercours")
    public ResponseEntity<?> ajouterCours(@RequestBody Course cours, HttpServletRequest request) {
        try {
            // Récupérer le token JWT de l'en-tête Authorization
            String token = request.getHeader("Authorization");

            // Vérifier si le token est valide et récupérer les informations utilisateur
            if (token != null && token.startsWith("Bearer ")) {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
                String jwt = token.substring(SecurityParams.HEADER_PREFIX.length());
                DecodedJWT decodedJWT = verifier.verify(jwt);
                String username = decodedJWT.getSubject();

                // Rechercher le compte associé à l'email
                Compte compte = service.getCompteByEmail(username);
                String compteID=compte.getId();
                if (compte != null) {
                    if (compte.getCoursesPurchased() == null) {
                        compte.setCoursesPurchased(new ArrayList<>());
                    }
                    ArrayList<Course> coursesPurchased = compte.getCoursesPurchased();
                    coursesPurchased.add(cours);
                    compte.setCoursesPurchased(coursesPurchased);

                    // Mettre à jour le compte avec le nouveau cours ajouté
                    service.updateCompte(compteID,compte);
                    cours.setIdEnseignant(compteID);
                    // Appeler le service "Cours" pour ajouter le cours
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<Course> result = restTemplate.postForEntity(Coursurl + "/addCour", cours, Course.class);

                    return new ResponseEntity<>(result.getStatusCodeValue() == 200 ? "Cours ajouté avec succès" : "Erreur lors de l'ajout du cours", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Aucun compte trouvé", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Token invalide ou non fourni", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de l'ajout du cours : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //etudiant
    @PostMapping("/addetudiant")
    public ResponseEntity<?> createEtudiant(@RequestBody Student body) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.postForEntity(studenturl + "/save", body, String.class);

            String response = String.valueOf(result);
            int startIndex = response.indexOf(',') + 1;

// Trouver l'index de la première virgule après l'index de départ
            int endIndex = response.indexOf(',', startIndex);

// Extraire la sous-chaîne qui contient l'ID utilisateur
            UserID = response.substring(startIndex, endIndex);

            return new ResponseEntity<>(result.getStatusCodeValue() == 200 ? "Etudiant created successfully user Id : " + UserID : "Enseignant Not created successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping ("/acheterCour/{Userid}/{COURSid}")
    public ResponseEntity<?> acheterCour( @PathVariable String COURSid,@PathVariable String Userid){

            if (COURSid == null || Userid == null) {
                return new ResponseEntity<>("Null Id", HttpStatus.BAD_REQUEST);
            }
        RestTemplate restTemplate = new RestTemplate();

        // URL à partir de laquelle vous souhaitez récupérer les données


        // Faire une requête GET pour récupérer les données

            Optional<Course> optionalCour = Optional.ofNullable(restTemplate.getForObject(Coursurl+"/getCour/"+COURSid, Course.class));
            if (optionalCour.isPresent()) {
                Course cours = optionalCour.get();
                Compte compte= service.findById(Userid);

                if (compte != null) {
                    if (compte.getCoursesPurchased() == null) {
                        compte.setCoursesPurchased(new ArrayList<>());
                    }
                    ArrayList<Course> coursesPurchased = compte.getCoursesPurchased();
                    coursesPurchased.add(cours);
                    compte.setCoursesPurchased(coursesPurchased);

                    // Mettre à jour le compte avec le nouveau cours ajouté
                    service.updateCompte(Userid, compte);


                }return new ResponseEntity<>("User " + Userid + " bought Cour " + COURSid + " successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cour doesn't exist", HttpStatus.BAD_REQUEST);
            }
        }




}