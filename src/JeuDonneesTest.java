import etude.Epreuve;
import etude.Etudiant;
import fraude.Fraude;
import fraude.FraudeCalculatrice;
import fraude.FraudeIAG;
import fraude.FraudePapier;

import java.util.List;

public class JeuDonneesTest {

    public static GestionnaireFraudes creerDonneesTest() {
        GestionnaireFraudes gestionnaire = new GestionnaireFraudes();

        Etudiant etudiant1 = creerEtudiant("001", "Martin", "Lucas");
        Etudiant etudiant2 = creerEtudiant("002", "Bernard", "Emma");
        Etudiant etudiant3 = creerEtudiant("003", "Petit", "Hugo");
        Etudiant etudiant4 = creerEtudiant("004", "Durand", "Camille");
        Etudiant etudiant5 = creerEtudiant("005", "Martin", "Alice");

        Epreuve epreuveMaths = new Epreuve();
        Epreuve epreuveInfo = new Epreuve();

        Fraude fraudeIAG = new FraudeIAG();
        fraudeIAG.addEtudiant(etudiant1);
        fraudeIAG.addEtudiant(etudiant2);

        Fraude fraudePapier = new FraudePapier();
        fraudePapier.addEtudiant(etudiant3);

        Fraude fraudeCalculatrice = new FraudeCalculatrice();
        fraudeCalculatrice.addEtudiant(etudiant2);
        fraudeCalculatrice.addEtudiant(etudiant4);

        Fraude autreFraudeIAG = new FraudeIAG();
        autreFraudeIAG.addEtudiant(etudiant5);

        FormulaireFraude formulaire1 = new FormulaireFraude(List.of(fraudeIAG));
        formulaire1.setId(1);
        formulaire1.setEpreuve(epreuveMaths);

        FormulaireFraude formulaire2 = new FormulaireFraude(List.of(fraudePapier, fraudeCalculatrice));
        formulaire2.setId(2);
        formulaire2.setEpreuve(epreuveInfo);

        FormulaireFraude formulaire3 = new FormulaireFraude(List.of(autreFraudeIAG));
        formulaire3.setId(3);
        formulaire3.setEpreuve(epreuveMaths);

        gestionnaire.ajouterFormulaire(formulaire1);
        gestionnaire.ajouterFormulaire(formulaire2);
        gestionnaire.ajouterFormulaire(formulaire3);

        return gestionnaire;
    }

    private static Etudiant creerEtudiant(String numero, String nom, String prenom) {
        Etudiant etudiant = new Etudiant();

        etudiant.setNumeroApprenant(numero);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);

        return etudiant;
    }
    /** Généré par IA pour le test **/
    public static void main(String[] args) {
        GestionnaireFraudes gestionnaire = creerDonneesTest();

        System.out.println("===== DONNÉES DE TEST =====");

        System.out.println("\n--- Résumé général ---");
        System.out.println("Nombre de formulaires : " + gestionnaire.getFormulaires().size());
        System.out.println("Nombre d'étudiants : " + gestionnaire.getEtudiants().size());
        System.out.println("Nombre d'épreuves : " + gestionnaire.getEpreuves().size());

        System.out.println("\n--- Étudiants ---");
        for (Etudiant etudiant : gestionnaire.getEtudiants()) {
            System.out.println(
                    etudiant.getNumeroApprenant()
                            + " - "
                            + etudiant.getPrenom()
                            + " "
                            + etudiant.getNom()
            );
        }

        System.out.println("\n--- Formulaires ---");
        for (FormulaireFraude formulaire : gestionnaire.getFormulaires()) {
            System.out.println("\nFormulaire n°" + formulaire.getId());

            System.out.println("Épreuve : " + formulaire.getEpreuve());

            System.out.println("Étudiants concernés :");
            for (Etudiant etudiant : formulaire.getEtudiants()) {
                System.out.println(
                        "  - "
                                + etudiant.getNumeroApprenant()
                                + " : "
                                + etudiant.getPrenom()
                                + " "
                                + etudiant.getNom()
                );
            }

            System.out.println("Fraudes constatées :");
            for (Fraude fraude : formulaire.getFraudes()) {
                System.out.println("  - " + fraude.getClass().getSimpleName());
            }
        }

        StatistiquesFraudes statistiquesFraudes = new StatistiquesFraudes();
        System.out.println("\n--- Statistiques ---");
        System.out.println("Nombre total de formulaires : "
                + statistiquesFraudes.nombreTotalFormulaires(gestionnaire.getFormulaires()));

        System.out.println("Nombre total de fraudes : "
                + statistiquesFraudes.nombreTotalFraudes(gestionnaire.getFormulaires()));

        System.out.println("Nombre d'étudiants distincts : "
                + statistiquesFraudes.nombreEtudiantsDistincts(gestionnaire.getFormulaires()));

        System.out.println("Moyenne de fraudes par formulaire : "
                + statistiquesFraudes.moyenneFraudesParFormulaire(gestionnaire.getFormulaires()));

        System.out.println("Écart-type des fraudes par formulaire : "
                + statistiquesFraudes.ecartTypeFraudesParFormulaire(gestionnaire.getFormulaires()));
    }
}