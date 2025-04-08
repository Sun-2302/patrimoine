package school.hei.patrimoine.cas;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.*;

public class PatrimoineDeBakoTest {
    @Test
    void patrimoine_bako(){
        var bako = new Personne("Bako");
        var au8avril25 = LocalDate.of(2025, APRIL, 8);

        var courant = new Compte("Compte Courant", au8avril25, ariary(2_000_000));
        var epargne = new Compte("Compte Epargne", au8avril25, ariary(625_000));
        var coffre = new Compte ("Coffre fort", au8avril25, ariary(1_750_000));

        var salaire = new FluxArgent("Salaire", courant,au8avril25, au8avril25.plusYears(100) ,2, ariary(2_125_000));

        //epargne
        var virement = new FluxArgent("Virement", courant,au8avril25, au8avril25.plusYears(100) ,3, ariary(-200_000));
        var fluxEpargne = new FluxArgent("Epargne", epargne,au8avril25, au8avril25.plusYears(100) ,3, ariary(200_000));

        var loyer = new FluxArgent("Loyer", courant,au8avril25, au8avril25.plusYears(100) ,26, ariary(-600_000));
        var trainDeVie = new FluxArgent("Train De Vie", courant,au8avril25, au8avril25.plusYears(100) ,1, ariary(-700_000));

        var ordinateur = new Materiel("Ordinateur", au8avril25.minusDays(1), au8avril25, ariary(3_000_000), -0.12);

        var patrimoineBakoAu8Avril25 =
                Patrimoine.of(
                  "patrimoineBakoAu8Avril25",
                  Devise.MGA,
                  au8avril25,
                  bako,
                  Set.of(
                          courant,
                          epargne,
                          coffre,
                          salaire,
                          virement,
                          fluxEpargne,
                          loyer,
                          trainDeVie,
                          ordinateur
                  )
                );

        var au31decembre25 = LocalDate.of(2025, DECEMBER, 31);
        var patrimoineBakoAu31Dec25 = patrimoineBakoAu8Avril25.projectionFuture(au31decembre25);

        System.out.println(patrimoineBakoAu31Dec25.getValeurComptable(Devise.MGA));

    }
}
