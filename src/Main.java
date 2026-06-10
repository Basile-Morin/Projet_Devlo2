import fraude.GestionnaireFraudes;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionnaireFraudes gestionnaireFraudes;

        System.out.println("Voulez-vous utiliser les données de test ?");
        System.out.println("1. Oui");
        System.out.println("2. Non");

        System.out.print("Votre choix : ");
        int choix = 0;
        try {
            choix = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Erreur de saisie, application lancée sans les données de test");
        }

        if (choix == 1) {
            gestionnaireFraudes = JeuDonneesExemple.creerDonneesTest();
            System.out.println("Données de test chargées.");
        } else {
            gestionnaireFraudes = new GestionnaireFraudes();
            System.out.println("Application lancée sans données de test.");
        }

        MenuConsole menuConsole = new MenuConsole(gestionnaireFraudes);
        menuConsole.lancer();
    }
}