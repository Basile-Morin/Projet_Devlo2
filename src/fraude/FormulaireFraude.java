package fraude;

import etude.Epreuve;
import etude.Etudiant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente un formulaire stockant les fraudes constatées
 * pendant une épreuve.
 */
public class FormulaireFraude {

    /** Identifiant unique du formulaire */
    private int id;

    /** Date de création du formulaire */
    private LocalDateTime dateCreation;

    /** Date de la dernière modification du formulaire */
    private LocalDateTime dateDerniereModification;

    /** Épreuve concernée par le formulaire */
    private Epreuve epreuve;

    /** Liste des étudiants qui sont concernés par le formulaire */
    private final List<Etudiant> etudiants;

    /** Liste des fraudes constatées pendant l'épreuve */
    private final List<Fraude> fraudes;

    /**
     * Construit un formulaire vide.
     * Les listes d'étudiants et de fraudes sont initialisées.
     * La date de création correspond à la date actuelle (au moment ou le formulaire est créé).
     */
    public FormulaireFraude() {
        etudiants = new ArrayList<>();
        fraudes = new ArrayList<>();

        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;
    }

    /**
     * Construit un formulaire à partir d'une liste de fraudes.
     *
     * @param fraudes liste des fraudes à ajouter au formulaire
     */
    public FormulaireFraude(List<Fraude> fraudes) {
        etudiants = new ArrayList<>();
        this.fraudes = new ArrayList<>();

        if (fraudes != null) {
            fraudes.forEach(this::ajouterFraudeConstatee);
        }

        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;
    }

    /**
     * Retire une fraude du formulaire.
     * On revérifie chaque étudiant pour vérifier s'il est toujours concerné par une fraude, sinon on le supprime
     * @param fraude fraude à retirer
     */
    public void retirerFraudeConstatee(Fraude fraude) {
        if (fraude == null) {
            return;
        }

        boolean fraudeRetiree = fraudes.remove(fraude);

        if (!fraudeRetiree) {
            return;
        }

        List<Etudiant> aSupprimer = new ArrayList<>(etudiants);

        fraudes.forEach(fraudeRestante -> {
            if (fraudeRestante.getEtudiants() != null) {
                aSupprimer.removeAll(fraudeRestante.getEtudiants());
            }
        });

        etudiants.removeAll(aSupprimer);
        dateDerniereModification = LocalDateTime.now();
    }

    /**
     * Ajoute une fraude au formulaire.
     * Les étudiants impliqués dans la fraude sont ajoutés au formulaire, en vérifiant les doublons
     *
     * @param fraude fraude à ajouter
     */
    public void ajouterFraudeConstatee(Fraude fraude) {
        if (fraude == null) {
            return;
        }

        fraudes.add(fraude);

        if (fraude.getEtudiants() != null) {
            fraude.getEtudiants().stream()
                    .filter(etudiant -> !etudiants.contains(etudiant))
                    .forEach(etudiants::add);
        }

        dateDerniereModification = LocalDateTime.now();
    }

    /**
     * Retourne une copie non modifiable de la liste de fraudes.
     *
     * @return liste des fraudes constatées
     */
    public List<Fraude> getFraudes() {
        return List.copyOf(fraudes);
    }

    /**
     * Retourne une copie non modifiable de la liste d'étudiants.
     *
     * @return liste des étudiants impliqués
     */
    public List<Etudiant> getEtudiants() {
        return List.copyOf(etudiants);
    }

    /**
     * Retourne l'épreuve.
     *
     * @return épreuve concernée
     */
    public Epreuve getEpreuve() {
        return epreuve;
    }

    /**
     * Modifie l'épreuve associée au formulaire.
     *
     * @param epreuve nouvelle épreuve
     */
    public void setEpreuve(Epreuve epreuve) {
        dateDerniereModification = LocalDateTime.now();
        this.epreuve = epreuve;
    }

    /**
     * Retourne la date de dernière modification.
     *
     * @return date de dernière modification
     */
    public LocalDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }

    /**
     * Modifie la date de la dernière modification.
     * La modification est ignorée si la date est nulle.
     *
     * @param dateDerniereModification nouvelle date de modification
     */
    public void setDateDerniereModification(LocalDateTime dateDerniereModification) {

        if (dateDerniereModification == null) {
            return;
        }

        this.dateDerniereModification = dateDerniereModification;
    }

    /**
     * Retourne la date de création du formulaire.
     *
     * @return date de création
     */
    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    /**
     * Modifie la date de création du formulaire.
     * La modification est ignorée si la date est nulle.
     *
     * @param dateCreation nouvelle date de création
     */
    public void setDateCreation(LocalDateTime dateCreation) {
        if (dateCreation == null) {
            return;
        }

        dateDerniereModification = LocalDateTime.now();
        this.dateCreation = dateCreation;
    }

    /**
     * Retourne l'identifiant du formulaire.
     *
     * @return identifiant du formulaire
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du formulaire.
     * !! à utiliser avec prudence car cela peut créer des formulaires avec des id identiques
     * @param id nouvel identifiant
     */
    public void setId(int id) {
        dateDerniereModification = LocalDateTime.now();
        this.id = id;
    }
}