package etude;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EpreuveTest {

    private Epreuve epreuve;

    @BeforeEach
    void setUp() {
        epreuve = new Epreuve();
    }

    // --- Constructeur ---

    @Test
    void constructeurParDefaut_creeLObjet() {
        assertNotNull(epreuve);
    }

    @Test
    void constructeurParDefaut_attributsNuls() {
        assertNull(epreuve.getCodeECUE());
        assertNull(epreuve.getDate());
        assertNull(epreuve.getHeure());
        assertEquals(0, epreuve.getDuree());
        assertNull(epreuve.getModalite());
    }

    // --- codeECUE ---

    @Test
    void setCodeECUE_stockeValeur() {
        epreuve.setCodeECUE("MATH101");
        assertEquals("MATH101", epreuve.getCodeECUE());
    }

    @Test
    void setCodeECUE_null_accepte() {
        epreuve.setCodeECUE(null);
        assertNull(epreuve.getCodeECUE());
    }

    // --- date ---

    @Test
    void setDate_stockeValeur() {
        LocalDate date = LocalDate.of(2025, 6, 15);
        epreuve.setDate(date);
        assertEquals(date, epreuve.getDate());
    }

    @Test
    void setDate_null_accepte() {
        epreuve.setDate(null);
        assertNull(epreuve.getDate());
    }

    // --- heure ---

    @Test
    void setHeure_stockeValeur() {
        LocalTime heure = LocalTime.of(9, 30);
        epreuve.setHeure(heure);
        assertEquals(heure, epreuve.getHeure());
    }

    @Test
    void setHeure_null_accepte() {
        epreuve.setHeure(null);
        assertNull(epreuve.getHeure());
    }

    // --- duree ---

    @Test
    void setDuree_valeurPositive_stocke() {
        epreuve.setDuree(120);
        assertEquals(120, epreuve.getDuree());
    }

    @Test
    void setDuree_zero_accepte() {
        epreuve.setDuree(0);
        assertEquals(0, epreuve.getDuree());
    }

    @Test
    void setDuree_valeurNegative_accepte() {
        // Pas de contrainte dans la classe : on vérifie le comportement réel
        epreuve.setDuree(-30);
        assertEquals(-30, epreuve.getDuree());
    }

    // --- modalite ---

    @Test
    void setModalite_examenEcrit_stocke() {
        epreuve.setModalite(Modalite.EXAMEN_ECRIT);
        assertEquals(Modalite.EXAMEN_ECRIT, epreuve.getModalite());
    }

    @Test
    void setModalite_oral_stocke() {
        epreuve.setModalite(Modalite.ORAL);
        assertEquals(Modalite.ORAL, epreuve.getModalite());
    }

    @Test
    void setModalite_null_accepte() {
        epreuve.setModalite(null);
        assertNull(epreuve.getModalite());
    }

    // --- Cohérence globale ---

    @Test
    void epreuveComplete_tousAttributsCorrects() {
        LocalDate date = LocalDate.of(2025, 1, 20);
        LocalTime heure = LocalTime.of(14, 0);

        epreuve.setCodeECUE("INFO202");
        epreuve.setDate(date);
        epreuve.setHeure(heure);
        epreuve.setDuree(90);
        epreuve.setModalite(Modalite.QCM);

        assertAll(
            () -> assertEquals("INFO202", epreuve.getCodeECUE()),
            () -> assertEquals(date, epreuve.getDate()),
            () -> assertEquals(heure, epreuve.getHeure()),
            () -> assertEquals(90, epreuve.getDuree()),
            () -> assertEquals(Modalite.QCM, epreuve.getModalite())
        );
    }
}
