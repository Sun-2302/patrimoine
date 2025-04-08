package school.hei.patrimoine.cas;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;

import java.time.LocalDate;
import java.util.Set;

import static java.time.Month.*;
import static school.hei.patrimoine.modele.Argent.ariary;

public class PatrimoineDeTianaTest {
    @Test
    void patrimoine_tiana(){
        var tiana = new Personne("Tiana");
        var au8avril25 = LocalDate.of(2025, APRIL, 8);

        var compteBancaire = new Compte("Compte Bancaire", au8avril25, ariary(60_000_000));

        var terrain = new Materiel("Terrain", au8avril25.minusDays(1), au8avril25, ariary(100_000_000), +0.1);

        var trainDeVie = new FluxArgent("TrainDeVie", compteBancaire,au8avril25, au8avril25 ,1, ariary(-4_000_000));

        //entreprise
        var au1juin25 = LocalDate.of(2025, JUNE, 1);
        var au31decembre25 = LocalDate.of(2025, DECEMBER, 31);
        var depenseEntreprise = new FluxArgent("Dépense de l'entreprise", compteBancaire, au1juin25, au31decembre25 ,5, ariary(-5_000_000));
        int revenu = 70_000_000;
        var revenusEntrepriseDebut = new FluxArgent("Revenus de l'entreprise", compteBancaire, au1juin25.minusMonths(1) , ariary((int)(revenu*0.1)));
        var revenusEntrepriseFin = new FluxArgent("Revenus de l'entreprise", compteBancaire, au31decembre25.plusMonths(1), ariary((int)(revenu*0.9)));

        //Prêt bancaire
        var au27juillet25 = LocalDate.of(2025, JULY, 27);
        var au27aout25 = LocalDate.of(2025, AUGUST, 27);
        var pretBancaire = new FluxArgent("Prêt bancaire", compteBancaire, au27juillet25 , ariary(20_000_000));
        var remboursement = new FluxArgent("Remboursement", compteBancaire, au27aout25, au27aout25.plusMonths(11), 1 , ariary(2_000_000));

        var patrimoneTianaAu8Avril25 =
                Patrimoine.of(
                        "patrimoineTianaAu8Avril25",
                        Devise.MGA,
                        au8avril25,
                        tiana,
                        Set.of(
                                compteBancaire,
                                terrain,
                                trainDeVie,
                                depenseEntreprise,
                                revenusEntrepriseDebut,
                                revenusEntrepriseFin,
                                pretBancaire,
                                remboursement
                        )
                );

        var au31mars26 = LocalDate.of(2026, MARCH, 26);
        var patrimoineTianaau31Mars26 = patrimoneTianaAu8Avril25.projectionFuture(au31mars26);

        System.out.println(patrimoineTianaau31Mars26.getValeurComptable(Devise.MGA));
    }
}
