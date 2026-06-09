//utilisation d'IAG afin de trouver de potentiels tests supplémentaires à faire

import etude.Epreuve;
import etude.Etudiant;
import fraude.FraudeIAG;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireFraudeTest {

    private GestionnaireFraudes gestionnaire;
    private Etudiant creerEtudiant(String numero, String nom, String prenom) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        return etudiant;
    }

    private FormulaireFraude creerFormulaire(int id, Etudiant... etudiants) {
        FormulaireFraude f = new FormulaireFraude();
        f.setId(id);
        FraudeIAG fraude = new FraudeIAG("TestService");
        for (Etudiant e : etudiants) {
            fraude.addEtudiant(e);
        }
        f.ajouterFraudeConstatee(fraude);
        return f;
    }

    @BeforeEach
    void setUp() {
        gestionnaire = new GestionnaireFraudes();
    }

    // --- tests sur l'ajout de formulaire ---

    @Test
    void TestAjouter1Formulaire() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        FormulaireFraude f = creerFormulaire(1, e1);

        gestionnaire.ajouterFormulaire(f);

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulairesParEtudiants(e1);
        assertEquals(1, resultats.size(), "On devrait avoir 1");
        assertTrue(resultats.contains(f), "le gestionnaire devrait contenir le formulaire");
    }

    @Test
    void TestAjouterPlusieursFormulaires() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        Etudiant e2 = creerEtudiant("2", "MARTY", "Égide");
        FormulaireFraude f1 = creerFormulaire(1, e1);
        FormulaireFraude f2 = creerFormulaire(2, e2);

        gestionnaire.ajouterFormulaire(f1);
        gestionnaire.ajouterFormulaire(f2);

        assertEquals(1, gestionnaire.rechercherFormulairesParEtudiants(e1).size(), "On devrait avoir 1 formulaire");
        assertEquals(1, gestionnaire.rechercherFormulairesParEtudiants(e2).size(), "On devrait avoir 1 formulaire");
    }

    // --- tests sur la suppression de formulaire ---

    @Test
    void TestSupprimerFormulaire2() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        FormulaireFraude f = creerFormulaire(1, e1);
        gestionnaire.ajouterFormulaire(f);

        gestionnaire.supprimerFormulaire(f.getId());

        assertTrue(gestionnaire.rechercherFormulairesParEtudiants(e1).isEmpty(), "Le gestionnaire devrait être vide");
    }

    @Test
    void TestSupprimerFormulaireIdInexistant() {
        boolean result = gestionnaire.supprimerFormulaire(1);

        assertFalse(result, "Le formulaire devrait être supprimé");
    }

    @Test
    void TestSupprimeLeBonFormulaire() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        Etudiant e2 = creerEtudiant("2", "MARTY", "Égide");
        FormulaireFraude f1 = creerFormulaire(1, e1);
        FormulaireFraude f2 = creerFormulaire(2, e2);
        gestionnaire.ajouterFormulaire(f1);
        gestionnaire.ajouterFormulaire(f2);

        gestionnaire.supprimerFormulaire(f1.getId());

        assertTrue(gestionnaire.rechercherFormulairesParEtudiants(e1).isEmpty(), "Le premier formulaire devrait être supprimé");
        assertEquals(1, gestionnaire.rechercherFormulairesParEtudiants(e2).size(),"Le deuxième formulaire ne devrait pas être supprimé");
    }

    // --- rechercherFormulairesParEtudiants ---

    @Test
    void TestRechercherFormulairesParEtudiantsEtudiantPresent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        FormulaireFraude f1 = creerFormulaire(1, e1);
        FormulaireFraude f2 = creerFormulaire(2, e1);
        gestionnaire.ajouterFormulaire(f1);
        gestionnaire.ajouterFormulaire(f2);

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulairesParEtudiants(e1);

        assertEquals(2, resultats.size(),"Le gestionnaire devrait avoir 2 formulaire");
        assertTrue(resultats.contains(f1),"Le gestionnaire devrait avoir le formulaire f1");
        assertTrue(resultats.contains(f2),"Le gestionnaire devrait avoir le formulaire f2");
    }

    @Test
    void TestRechercherFormulairesParEtudiantsEtudiantAbsent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        Etudiant e2 = creerEtudiant("2", "MARTY", "Égide");
        gestionnaire.ajouterFormulaire(creerFormulaire(1, e1));

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulairesParEtudiants(e2);

        assertNotNull(resultats, "On devrait récupérer une liste"); //on récupère une liste donc pas Null
        assertTrue(resultats.isEmpty(), "La liste devrait être vide"); //elle est vide
    }

    @Test
    void TestRechercherFormulairesParEtudiantsAucunFormulaire() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulairesParEtudiants(e1);

        assertNotNull(resultats, "On devrait avoir une liste vide");
        assertTrue(resultats.isEmpty(), "");
    }

    // --- rechercherFormulaireParEpreuve ---

    @Test
    void TestRechercherFormulaireParEpreuveEpreuvePresente() {
        Epreuve epreuve = new Epreuve();
        epreuve.setCodeECUE("maths");
        FormulaireFraude f = new FormulaireFraude();
        f.setId(1);
        f.setEpreuve(epreuve);
        gestionnaire.ajouterFormulaire(f);

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulaireParEpreuve(epreuve);

        assertEquals(1, resultats.size(), "On devrait avoir un formulaire");
        assertTrue(resultats.contains(f), "Le gestionnaire devrait contenir le formulaire f");
    }

    @Test
    void TestRechercherFormulaireParEpreuveEpreuveAbsente() {
        Epreuve epreuve1 = new Epreuve();
        epreuve1.setCodeECUE("maths");
        Epreuve epreuve2 = new Epreuve();
        epreuve2.setCodeECUE("informatique");
        FormulaireFraude f = new FormulaireFraude();
        f.setEpreuve(epreuve1);
        gestionnaire.ajouterFormulaire(f);

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulaireParEpreuve(epreuve2);

        assertNotNull(resultats, "");
        assertTrue(resultats.isEmpty(), "");
    }

    @Test
    void TestRechercherFormulaireParEpreuveMemeEpreuvePlusieursFormulaires() {
        Epreuve epreuve = new Epreuve();
        epreuve.setCodeECUE("maths");
        FormulaireFraude f1 = new FormulaireFraude();
        f1.setId(1);
        f1.setEpreuve(epreuve);
        FormulaireFraude f2 = new FormulaireFraude();
        f2.setId(2);
        f2.setEpreuve(epreuve);
        gestionnaire.ajouterFormulaire(f1);
        gestionnaire.ajouterFormulaire(f2);

        List<FormulaireFraude> resultats = gestionnaire.rechercherFormulaireParEpreuve(epreuve);

        assertEquals(2, resultats.size(), "");
    }

    // --- rechercherEtudiantsParNom ---

    // --- rechercherEtudiantsParNumero (bug documenté) ---

    @Test
    void TestRechercherEtudiantsParNumeroNumeroAbsent() {
        // La liste interne etudiants est vide : aucun étudiant trouvé → null attendu
        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParNumero("999");

        assertNull(resultats, "Doit retourner null si le numéro n'est pas trouvé");
    }

    // --- rechercherEtudiantsParNom ---

    @Test
    void TestRechercherEtudiantsParNomNomPresent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        gestionnaire.ajouterFormulaire(creerFormulaire(1, e1));

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParNom("MORIN");

        assertEquals(1, resultats.size(), "On devrait trouver 1 étudiant");
        assertEquals("MORIN", resultats.get(0).getNom(), "Le nom doit correspondre");
    }

    @Test
    void TestRechercherEtudiantsParNomNomAbsent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        gestionnaire.ajouterFormulaire(creerFormulaire(1, e1));

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParNom("inconnu");

        assertTrue(resultats.isEmpty(), "La liste doit être vide si le nom n'existe pas");
    }

    // --- rechercherEtudiantsParPrenom ---

    @Test
    void TestRechercherEtudiantsParPrenomPrenomPresent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        gestionnaire.ajouterFormulaire(creerFormulaire(1, e1));

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParPrenom("Basile");

        assertEquals(1, resultats.size(), "On devrait trouver 1 étudiant");
        assertEquals("Basile", resultats.get(0).getPrenom(), "Le prénom doit correspondre");
    }

    // --- rechercherEtudiantsParNumero ---

    @Test
    void TestRechercherEtudiantsParNumero_numeroPresent() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        gestionnaire.ajouterFormulaire(creerFormulaire(1, e1));

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParNumero("1");

        assertNotNull(resultats, "La liste ne doit pas être null");
        assertTrue(resultats.contains(e1), "La liste doit contenir l'étudiant recherché");
    }

    // --- Tests des Getters ---

    @Test
    void TestGetters() {
        Etudiant e1 = creerEtudiant("1", "MORIN", "Basile");
        FormulaireFraude f1 = creerFormulaire(1, e1);
        Epreuve epreuve = new Epreuve();
        epreuve.setCodeECUE("maths");
        f1.setEpreuve(epreuve);

        gestionnaire.ajouterFormulaire(f1);

        assertEquals(1, gestionnaire.getFormulaires().size(), "devrait retourner 1");
        assertEquals(1, gestionnaire.getEtudiants().size(), "devrait retourner 1");
        assertEquals(1, gestionnaire.getEpreuves().size(), "devrait retourner 1");
    }

}