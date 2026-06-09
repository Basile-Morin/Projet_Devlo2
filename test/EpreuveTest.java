import etude.*;
import fraude.*;
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
        epreuve.setCodeECUE("maths");
        assertEquals("maths", epreuve.getCodeECUE());
    }

    // --- date ---

    @Test
    void setDate_stockeValeur() {
        LocalDate date = LocalDate.of(2026, 6, 9);
        epreuve.setDate(date);
        assertEquals(date, epreuve.getDate());
    }

    // --- heure ---

    @Test
    void setHeure_stockeValeur() {
        LocalTime heure = LocalTime.of(14, 40);
        epreuve.setHeure(heure);
        assertEquals(heure, epreuve.getHeure());
    }

    // --- duree ---

    @Test
    void setDuree_valeurPositive_stocke() {
        epreuve.setDuree(120);
        assertEquals(120, epreuve.getDuree());
    }

    // --- modalite ---

    @Test
    void setModalite_examenEcrit_stocke() {
        epreuve.setModalite(Modalite.EXAMEN_ECRIT);
        assertEquals(Modalite.EXAMEN_ECRIT, epreuve.getModalite());
    }

    // --- Cohérence globale ---

    @Test
    void epreuveComplete_tousAttributsCorrects() {
        LocalDate date = LocalDate.of(2026, 6, 9);
        LocalTime heure = LocalTime.of(14, 40);

        epreuve.setCodeECUE("informatique");
        epreuve.setDate(date);
        epreuve.setHeure(heure);
        epreuve.setDuree(90);
        epreuve.setModalite(Modalite.QCM);

        assertAll(
                () -> assertEquals("informatique", epreuve.getCodeECUE()),
                () -> assertEquals(date, epreuve.getDate()),
                () -> assertEquals(heure, epreuve.getHeure()),
                () -> assertEquals(90, epreuve.getDuree()),
                () -> assertEquals(Modalite.QCM, epreuve.getModalite())
        );
    }
}