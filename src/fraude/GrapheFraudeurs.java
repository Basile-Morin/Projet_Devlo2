package fraude;

import etude.Etudiant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrapheFraudeurs {

    private final Map<Etudiant, Set<Etudiant>> adjacences = new HashMap<>(); //Set pour éviter les doublons

    public void construireGraphe(List<FormulaireFraude> formulaires) {
        adjacences.clear();

        for (FormulaireFraude formulaire : formulaires) {
            for (Fraude fraude : formulaire.getFraudes()) {
                ajouterLiensEntreEtudiants(fraude.getEtudiants());
            }
        }
    }

    private void ajouterLiensEntreEtudiants(List<Etudiant> etudiants) {
        for (Etudiant etudiant : etudiants) {
            adjacences.putIfAbsent(etudiant, new HashSet<>());
        }

        for (Etudiant etudiant1 : etudiants) {
            for (Etudiant etudiant2 : etudiants) {
                if (!etudiant1.equals(etudiant2)) {
                    Set<Etudiant> set =adjacences.get(etudiant1);
                    set.add(etudiant2);
                }
            }
        }
    }

    public void afficherGraphe() {
        System.out.println("===== GRAPHE DES FRAUDEURS =====");

        for (Etudiant etudiant : adjacences.keySet()) {
            System.out.print(afficherEtudiant(etudiant) + " -> ");

            Set<Etudiant> voisins = adjacences.get(etudiant);

            if (voisins.isEmpty()) {
                System.out.println("aucun lien");
            } else {
                for (Etudiant voisin : voisins) {
                    System.out.print(afficherEtudiant(voisin) + " ; ");
                }
                System.out.println();
            }
        }
    }

    private String afficherEtudiant(Etudiant etudiant) {
        return etudiant.getNumeroApprenant()
                + " - "
                + etudiant.getPrenom()
                + " "
                + etudiant.getNom();
    }

    public Map<Etudiant, Set<Etudiant>> getAdjacences() {
        return adjacences;
    }
}