package altn72.tpfilrouge.modele;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Via cette interface nous pouvons requêter la BDD
 * en profitant des requêtes prédéfinies utilisables directement
 *
 * @author Jacques Augustin
 *
  */
public interface PassagerRepository extends JpaRepository<Passager,Integer> {

    //    @Query("SELECT e from Passager e WHERE e.email = ?1")
    //Optional<Passager> rechercheParMail (String email);
}
