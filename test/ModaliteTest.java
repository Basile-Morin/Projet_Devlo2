package etude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModaliteTest {

    @Test
    void valeurs_contientExamenEcrit() {
        assertNotNull(Modalite.valueOf("EXAMEN_ECRIT"));
    }

    @Test
    void valeurs_contientOral() {
        assertNotNull(Modalite.valueOf("ORAL"));
    }

    @Test
    void valeurs_contientQCM() {
        assertNotNull(Modalite.valueOf("QCM"));
    }

    @Test
    void valeurs_contientSurOrdinateur() {
        assertNotNull(Modalite.valueOf("SUR_ORDINATEUR"));
    }

    @Test
    void valeurs_contientProjet() {
        assertNotNull(Modalite.valueOf("PROJET"));
    }

    @Test
    void valeurs_contientTP() {
        assertNotNull(Modalite.valueOf("TP"));
    }

    @Test
    void valeurs_nombreTotal_six() {
        assertEquals(6, Modalite.values().length);
    }

    @Test
    void valueOf_valeurInconnue_leveException() {
        assertThrows(IllegalArgumentException.class, () -> Modalite.valueOf("STAGE"));
    }
}
