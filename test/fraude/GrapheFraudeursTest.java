package fraude;

import etude.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GrapheFraudeursTest {

    private GrapheFraudeurs graphe;

    @BeforeEach
    void setUp() {
        graphe = new GrapheFraudeurs();
    }

    private Etudiant creerEtudiant(String numero, String nom, String prenom) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNumeroApprenant(numero);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        return etudiant;
    }

    private FormulaireFraude creerFormulaire(Etudiant... etudiants) {
        FraudeIAG fraude = new FraudeIAG("ChatGPT");
        for (Etudiant etudiant : etudiants) {
            fraude.addEtudiant(etudiant);
        }
        FormulaireFraude formulaire = new FormulaireFraude();
        formulaire.ajouterFraudeConstatee(fraude);
        return formulaire;
    }

    @Test
    void testConstruireGraphe() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Etudiant etudiant3 = creerEtudiant("003", "MARTIN", "Paul");
        FormulaireFraude formulaire1 = creerFormulaire(etudiant1, etudiant2);
        FormulaireFraude formulaire2 = creerFormulaire(etudiant2, etudiant3);

        graphe.construireGraphe(List.of(formulaire1, formulaire2));

        assertEquals(Set.of(etudiant2), graphe.getAdjacences().get(etudiant1));
        assertEquals(Set.of(etudiant1, etudiant3), graphe.getAdjacences().get(etudiant2));
        assertEquals(Set.of(etudiant2), graphe.getAdjacences().get(etudiant3));

        graphe.construireGraphe(List.of(creerFormulaire(etudiant1)));
        assertEquals(1, graphe.getAdjacences().size());
        assertTrue(graphe.getAdjacences().get(etudiant1).isEmpty());
    }

    @Test
    void testAfficherGraphe() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Etudiant etudiant3 = creerEtudiant("003", "MARTIN", "Paul");
        graphe.construireGraphe(List.of(creerFormulaire(etudiant1, etudiant2), creerFormulaire(etudiant3)));
        PrintStream sortieInitiale = System.out;
        ByteArrayOutputStream sortie = new ByteArrayOutputStream();

        System.setOut(new PrintStream(sortie));
        try {
            graphe.afficherGraphe();
        } finally {
            System.setOut(sortieInitiale);
        }

        String texte = sortie.toString();
        assertTrue(texte.contains("GRAPHE DES FRAUDEURS"));
        assertTrue(texte.contains("001 - Basile MORIN"));
        assertTrue(texte.contains("002 - Alice DUPONT"));
        assertTrue(texte.contains("003 - Paul MARTIN"));
        assertTrue(texte.contains("aucun lien"));
    }

    @Test
    void testGetters() {
        Etudiant etudiant1 = creerEtudiant("001", "MORIN", "Basile");
        Etudiant etudiant2 = creerEtudiant("002", "DUPONT", "Alice");
        Etudiant etudiant3 = creerEtudiant("003", "MARTIN", "Paul");
        graphe.construireGraphe(List.of(creerFormulaire(etudiant1, etudiant2), creerFormulaire(etudiant2, etudiant3)));

        assertEquals(3, graphe.getAdjacences().size());
        assertEquals(Set.of(etudiant2), graphe.getAdjacences().get(etudiant1));
        assertEquals(Set.of(etudiant1, etudiant3), graphe.getAdjacences().get(etudiant2));
        assertEquals(Set.of(etudiant2), graphe.getAdjacences().get(etudiant3));
    }
}
