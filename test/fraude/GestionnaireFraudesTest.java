package fraude;

import etude.Epreuve;
import etude.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestionnaireFraudesTest {

    private GestionnaireFraudes gestionnaire;

    @BeforeEach
    void setUp() {
        gestionnaire = new GestionnaireFraudes();
    }

    private Etudiant creerEtudiant(String numero, String nom, String prenom) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        return etudiant;
    }

    private Epreuve creerEpreuve(String code) {
        Epreuve epreuve = new Epreuve();
        epreuve.setCodeECUE(code);
        return epreuve;
    }

    private FormulaireFraude creerFormulaire(Epreuve epreuve, Etudiant... etudiants) {
        FraudeIAG fraude = new FraudeIAG("ChatGPT");
        for (Etudiant etudiant : etudiants) {
            fraude.addEtudiant(etudiant);
        }
        FormulaireFraude formulaire = new FormulaireFraude();
        formulaire.setEpreuve(epreuve);
        formulaire.ajouterFraudeConstatee(fraude);
        return formulaire;
    }

    @Test
    void testAjouterFormulaire() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Epreuve epreuve1 = creerEpreuve("INFO");
        Epreuve epreuve2 = creerEpreuve("MATH");
        FormulaireFraude formulaire1 = creerFormulaire(epreuve1, etudiant1);
        FormulaireFraude formulaire2 = creerFormulaire(epreuve2, etudiant2);

        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);

        assertEquals(List.of(formulaire1, formulaire2), gestionnaire.getFormulaires());
        assertEquals(List.of(etudiant1, etudiant2), gestionnaire.getEtudiants());
        assertEquals(List.of(epreuve1, epreuve2), gestionnaire.getEpreuves());
        assertEquals(0, formulaire1.getId());
        assertEquals(1, formulaire2.getId());
    }

    @Test
    void testSupprimerFormulaire() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Epreuve epreuve1 = creerEpreuve("INFO");
        Epreuve epreuve2 = creerEpreuve("MATH");
        FormulaireFraude formulaire1 = creerFormulaire(epreuve1, etudiant1);
        FormulaireFraude formulaire2 = creerFormulaire(epreuve2, etudiant2);
        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);

        assertTrue(gestionnaire.supprimerFormulaire(formulaire1.getId()));
        assertEquals(List.of(formulaire2), gestionnaire.getFormulaires());
        assertEquals(List.of(etudiant2), gestionnaire.getEtudiants());
        assertEquals(List.of(epreuve2), gestionnaire.getEpreuves());

        assertFalse(gestionnaire.supprimerFormulaire(999));
        assertEquals(List.of(formulaire2), gestionnaire.getFormulaires());
        assertEquals(List.of(etudiant2), gestionnaire.getEtudiants());
        assertEquals(List.of(epreuve2), gestionnaire.getEpreuves());
    }

    @Test
    void testRechercherFormulairesParEtudiants() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Etudiant absent = creerEtudiant("999", "INCONNU", "Inconnu");
        FormulaireFraude formulaire1 = creerFormulaire(creerEpreuve("INFO"), etudiant1);
        FormulaireFraude formulaire2 = creerFormulaire(creerEpreuve("MATH"), etudiant2);
        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);

        assertEquals(List.of(formulaire1), gestionnaire.rechercherFormulairesParEtudiants(etudiant1));
        assertTrue(gestionnaire.rechercherFormulairesParEtudiants(absent).isEmpty());
    }

    @Test
    void testRechercherFormulaireParEpreuve() {
        Epreuve epreuve1 = creerEpreuve("INFO");
        Epreuve epreuve2 = creerEpreuve("MATH");
        Epreuve absente = creerEpreuve("PHYS");
        FormulaireFraude formulaire1 = creerFormulaire(epreuve1, creerEtudiant("001", "MORIN", "Basile"));
        FormulaireFraude formulaire2 = creerFormulaire(epreuve2, creerEtudiant("002", "DUPONT", "Alice"));
        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);

        assertEquals(List.of(formulaire1), gestionnaire.rechercherFormulaireParEpreuve(epreuve1));
        assertTrue(gestionnaire.rechercherFormulaireParEpreuve(absente).isEmpty());
    }

    @Test
    void testRechercherEtudiantsParNumero() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("INFO"), etudiant1));
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("MATH"), etudiant2));

        assertEquals(List.of(etudiant1), gestionnaire.rechercherEtudiantsParNumero("001"));
        assertTrue(gestionnaire.rechercherEtudiantsParNumero("999").isEmpty());
    }

    @Test
    void testRechercherEtudiantsParNom() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("INFO"), etudiant1));
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("MATH"), etudiant2));

        assertEquals(List.of(etudiant1), gestionnaire.rechercherEtudiantsParNom("MORIN"));
        assertTrue(gestionnaire.rechercherEtudiantsParNom("INCONNU").isEmpty());
    }

    @Test
    void testRechercherEtudiantsParPrenom() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("INFO"), etudiant1));
        gestionnaire.ajouterFormulaire(creerFormulaire(creerEpreuve("MATH"), etudiant2));

        assertEquals(List.of(etudiant1), gestionnaire.rechercherEtudiantsParPrenom("Basile"));
        assertTrue(gestionnaire.rechercherEtudiantsParPrenom("Inconnu").isEmpty());
    }

    @Test
    void testGetters() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Epreuve epreuve1 = creerEpreuve("INFO");
        Epreuve epreuve2 = creerEpreuve("MATH");
        FormulaireFraude formulaire1 = creerFormulaire(epreuve1, etudiant1);
        FormulaireFraude formulaire2 = creerFormulaire(epreuve2, etudiant2);
        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);

        assertEquals(List.of(formulaire1, formulaire2), gestionnaire.getFormulaires());
        assertEquals(List.of(etudiant1, etudiant2), gestionnaire.getEtudiants());
        assertEquals(List.of(epreuve1, epreuve2), gestionnaire.getEpreuves());
    }

    @Test
    void testReconstruireEtudiantsEtEpreuves() {
        Epreuve epreuve = creerEpreuve("INFO");
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        gestionnaire.ajouterFormulaire(creerFormulaire(epreuve, etudiant1));
        gestionnaire.ajouterFormulaire(creerFormulaire(epreuve, etudiant1, etudiant2));
        gestionnaire.getEtudiants().clear();
        gestionnaire.getEpreuves().clear();

        gestionnaire.reconstruireEtudiantsEtEpreuves();

        assertEquals(List.of(etudiant1, etudiant2), gestionnaire.getEtudiants());
        assertEquals(List.of(epreuve), gestionnaire.getEpreuves());
    }
}
