package etude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CursusTest {

    @Test
    void testValues() {
        Cursus[] cursusAttendus = {Cursus.E1, Cursus.E2, Cursus.E3E, Cursus.E3A, Cursus.E4, Cursus.E5};
        assertArrayEquals(cursusAttendus, Cursus.values());
    }

    @Test
    void testValueOf() {
        assertEquals(Cursus.E3E, Cursus.valueOf("E3E"));
        assertThrows(IllegalArgumentException.class, () -> Cursus.valueOf("E6"));
    }
}
