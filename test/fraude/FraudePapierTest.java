package fraude;

import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FraudePapierTest {

    @Test
    void testConstructeursGettersSetters() {
        FraudePapier vide = new FraudePapier();
        assertNull(vide.getDimension());
        assertFalse(vide.isPlie());

        Point dimension = new Point(10, 20);
        FraudePapier fraude = new FraudePapier(true, dimension);
        assertTrue(fraude.isPlie());
        assertEquals(dimension, fraude.getDimension());

        Point nouvelleDimension = new Point(5, 8);
        fraude.setDimension(nouvelleDimension);
        fraude.setPlie(false);
        assertEquals(nouvelleDimension, fraude.getDimension());
        assertFalse(fraude.isPlie());
    }
}
