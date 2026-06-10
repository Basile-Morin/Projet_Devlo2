package etude;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EpreuveTest {

    @Test
    void testGettersSetters() {
        Epreuve epreuve = new Epreuve();
        assertNull(epreuve.getCodeECUE());
        assertNull(epreuve.getDate());
        assertNull(epreuve.getHeure());
        assertEquals(0, epreuve.getDuree());
        assertNull(epreuve.getModalite());

        LocalDate date = LocalDate.of(2026, 6, 9);
        LocalTime heure = LocalTime.of(8, 30);
        epreuve.setCodeECUE("INFO");
        epreuve.setDate(date);
        epreuve.setHeure(heure);
        epreuve.setDuree(90);
        epreuve.setModalite(Modalite.QCM);

        assertEquals("INFO", epreuve.getCodeECUE());
        assertEquals(date, epreuve.getDate());
        assertEquals(heure, epreuve.getHeure());
        assertEquals(90, epreuve.getDuree());
        assertEquals(Modalite.QCM, epreuve.getModalite());
    }
}
