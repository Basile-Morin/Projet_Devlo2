package fraude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FraudeIAGConnecteeTest {

    @Test
    void testConstructeursGettersSetters() {
        FraudeIAGConnectee vide = new FraudeIAGConnectee();
        assertNull(vide.getAdresseIP());

        FraudeIAGConnectee fraude = new FraudeIAGConnectee("ChatGPT", "192.168.1.1");
        assertEquals("ChatGPT", fraude.getNomService());
        assertEquals("192.168.1.1", fraude.getAdresseIP());

        fraude.setAdresseIP("10.0.0.1");
        assertEquals("10.0.0.1", fraude.getAdresseIP());
    }
}
