package fraude;

import etude.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatistiquesFraudesTest {

    private StatistiquesFraudes statistiques;
    private List<FormulaireFraude> formulaires;

    @BeforeEach
    void setUp() {
        statistiques = new StatistiquesFraudes();
        Etudiant etudiant1 = creerEtudiant("001");
        Etudiant etudiant2 = creerEtudiant("002");
        formulaires = List.of(creerFormulaire(etudiant1, 1), creerFormulaire(etudiant2, 3));
    }

    private Etudiant creerEtudiant(String numero) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        return etudiant;
    }

    private FormulaireFraude creerFormulaire(Etudiant etudiant, int nombreFraudes) {
        FormulaireFraude formulaire = new FormulaireFraude();
        for (int i = 0; i < nombreFraudes; i++) {
            FraudeIAG fraude = new FraudeIAG("ChatGPT");
            fraude.addEtudiant(etudiant);
            formulaire.ajouterFraudeConstatee(fraude);
        }
        return formulaire;
    }

    @Test
    void testNombreTotalFormulaires() {
        assertEquals(0, statistiques.nombreTotalFormulaires(null));
        assertEquals(2, statistiques.nombreTotalFormulaires(formulaires));
    }

    @Test
    void testNombreEtudiantsDistincts() {
        assertEquals(0, statistiques.nombreEtudiantsDistincts(null));
        assertEquals(2, statistiques.nombreEtudiantsDistincts(formulaires));
    }

    @Test
    void testNombreTotalFraudes() {
        assertEquals(0, statistiques.nombreTotalFraudes(null));
        assertEquals(4, statistiques.nombreTotalFraudes(formulaires));
    }

    @Test
    void testMoyenneFraudesParFormulaire() {
        assertEquals(0.0, statistiques.moyenneFraudesParFormulaire(null));
        assertEquals(0.0, statistiques.moyenneFraudesParFormulaire(List.of()));
        assertEquals(2.0, statistiques.moyenneFraudesParFormulaire(formulaires));
    }

    @Test
    void testEcartTypeFraudesParFormulaire() {
        assertEquals(0.0, statistiques.ecartTypeFraudesParFormulaire(null));
        assertEquals(0.0, statistiques.ecartTypeFraudesParFormulaire(List.of()));
        assertEquals(1.0, statistiques.ecartTypeFraudesParFormulaire(formulaires));
    }
}
