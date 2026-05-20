import etude.Epreuve;
import etude.Etudiant;
import fraude.Fraude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormulaireFraudeTest {
    FormulaireFraude formulaire;

    private Etudiant creerEtudiant(String numero) {
        Etudiant e = new Etudiant();
        e.setNumeroApprenant(numero);
        return e;
    }

    private Fraude creerFraude(Etudiant... etudiants) {
        Fraude fraude = new Fraude();

        for (Etudiant etudiant : etudiants) {
            fraude.addEtudiant(etudiant);
        }

        return fraude;
    }

    @BeforeEach
    void initialiserFormulaire(){
        formulaire = new FormulaireFraude();
    }

    @Test
    void constructeurVideInitialiseCorrectementLeFormulaire() {

        assertNotNull(formulaire.getEtudiants(), "La liste des étudiants ne doit pas être null après le constructeur");
        assertNotNull(formulaire.getFraudes(), "La liste des fraudes ne doit pas être null après le constructeur");
        assertTrue(formulaire.getEtudiants().isEmpty(), "La liste des étudiants doit être vide à la création");
        assertTrue(formulaire.getFraudes().isEmpty(), "La liste des fraudes doit être vide à la création");
        assertNotNull(formulaire.getDateCreation(), "La date de création doit être initialisée");
        assertNotNull(formulaire.getDateDerniereModification(), "La date de dernière modification doit être initialisée");
        assertEquals(formulaire.getDateCreation(), formulaire.getDateDerniereModification(), "À la création, la date de dernière modification doit être égale à la date de création");
    }

    @Test
    void constructeurAvecFraudesInitialiseCorrectementLeFormulaire() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");
        Etudiant e3 = creerEtudiant("003");
        Etudiant e4 = creerEtudiant("004");

        Fraude fraude1 = creerFraude(e1, e2);
        Fraude fraude2 = creerFraude(e3, e4);

        List<Fraude> fraudes = List.of(fraude1,fraude2);

        assertNotNull(formulaire.getEtudiants(), "La liste des étudiants ne doit pas être null après le constructeur");
        assertNotNull(formulaire.getFraudes(), "La liste des fraudes ne doit pas être null après le constructeur");
        assertTrue(formulaire.getEtudiants().isEmpty(), "La liste des étudiants doit être vide à la création");
        assertTrue(formulaire.getFraudes().isEmpty(), "La liste des fraudes doit être vide à la création");
        assertNotNull(formulaire.getDateCreation(), "La date de création doit être initialisée");
        assertNotNull(formulaire.getDateDerniereModification(), "La date de dernière modification doit être initialisée");
        assertEquals(formulaire.getDateCreation(), formulaire.getDateDerniereModification(), "À la création, la date de dernière modification doit être égale à la date de création");
    }

    @Test
    void ajouterFraudeConstateeAjouteLaFraudeEtSesEtudiants() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");
        Fraude fraude = creerFraude(e1, e2);

        formulaire.ajouterFraudeConstatee(fraude);

        assertEquals(1, formulaire.getFraudes().size(), "Le formulaire doit contenir exactement une fraude après l'ajout");
        assertTrue(formulaire.getFraudes().contains(fraude), "La fraude ajoutée doit être présente dans le formulaire");
        assertEquals(2, formulaire.getEtudiants().size(), "Les deux étudiants liés à la fraude doivent être ajoutés au formulaire");
        assertTrue(formulaire.getEtudiants().contains(e1), "Le premier étudiant de la fraude doit être présent dans le formulaire");
        assertTrue(formulaire.getEtudiants().contains(e2), "Le second étudiant de la fraude doit être présent dans le formulaire");
    }

    @Test
    void ajouterDeuxFraudesAvecUnEtudiantCommunNeCreePasDeDoublon() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");
        Etudiant e3 = creerEtudiant("003");

        Fraude fraude1 = creerFraude(e1, e2);
        Fraude fraude2 = creerFraude(e2, e3);

        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);

        assertEquals(2, formulaire.getFraudes().size(), "Le formulaire doit contenir les deux fraudes ajoutées");
        assertEquals(3, formulaire.getEtudiants().size(), "Un étudiant présent dans plusieurs fraudes ne doit apparaître qu'une seule fois");
        assertTrue(formulaire.getEtudiants().contains(e1), "L'étudiant e1 doit être présent dans le formulaire");
        assertTrue(formulaire.getEtudiants().contains(e2), "L'étudiant e2, commun aux deux fraudes, doit être présent");
        assertTrue(formulaire.getEtudiants().contains(e3), "L'étudiant e3 doit être présent dans le formulaire");
    }

    @Test
    void retirerFraudeConstateeSupprimeSeulementLesEtudiantsQuiNeSontPlusLiesAUneFraude() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");
        Etudiant e3 = creerEtudiant("003");

        Fraude fraude1 = creerFraude(e1, e2);
        Fraude fraude2 = creerFraude(e2, e3);

        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);

        formulaire.retirerFraudeConstatee(fraude1);

        assertEquals(1, formulaire.getFraudes().size(), "Une seule fraude doit rester après le retrait de fraude1");
        assertFalse(formulaire.getFraudes().contains(fraude1), "La fraude retirée ne doit plus être présente dans le formulaire");
        assertTrue(formulaire.getFraudes().contains(fraude2), "La fraude non retirée doit rester dans le formulaire");
        assertFalse(formulaire.getEtudiants().contains(e1), "L'étudiant e1 doit être supprimé car il n'est plus lié à aucune fraude");
        assertTrue(formulaire.getEtudiants().contains(e2), "L'étudiant e2 doit rester car il est encore lié à fraude2");
        assertTrue(formulaire.getEtudiants().contains(e3), "L'étudiant e3 doit rester car il est lié à fraude2");
        assertEquals(2, formulaire.getEtudiants().size(), "Il doit rester exactement deux étudiants dans le formulaire");
    }

    @Test
    void retirerLaDerniereFraudeVideLaListeDesEtudiants() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");

        Fraude fraude = creerFraude(e1, e2);

        formulaire.ajouterFraudeConstatee(fraude);

        formulaire.retirerFraudeConstatee(fraude);

        assertTrue(formulaire.getFraudes().isEmpty(), "La liste des fraudes doit être vide après le retrait de la seule fraude");
        assertTrue(formulaire.getEtudiants().isEmpty(), "La liste des étudiants doit être vide lorsqu'il ne reste aucune fraude");
    }

    @Test
    void constructeurAvecFraudesAjouteLesFraudesEtLesEtudiantsAssocies() {
        Etudiant e1 = creerEtudiant("001");
        Etudiant e2 = creerEtudiant("002");
        Etudiant e3 = creerEtudiant("003");

        Fraude fraude1 = creerFraude(e1, e2);
        Fraude fraude2 = creerFraude(e2, e3);

        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);

        assertEquals(2, formulaire.getFraudes().size(), "Le constructeur doit ajouter toutes les fraudes fournies");
        assertEquals(3, formulaire.getEtudiants().size(), "Le constructeur doit ajouter tous les étudiants liés aux fraudes sans doublon");
        assertTrue(formulaire.getFraudes().contains(fraude1), "fraude1 doit être présente dans le formulaire");
        assertTrue(formulaire.getFraudes().contains(fraude2), "fraude2 doit être présente dans le formulaire");
        assertTrue(formulaire.getEtudiants().contains(e1), "e1 doit être présent dans le formulaire");
        assertTrue(formulaire.getEtudiants().contains(e2), "e2 doit être présent dans le formulaire");
        assertTrue(formulaire.getEtudiants().contains(e3), "e3 doit être présent dans le formulaire");
    }

    @Test
    void getFraudesRetourneUneListeNonModifiable() {
        FormulaireFraude formulaire = new FormulaireFraude();

        assertThrows(UnsupportedOperationException.class, () -> formulaire.getFraudes().add(new Fraude()), "La liste retournée par getFraudes() ne doit pas être modifiable");
    }

    @Test
    void getEtudiantsRetourneUneListeNonModifiable() {
        FormulaireFraude formulaire = new FormulaireFraude();
        Etudiant e1 = creerEtudiant("001");

        assertThrows(UnsupportedOperationException.class, () -> formulaire.getEtudiants().add(e1), "La liste retournée par getEtudiants() ne doit pas être modifiable");
    }

    @Test
    void ajouterFraudeConstateeMetAJourLaDateDeDerniereModification() {
        FormulaireFraude formulaire = new FormulaireFraude();
        LocalDateTime avantModification = formulaire.getDateDerniereModification();

        Etudiant e1 = creerEtudiant("001");
        Fraude fraude = creerFraude(e1);

        formulaire.ajouterFraudeConstatee(fraude);

        assertFalse(formulaire.getDateDerniereModification().isBefore(avantModification), "La date de dernière modification doit être mise à jour après l'ajout d'une fraude");
    }

    @Test
    void retirerFraudeConstateeMetAJourLaDateDeDerniereModification() {
        Etudiant e1 = creerEtudiant("001");
        Fraude fraude = creerFraude(e1);

        FormulaireFraude formulaire = new FormulaireFraude();
        formulaire.ajouterFraudeConstatee(fraude);

        LocalDateTime avantRetrait = formulaire.getDateDerniereModification();

        formulaire.retirerFraudeConstatee(fraude);

        assertFalse(formulaire.getDateDerniereModification().isBefore(avantRetrait), "La date de dernière modification doit être mise à jour après le retrait d'une fraude");
    }

    @Test
    void setEtGetEpreuveFonctionnentCorrectement() {
        FormulaireFraude formulaire = new FormulaireFraude();
        Epreuve epreuve = new Epreuve();

        formulaire.setEpreuve(epreuve);

        assertSame(epreuve, formulaire.getEpreuve(), "getEpreuve() doit retourner l'épreuve passée à setEpreuve()");
    }

    @Test
    void setEtGetIdFonctionnentCorrectement() {
        FormulaireFraude formulaire = new FormulaireFraude();

        formulaire.setId(42);

        assertEquals(42, formulaire.getId(), "getId() doit retourner l'identifiant passé à setId()");
    }

    @Test
    void setEtGetDateCreationFonctionnentCorrectement() {
        FormulaireFraude formulaire = new FormulaireFraude();
        LocalDateTime dateCreation = LocalDateTime.of(2026, 5, 20, 10, 30);

        formulaire.setDateCreation(dateCreation);

        assertEquals(dateCreation, formulaire.getDateCreation(), "getDateCreation() doit retourner la date passée à setDateCreation()");
    }

    @Test
    void setEtGetDateDerniereModificationFonctionnentCorrectement() {
        FormulaireFraude formulaire = new FormulaireFraude();
        LocalDateTime dateModification = LocalDateTime.of(2026, 5, 20, 11, 45);

        formulaire.setDateDerniereModification(dateModification);

        assertEquals(dateModification, formulaire.getDateDerniereModification(), "getDateDerniereModification() doit retourner la date passée à setDateDerniereModification()");
    }
}