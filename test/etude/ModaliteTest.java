package etude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModaliteTest {

    @Test
    void testValues() {
        Modalite[] modalitesAttendues = {Modalite.EXAMEN_ECRIT, Modalite.ORAL, Modalite.QCM, Modalite.SUR_ORDINATEUR, Modalite.PROJET, Modalite.TP};
        assertArrayEquals(modalitesAttendues, Modalite.values());
    }

    @Test
    void testValueOf() {
        assertEquals(Modalite.QCM, Modalite.valueOf("QCM"));
        assertThrows(IllegalArgumentException.class, () -> Modalite.valueOf("STAGE"));
    }
}
