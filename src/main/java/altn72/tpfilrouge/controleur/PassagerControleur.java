package altn72.tpfilrouge.controleur;

import altn72.tpfilrouge.modele.Passager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Classe responsable de l'aiguillage
 * C'est notre tour de contrôle
 *
 * @author Jacques Augustin
 */
//@RestController
@Controller
@RequestMapping("/tpfilrouge") //cette annotation permet de spécifier une url "racine" qui s'applique à tous les endpoints définis dans la classe
public class PassagerControleur {

    private final PassagerService passagerService;  // "final" => la valeur de la variable de pourra plus être modifiée APRES l'initialisation

    @Autowired   // Injection de dépendance via le constructeur (pratique recommandée)
    public PassagerControleur(PassagerService passagerService) {
        this.passagerService = passagerService;
    }

    // Question 6.1
    @GetMapping("/passagers")
    // La méthode ci-dessous est ce que l'on appelle un endpoint car elle peut être appelée
    // en ajoutant /passagers à l'url de base de l'application + le path défini via @RequestMapping
//    public List<Passager> afficherInfosPassagers(){
    public String afficherInfosPassagers(Model model){

//        return List.of(
//                new Passager_V0(1,"ACHVAR", "Didier","RF101010"),
//                new Passager_V0(2,"AUGUSTIN","Jacques","RF123456"));

//        return passagerService.getTousLesPassagers();
        model.addAttribute("passagers",passagerService.getTousLesPassagers());
        return "listePassagers";
    }

    // Question 6.2
    @GetMapping("/{idPassager}") // cet endpoint sera appelé via l'url localhost:8080/tpfilrouge/2 (par exemple)
    public String afficherPassager(@PathVariable Integer idPassager, Model model) {
        Optional<Passager> unPassager = passagerService.recupererUnPassager(idPassager);
        model.addAttribute("passagerToUpdate", unPassager);
        return "detailsPassager";
    }

    // Questions 6.3 & 7.7
    @DeleteMapping("/supprimerPassager/{idPassager}")
    public String supprimerPassager(@PathVariable Integer idPassager) {
        passagerService.supprimerUnPassager(idPassager);
        return "redirect:/tpfilrouge/passagers";
    }


    /**
     * @author JAA
     * DEBUT bloc pour modif passager
     */

    @PutMapping("/preparerModifPassager/{idPassager}")
    public String preparerModifPassager(@PathVariable Integer idPassager, Model model)  {
        Optional<Passager> passagerToUpdate = passagerService.recupererUnPassager(idPassager);
        model.addAttribute("passagerToUpdate", passagerToUpdate.get());
        return "detailsPassager";
    }

    // Question 6.4
    @PutMapping("modifierPassager/{idPassager}")
    public String modifierPassager(@PathVariable Integer idPassager,
                                   @ModelAttribute("passagerAModifier") Passager passagerModifie) {
        passagerService.modifierPassager(idPassager, passagerModifie);
        return "redirect:/tpfilrouge/passagers";
    }


    /**
     * @author JAA
     * DEBUT bloc pour ajout d'un nouveau passager
     */
    // Question 6.5
//    @PostMapping("/ajouter")
    @PostMapping("/ajouter")
    public String ajouterPassager(@ModelAttribute("newPassager") Passager passager) {
        passagerService.ajouterPassager(passager);
        return "redirect:/tpfilrouge/passagers";
    }


    //POur que Thymeleaf affiche le formulaire vierge
    // permettant d'ajouter un passager
    @GetMapping("/preparerAjoutPassager")
    public String preparerAjoutPassager(Model model) {
        Passager newPassager = new Passager();
        model.addAttribute("newPassager", newPassager);
        return "nouveauPassager";
    }
}
