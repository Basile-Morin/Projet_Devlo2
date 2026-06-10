package fraude;

import etude.Etudiant;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudeTest {

    private Etudiant creerEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant("001");
        etudiant.setNom("MORIN");
        etudiant.setPrenom("Basile");
        return etudiant;
    }

    @Test
    void testAddEtudiant() {
        Fraude fraude = new FraudeIAG();
        Etudiant etudiant = creerEtudiant();

        fraude.addEtudiant(etudiant);

        assertEquals(List.of(etudiant), fraude.getEtudiants());
    }

    @Test
    void testRemoveEtudiant() {
        Fraude fraude = new FraudeIAG();
        Etudiant etudiant = creerEtudiant();
        fraude.addEtudiant(etudiant);

        fraude.removeEtudiant(etudiant);

        assertFalse(fraude.getEtudiants().contains(etudiant));
    }

    @Test
    void testGettersSetters() {
        Fraude fraude = new FraudeIAG();
        Etudiant etudiant = creerEtudiant();
        assertTrue(fraude.getEtudiants().isEmpty());
        assertEquals(LocalDate.now(), fraude.getDateReleve());

        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(etudiant);
        LocalDate date = LocalDate.of(2026, 6, 9);
        fraude.setEtudiants(etudiants);
        fraude.setContenu("Capture d'écran");
        fraude.setDescription("Utilisation d'une IA");
        fraude.setDateReleve(date);

        assertEquals(List.of(etudiant), fraude.getEtudiants());
        assertThrows(UnsupportedOperationException.class, () -> fraude.getEtudiants().clear());
        assertEquals("Capture d'écran", fraude.getContenu());
        assertEquals("Utilisation d'une IA", fraude.getDescription());
        assertEquals(date, fraude.getDateReleve());
    }
}
