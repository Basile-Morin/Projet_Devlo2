package etude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
    }

    // --- Constructeur ---

    @Test
    void constructeurParDefaut_creeLObjet() {
        assertNotNull(etudiant);
    }

    @Test
    void constructeurParDefaut_attributsNuls() {
        assertNull(etudiant.getNumeroApprenant());
        assertNull(etudiant.getNom());
        assertNull(etudiant.getPrenom());
        assertNull(etudiant.getCursus());
    }

    // --- numeroApprenant ---

    @Test
    void setNumeroApprenant_stockeValeur() {
        etudiant.setNumeroApprenant("E12345");
        assertEquals("E12345", etudiant.getNumeroApprenant());
    }

    @Test
    void setNumeroApprenant_null_accepte() {
        etudiant.setNumeroApprenant(null);
        assertNull(etudiant.getNumeroApprenant());
    }

    // --- nom ---

    @Test
    void setNom_stockeValeur() {
        etudiant.setNom("Dupont");
        assertEquals("Dupont", etudiant.getNom());
    }

    @Test
    void setNom_chaineVide_accepte() {
        etudiant.setNom("");
        assertEquals("", etudiant.getNom());
    }

    // --- prenom ---

    @Test
    void setPrenom_stockeValeur() {
        etudiant.setPrenom("Alice");
        assertEquals("Alice", etudiant.getPrenom());
    }

    // --- cursus ---

    @Test
    void setCursus_stockeValeurE1() {
        etudiant.setCursus(Cursus.E1);
        assertEquals(Cursus.E1, etudiant.getCursus());
    }

    @Test
    void setCursus_stockeValeurE3A() {
        etudiant.setCursus(Cursus.E3A);
        assertEquals(Cursus.E3A, etudiant.getCursus());
    }

    @Test
    void setCursus_null_accepte() {
        etudiant.setCursus(null);
        assertNull(etudiant.getCursus());
    }

    // --- Cohérence globale ---

    @Test
    void etudiantComplet_tousAttributsCorrects() {
        etudiant.setNumeroApprenant("E99999");
        etudiant.setNom("Martin");
        etudiant.setPrenom("Bob");
        etudiant.setCursus(Cursus.E5);

        assertAll(
            () -> assertEquals("E99999", etudiant.getNumeroApprenant()),
            () -> assertEquals("Martin", etudiant.getNom()),
            () -> assertEquals("Bob", etudiant.getPrenom()),
            () -> assertEquals(Cursus.E5, etudiant.getCursus())
        );
    }
}
