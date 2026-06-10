package fraude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FraudeCalculatriceTest {

    @Test
    void testConstructeursGettersSetters() {
        FraudeCalculatrice vide = new FraudeCalculatrice();
        assertNull(vide.getMarque());
        assertNull(vide.getProgrammeStocke());

        FraudeCalculatrice fraude = new FraudeCalculatrice("Casio", "programme");
        assertEquals("Casio", fraude.getMarque());
        assertEquals("programme", fraude.getProgrammeStocke());

        fraude.setMarque("Texas Instruments");
        fraude.setProgrammeStocke("antisèche");
        assertEquals("Texas Instruments", fraude.getMarque());
        assertEquals("antisèche", fraude.getProgrammeStocke());
    }
}
