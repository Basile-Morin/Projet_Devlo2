import etude.Cursus;
import etude.Epreuve;
import etude.Etudiant;
import etude.Modalite;
import fraude.Fraude;
import fraude.FraudeCalculatrice;
import fraude.FraudeIAG;
import fraude.FraudeIAGConnectee;
import fraude.FraudePapier;

import java.awt.Point;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MenuConsole {
    private final GestionnaireFraudes gestionnaire;
    private final Scanner scanner;
    private final StatistiquesFraudes statistiquesFraudes = new StatistiquesFraudes();

    public MenuConsole(GestionnaireFraudes gestionnaire) {
        this.gestionnaire = gestionnaire;
        this.scanner = new Scanner(System.in);
    }

    // =========================================================
    // MENU
    // =========================================================

    public void lancer() {
        int choix = 1;

        while (choix != 0) {
            afficherMenu();
            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    afficherFormulaires();
                    break;
                case 2:
                    ajouterFormulaire();
                    break;
                case 3:
                    supprimerFormulaire();
                    break;
                case 4:
                    afficherRecherches();
                    break;
                case 5:
                    afficherStatistiques();
                    break;
                case 6:
                    afficherGraphe();
                    break;
                case 7:
                    afficherEtudiants();
                    break;
                case 8:
                    afficherEpreuves();
                    break;
                case 9:
                    ajouterFraudeAFormulaire();
                    break;
                case 0:
                    System.out.println("Fin du programme.");
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }

            System.out.println();
        }
    }

    public void afficherMenu() {
        System.out.println("===== MENU CONSOLE =====");
        System.out.println("1. Afficher les formulaires");
        System.out.println("2. Ajouter un formulaire");
        System.out.println("3. Supprimer un formulaire");
        System.out.println("4. Afficher les recherches");
        System.out.println("5. Afficher les statistiques");
        System.out.println("6. Afficher le graphe");
        System.out.println("7. Afficher les étudiants existants");
        System.out.println("8. Afficher les épreuves existantes");
        System.out.println("9. Créer une fraude et l'ajouter à un formulaire");
        System.out.println("0. Quitter");
    }

    // =========================================================
    // OPTION 1 : AFFICHER LES FORMULAIRES
    // =========================================================

    public void afficherFormulaires() {
        System.out.println("===== FORMULAIRES =====");

        for (FormulaireFraude formulaire : gestionnaire.getFormulaires()) {
            System.out.println("\nFormulaire n°" + formulaire.getId());

            System.out.print("Épreuve : ");
            afficherEpreuve(formulaire.getEpreuve());

            System.out.println("Étudiants concernés :");
            for (Etudiant etudiant : formulaire.getEtudiants()) {
                afficherEtudiant(etudiant);
            }

            System.out.println("Fraudes constatées :");
            for (Fraude fraude : formulaire.getFraudes()) {
                afficherFraude(fraude);
            }
        }
    }

    // =========================================================
    // OPTION 2 : AJOUTER UN FORMULAIRE
    // =========================================================

    public void ajouterFormulaire() {
        FormulaireFraude formulaire = new FormulaireFraude();

        Epreuve epreuve = choisirOuCreerEpreuve();

        if (epreuve == null) {
            System.out.println("Le formulaire n'a pas été créé.");
            return;
        }

        formulaire.setEpreuve(epreuve);
        gestionnaire.ajouterFormulaire(formulaire);

        System.out.println("Formulaire n°" + formulaire.getId() + " ajouté.");
    }


    private Epreuve choisirOuCreerEpreuve() {
        System.out.println("===== CHOIX DE L'ÉPREUVE =====");
        System.out.println("1. Choisir une épreuve existante");
        System.out.println("2. Créer une nouvelle épreuve");

        int choix = lireEntier("Votre choix : ");

        if (choix == 1 && !gestionnaire.getEpreuves().isEmpty()) {
            return choisirEpreuveExistante();
        }

        return creerEpreuve();
    }

    private Epreuve choisirEpreuveExistante() {
        List<Epreuve> epreuves = gestionnaire.getEpreuves();

        for (int i = 0; i < epreuves.size(); i++) {
            System.out.print(i + ". ");
            afficherEpreuve(epreuves.get(i));
        }

        int index = lireEntier("Numéro de l'épreuve choisie : ");
        return epreuves.get(index);
    }

    private Epreuve creerEpreuve() {
        Epreuve epreuve = new Epreuve();

        try {
            String codeECUE = lireTexte("Code ECUE : ");
            String date = lireTexte("Date de l'épreuve (AAAA-MM-JJ) : ");
            String heure = lireTexte("Heure de l'épreuve (HH:MM) : ");
            int duree = lireEntier("Durée en minutes : ");
            Modalite modalite = choisirModalite();

            epreuve.setCodeECUE(codeECUE);
            epreuve.setDate(LocalDate.parse(date));
            epreuve.setHeure(LocalTime.parse(heure));
            epreuve.setDuree(duree);
            epreuve.setModalite(modalite);

            return epreuve;

        } catch (Exception e) {
            System.out.println("Erreur dans la création de l'épreuve.");
            return null;
        }
    }

    private Modalite choisirModalite() {
        System.out.println("===== MODALITÉ =====");

        Modalite[] modalites = Modalite.values();

        for (int i = 0; i < modalites.length; i++) {
            System.out.println(i + ". " + modalites[i]);
        }

        int index = lireEntier("Modalité choisie : ");
        return modalites[index];
    }

    // =========================================================
    // OPTION 3 : SUPPRIMER UN FORMULAIRE
    // =========================================================

    public void supprimerFormulaire() {
        int id = lireEntier("Id du formulaire à supprimer : ");

        boolean supprime = gestionnaire.supprimerFormulaire(id);

        if (supprime) {
            System.out.println("Formulaire n°" + id + " supprimé.");
        } else {
            System.out.println("Aucun formulaire avec l'id " + id + " trouvé.");
        }
    }

    // =========================================================
    // OPTION 4 : AFFICHER LES RECHERCHES
    // =========================================================

    public void afficherRecherches() {
        System.out.println("===== RECHERCHES =====");
        System.out.println("1. Rechercher des étudiants par nom");
        System.out.println("2. Rechercher des étudiants par prénom");

        int choix = lireEntier("Votre choix : ");

        switch (choix) {
            case 1:
                rechercherParNom();
                break;
            case 2:
                rechercherParPrenom();
                break;
            default:
                System.out.println("Choix invalide.");
                break;
        }
    }

    private void rechercherParNom() {
        String nom = lireTexte("Nom recherché : ");

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParNom(nom);

        System.out.println("Résultat(s) :");
        for (Etudiant etudiant : resultats) {
            afficherEtudiant(etudiant);
        }
    }

    private void rechercherParPrenom() {
        String prenom = lireTexte("Prénom recherché : ");

        List<Etudiant> resultats = gestionnaire.rechercherEtudiantsParPrenom(prenom);

        System.out.println("Résultat(s) :");
        for (Etudiant etudiant : resultats) {
            afficherEtudiant(etudiant);
        }
    }

    // =========================================================
    // OPTION 5 : AFFICHER LES STATISTIQUES
    // =========================================================

    public void afficherStatistiques() {
        System.out.println("===== STATISTIQUES =====");

        System.out.println("Nombre total de formulaires : "
                + statistiquesFraudes.nombreTotalFormulaires(gestionnaire.getFormulaires()));

        System.out.println("Nombre total de fraudes : "
                + statistiquesFraudes.nombreTotalFraudes(gestionnaire.getFormulaires()));

        System.out.println("Nombre d'étudiants distincts : "
                + statistiquesFraudes.nombreEtudiantsDistincts(gestionnaire.getFormulaires()));

        System.out.printf("Moyenne de fraudes par formulaire : %.2f \n",
                statistiquesFraudes.moyenneFraudesParFormulaire(gestionnaire.getFormulaires()));

        System.out.printf("Écart-type des fraudes par formulaire : %.2f \n",
                statistiquesFraudes.ecartTypeFraudesParFormulaire(gestionnaire.getFormulaires()));
    }

    // =========================================================
    // OPTION 6 : AFFICHER LE GRAPHE
    // =========================================================

    public void afficherGraphe() {
        GrapheFraudeurs graphe = new GrapheFraudeurs();

        graphe.construireGraphe(gestionnaire.getFormulaires());
        graphe.afficherGraphe();
    }

    // =========================================================
    // OPTION 7 : AFFICHER LES ÉTUDIANTS
    // =========================================================

    public void afficherEtudiants() {
        System.out.println("===== ÉTUDIANTS EXISTANTS =====");

        for (Etudiant etudiant : gestionnaire.getEtudiants()) {
            afficherEtudiant(etudiant);
        }
    }

    // =========================================================
    // OPTION 8 : AFFICHER LES ÉPREUVES
    // =========================================================

    public void afficherEpreuves() {
        System.out.println("===== ÉPREUVES EXISTANTES =====");

        for (Epreuve epreuve : gestionnaire.getEpreuves()) {
            afficherEpreuve(epreuve);
        }
    }

    // =========================================================
    // OPTION 9 : CRÉER UNE FRAUDE ET L'AJOUTER À UN FORMULAIRE
    // =========================================================

    public void ajouterFraudeAFormulaire() {
        FormulaireFraude formulaire = choisirOuCreerFormulaire();
        Fraude fraude = creerFraude();

        ajouterEtudiantsDansFraude(fraude);

        formulaire.ajouterFraudeConstatee(fraude);
        gestionnaire.reconstruireEtudiantsEtEpreuves();

        System.out.println("Fraude ajoutée au formulaire n°" + formulaire.getId() + ".");
    }

    private FormulaireFraude choisirOuCreerFormulaire() {
        System.out.println("===== CHOIX DU FORMULAIRE =====");
        System.out.println("1. Choisir un formulaire existant");
        System.out.println("2. Créer un nouveau formulaire");

        int choix = lireEntier("Votre choix : ");

        boolean choixFormulaire=choix == 1 && !gestionnaire.getFormulaires().isEmpty();
        if (choixFormulaire) {  //Choix d'un formulaire
            afficherFormulaires();

            int id = lireEntier("Id du formulaire choisi : ");

            for (FormulaireFraude formulaire : gestionnaire.getFormulaires()) {
                if (formulaire.getId() == id) {
                    return formulaire;
                }
            }

            System.out.println("Id introuvable, création d'un nouveau formulaire.");
        }

        //Création d'un formualire (on rentre dedans si on a saisi une mauvaise id dans le choix du formulaire)
        FormulaireFraude formulaire = new FormulaireFraude();

        Epreuve epreuve = choisirOuCreerEpreuve();
        formulaire.setEpreuve(epreuve);

        gestionnaire.ajouterFormulaire(formulaire);

        return formulaire;
    }


    private Fraude creerFraude() {
        System.out.println("===== TYPE DE FRAUDE =====");
        System.out.println("1. Fraude IAG");
        System.out.println("2. Fraude IAG connectée");
        System.out.println("3. Fraude papier");
        System.out.println("4. Fraude calculatrice");

        int choix = lireEntier("Votre choix : ");

        Fraude fraude;

        switch (choix) {
            case 1:
                fraude = creerFraudeIAG();
                break;
            case 2:
                fraude = creerFraudeIAGConnectee();
                break;
            case 3:
                fraude = creerFraudePapier();
                break;
            case 4:
                fraude = creerFraudeCalculatrice();
                break;
            default:
                System.out.println("Choix invalide, fraude IAG créée par défaut.");
                fraude = new FraudeIAG();
                break;
        }

        fraude.setDescription(lireTexte("Description de la fraude : "));
        fraude.setContenu(lireTexte("Contenu de la fraude : "));

        return fraude;
    }

    private FraudeIAG creerFraudeIAG() {
        FraudeIAG fraudeIAG = new FraudeIAG();
        fraudeIAG.setNomService(lireTexte("Nom du service IAG : "));
        return fraudeIAG;
    }

    private FraudeIAGConnectee creerFraudeIAGConnectee() {
        FraudeIAGConnectee fraudeIAGConnectee = new FraudeIAGConnectee();
        fraudeIAGConnectee.setNomService(lireTexte("Nom du service IAG : "));
        fraudeIAGConnectee.setAdresseIP(lireTexte("Adresse IP : "));
        return fraudeIAGConnectee;
    }

    private FraudePapier creerFraudePapier() {
        FraudePapier fraudePapier = new FraudePapier();

        int largeur = lireEntier("Largeur du papier : ");
        int hauteur = lireEntier("Hauteur du papier : ");

        fraudePapier.setDimension(new Point(largeur, hauteur));
        fraudePapier.setPlie(lireBooleen("Papier plié ?"));

        return fraudePapier;
    }

    private FraudeCalculatrice creerFraudeCalculatrice() {
        FraudeCalculatrice fraudeCalculatrice = new FraudeCalculatrice();

        fraudeCalculatrice.setMarque(lireTexte("Marque de la calculatrice : "));
        fraudeCalculatrice.setProgrammeStocke(lireTexte("Programme stocké : "));

        return fraudeCalculatrice;
    }


    private void ajouterEtudiantsDansFraude(Fraude fraude) {
        int choix = 1;

        while (choix != 0) {
            System.out.println("===== AJOUT D'ÉTUDIANTS À LA FRAUDE =====");
            System.out.println("1. Choisir un étudiant existant");
            System.out.println("2. Créer un nouvel étudiant");
            System.out.println("0. Terminer");

            choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    if (gestionnaire.getEtudiants().isEmpty()) {
                        System.out.println("Aucun étudiant existant.");
                    } else { //Choix d'un étudiant
                        List<Etudiant> etudiants = gestionnaire.getEtudiants();

                        for (int i = 0; i < etudiants.size(); i++) {
                            System.out.print(i + ". ");
                            afficherEtudiant(etudiants.get(i));
                        }

                        int index = lireEntier("Numéro de l'étudiant choisi : ");
                        fraude.addEtudiant(etudiants.get(index));
                    }
                    break;

                case 2:
                    fraude.addEtudiant(creerEtudiant());
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }


    private Etudiant creerEtudiant() {
        Etudiant etudiant = new Etudiant();

        etudiant.setNumeroApprenant(lireTexte("Numéro apprenant : "));
        etudiant.setNom(lireTexte("Nom : "));
        etudiant.setPrenom(lireTexte("Prénom : "));
        etudiant.setCursus(choisirCursus());

        return etudiant;
    }

    private Cursus choisirCursus() {
        System.out.println("===== CURSUS =====");

        Cursus[] cursus = Cursus.values();

        for (int i = 0; i < cursus.length; i++) {
            System.out.println(i + ". " + cursus[i]);
        }

        int index = lireEntier("Cursus choisi : ");
        return cursus[index];
    }

    // =========================================================
    // MÉTHODES D'AFFICHAGE COMMUNES
    // =========================================================

    private void afficherEtudiant(Etudiant etudiant) {
        System.out.println(" - "
                + etudiant.getNumeroApprenant()
                + " : "
                + etudiant.getPrenom()
                + " "
                + etudiant.getNom()
                + " | "
                + etudiant.getCursus());
    }

    private void afficherEpreuve(Epreuve epreuve) {
        System.out.println(" - Code ECUE : " + epreuve.getCodeECUE()
                + " | Date : " + epreuve.getDate()
                + " | Heure : " + epreuve.getHeure()
                + " | Durée : " + epreuve.getDuree()
                + " min"
                + " | Modalité : " + epreuve.getModalite());
    }

    private void afficherFraude(Fraude fraude) {
        System.out.println(" - " + fraude.getClass().getSimpleName()
                + " | Description : " + fraude.getDescription()
                + " | Contenu : " + fraude.getContenu());
    }

    // =========================================================
    // MÉTHODES DE LECTURE AU CLAVIER
    // =========================================================

    private int lireEntier(String message) {
        System.out.print(message);
        int valeur = scanner.nextInt();
        scanner.nextLine();
        return valeur;
    }

    private String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private boolean lireBooleen(String message) {
        System.out.print(message + " (true/false) : ");
        boolean valeur = scanner.nextBoolean();
        scanner.nextLine();
        return valeur;
    }

    // =========================================================
    // MAIN DE TEST
    // =========================================================

    public static void main(String[] args) {
        GestionnaireFraudes gestionnaire = JeuDonneesTest.creerDonneesTest();
        MenuConsole menu = new MenuConsole(gestionnaire);
        menu.lancer();
    }
}