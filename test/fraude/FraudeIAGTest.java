package fraude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FraudeIAGTest {

    @Test
    void testConstructeursGettersSetters() {
        FraudeIAG vide = new FraudeIAG();
        assertNull(vide.getNomService());

        FraudeIAG fraude = new FraudeIAG("ChatGPT");
        assertEquals("ChatGPT", fraude.getNomService());

        fraude.setNomService("Gemini");
        assertEquals("Gemini", fraude.getNomService());
    }
}
