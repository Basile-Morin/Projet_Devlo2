package etude;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EtudiantTest {

    private Etudiant creerEtudiant(String numero) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        return etudiant;
    }

    @Test
    void testGettersSetters() {
        Etudiant etudiant = new Etudiant();
        assertNull(etudiant.getNumeroApprenant());
        assertNull(etudiant.getNom());
        assertNull(etudiant.getPrenom());
        assertNull(etudiant.getCursus());

        etudiant.setNumeroApprenant("001");
        etudiant.setNom("MORIN");
        etudiant.setPrenom("Basile");
        etudiant.setCursus(Cursus.E3E);

        assertEquals("001", etudiant.getNumeroApprenant());
        assertEquals("MORIN", etudiant.getNom());
        assertEquals("Basile", etudiant.getPrenom());
        assertEquals(Cursus.E3E, etudiant.getCursus());
    }

    @Test
    void testEquals() {
        Etudiant etudiant = creerEtudiant("001");
        assertTrue(etudiant.equals(etudiant));
        assertTrue(etudiant.equals(creerEtudiant("001")));
        assertFalse(etudiant.equals(creerEtudiant("002")));
        assertFalse(etudiant.equals(null));
        assertFalse(etudiant.equals("001"));
    }

    @Test
    void testHashCode() {
        assertEquals(creerEtudiant("001").hashCode(), creerEtudiant("001").hashCode());
        assertEquals(0, new Etudiant().hashCode());
    }
}
