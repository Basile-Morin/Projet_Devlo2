package fraude;

import etude.Epreuve;
import etude.Etudiant;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormulaireFraudeTest {

    private Etudiant creerEtudiant(String numero) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        return etudiant;
    }

    private Fraude creerFraude(Etudiant... etudiants) {
        Fraude fraude = new FraudeIAG("ChatGPT");
        for (Etudiant etudiant : etudiants) {
            fraude.addEtudiant(etudiant);
        }
        return fraude;
    }

    private Fraude creerFraudeSansEtudiants() {
        return new FraudeIAG("ChatGPT") {
            @Override
            public List<Etudiant> getEtudiants() {
                return null;
            }
        };
    }

    @Test
    void testConstructeurs() {
        FormulaireFraude vide = new FormulaireFraude();
        assertTrue(vide.getFraudes().isEmpty());
        assertTrue(vide.getEtudiants().isEmpty());
        assertNotNull(vide.getDateCreation());
        assertEquals(vide.getDateCreation(), vide.getDateDerniereModification());

        Etudiant etudiant1 = creerEtudiant("001");
        Etudiant etudiant2 = creerEtudiant("002");
        Etudiant etudiant3 = creerEtudiant("003");
        Fraude fraude1 = creerFraude(etudiant1, etudiant2);
        Fraude fraude2 = creerFraude(etudiant2, etudiant3);
        Fraude fraude3 = creerFraudeSansEtudiants();
        FormulaireFraude rempli = new FormulaireFraude(List.of(fraude1, fraude2, fraude3));

        assertEquals(List.of(fraude1, fraude2, fraude3), rempli.getFraudes());
        assertEquals(List.of(etudiant1, etudiant2, etudiant3), rempli.getEtudiants());
        assertTrue(new FormulaireFraude(null).getFraudes().isEmpty());
    }

    @Test
    void testAjouterFraudeConstatee() {
        FormulaireFraude formulaire = new FormulaireFraude();
        Etudiant etudiant1 = creerEtudiant("001");
        Etudiant etudiant2 = creerEtudiant("002");
        Etudiant etudiant3 = creerEtudiant("003");
        Fraude fraude1 = creerFraude(etudiant1, etudiant2);
        Fraude fraude2 = creerFraude(etudiant2, etudiant3);
        Fraude fraude3 = creerFraudeSansEtudiants();

        formulaire.ajouterFraudeConstatee(null);
        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);
        formulaire.ajouterFraudeConstatee(fraude3);

        assertEquals(List.of(fraude1, fraude2, fraude3), formulaire.getFraudes());
        assertEquals(List.of(etudiant1, etudiant2, etudiant3), formulaire.getEtudiants());
    }

    @Test
    void testRetirerFraudeConstatee() {
        FormulaireFraude formulaire = new FormulaireFraude();
        Etudiant etudiant1 = creerEtudiant("001");
        Etudiant etudiant2 = creerEtudiant("002");
        Etudiant etudiant3 = creerEtudiant("003");
        Fraude fraude1 = creerFraude(etudiant1, etudiant2);
        Fraude fraude2 = creerFraude(etudiant2, etudiant3);
        Fraude fraude3 = creerFraudeSansEtudiants();
        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);
        formulaire.ajouterFraudeConstatee(fraude3);

        formulaire.retirerFraudeConstatee(null);
        formulaire.retirerFraudeConstatee(creerFraude(etudiant1));
        formulaire.retirerFraudeConstatee(fraude1);

        assertEquals(List.of(fraude2, fraude3), formulaire.getFraudes());
        assertFalse(formulaire.getEtudiants().contains(etudiant1));
        assertEquals(List.of(etudiant2, etudiant3), formulaire.getEtudiants());
    }

    @Test
    void testGettersSetters() {
        FormulaireFraude formulaire = new FormulaireFraude();
        Etudiant etudiant1 = creerEtudiant("001");
        Etudiant etudiant2 = creerEtudiant("002");
        Etudiant etudiant3 = creerEtudiant("003");
        Fraude fraude1 = creerFraude(etudiant1, etudiant2);
        Fraude fraude2 = creerFraude(etudiant2, etudiant3);
        Fraude fraude3 = creerFraudeSansEtudiants();
        Epreuve epreuve = new Epreuve();
        LocalDateTime creation = LocalDateTime.of(2026, 6, 8, 9, 0);
        LocalDateTime modification = LocalDateTime.of(2026, 6, 9, 10, 0);
        formulaire.ajouterFraudeConstatee(fraude1);
        formulaire.ajouterFraudeConstatee(fraude2);
        formulaire.ajouterFraudeConstatee(fraude3);

        formulaire.setEpreuve(epreuve);
        formulaire.setDateCreation(creation);
        formulaire.setDateDerniereModification(modification);
        formulaire.setId(42);

        assertSame(epreuve, formulaire.getEpreuve());
        assertEquals(creation, formulaire.getDateCreation());
        assertEquals(42, formulaire.getId());
        assertFalse(formulaire.getDateDerniereModification().isBefore(modification));
        assertThrows(UnsupportedOperationException.class, () -> formulaire.getFraudes().clear());
        assertThrows(UnsupportedOperationException.class, () -> formulaire.getEtudiants().clear());
        assertEquals(List.of(fraude1, fraude2, fraude3), formulaire.getFraudes());
        assertEquals(List.of(etudiant1, etudiant2, etudiant3), formulaire.getEtudiants());
    }
}
