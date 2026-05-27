package etude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CursusTest {

    @Test
    void valeurs_contientE1() {
        assertNotNull(Cursus.valueOf("E1"));
    }

    @Test
    void valeurs_contientE2() {
        assertNotNull(Cursus.valueOf("E2"));
    }

    @Test
    void valeurs_contientE3E() {
        assertNotNull(Cursus.valueOf("E3E"));
    }

    @Test
    void valeurs_contientE3A() {
        assertNotNull(Cursus.valueOf("E3A"));
    }

    @Test
    void valeurs_contientE4() {
        assertNotNull(Cursus.valueOf("E4"));
    }

    @Test
    void valeurs_contientE5() {
        assertNotNull(Cursus.valueOf("E5"));
    }

    @Test
    void valeurs_nombreTotal_six() {
        assertEquals(6, Cursus.values().length);
    }

    @Test
    void ordinal_E1_estZero() {
        assertEquals(0, Cursus.E1.ordinal());
    }

    @Test
    void ordinal_E5_estCinq() {
        assertEquals(5, Cursus.E5.ordinal());
    }

    @Test
    void valueOf_valeurInconnue_leveException() {
        assertThrows(IllegalArgumentException.class, () -> Cursus.valueOf("E6"));
    }
}
