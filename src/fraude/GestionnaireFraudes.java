package fraude;

import etude.Epreuve;
import etude.Etudiant;

import java.util.ArrayList;
import java.util.List;

/**
 * Gère l'ensemble des formulaires de fraude.
 *
 * Le gestionnaire permet d'ajouter, supprimer et rechercher des formulaires.
 * Elle conserve également la liste des étudiants et des épreuves associés aux formualires.
 */
public class GestionnaireFraudes {

    /** Liste des formulaires de fraude enregistrés. */
    private List<FormulaireFraude> formulaires = new ArrayList<>();

    /** Liste des étudiants présents dans au moins un formulaire. */
    private List<Etudiant> etudiants = new ArrayList<>();

    /** Liste des épreuves présentes dans au moins un formulaire. */
    private List<Epreuve> epreuves = new ArrayList<>();

    /** Identifiant à attribuer au prochain formulaire ajouté. */
    private int currentId = 0;

    /**
     * Ajoute un formulaire au gestionnaire.
     *
     * L'épreuve et les étudiants du formulaire sont également ajoutés
     * aux listes du gestionnaire s'ils ne sont pas déjà présents.
     * Un identifiant unique est ensuite attribué au formulaire.
     *
     * @param formulaire formulaire de fraude à ajouter
     */
    public void ajouterFormulaire(FormulaireFraude formulaire) {
        this.formulaires.add(formulaire);

        if (!epreuves.contains(formulaire.getEpreuve())) {
            epreuves.add(formulaire.getEpreuve());
        }

        for (Etudiant etudiant : formulaire.getEtudiants()) {
            if (!etudiants.contains(etudiant)) {
                etudiants.add(etudiant);
            }
        }

        formulaire.setId(currentId++);
    }

    /**
     * Supprime un formulaire à partir de son identifiant.
     *
     * Après la suppression, les listes des étudiants et des épreuves
     * sont reconstruites afin de retirer les éléments qui ne sont plus
     * utilisés dans aucun formulaire.
     *
     * @param id identifiant du formulaire à supprimer
     * @return true si le formulaire a été trouvé et supprimé,
     *         false sinon
     */
    public boolean supprimerFormulaire(int id) {
        for (int i = 0; i < formulaires.size(); i++) {
            if (formulaires.get(i).getId() == id) {
                formulaires.remove(i);
                reconstruireEtudiantsEtEpreuves();
                return true;
            }
        }

        return false;
    }

    /**
     * Recherche tous les formulaires associés à un étudiant.
     *
     * @param etudiant étudiant recherché
     * @return liste des formulaires contenant cet étudiant
     */
    public List<FormulaireFraude> rechercherFormulairesParEtudiants(
            Etudiant etudiant) {

        List<FormulaireFraude> resultat = new ArrayList<>();

        for (FormulaireFraude formulaire : this.formulaires) {
            if (formulaire.getEtudiants().contains(etudiant)) {
                resultat.add(formulaire);
            }
        }

        return resultat;
    }

    /**
     * Recherche tous les formulaires associés à une épreuve.
     *
     * @param epreuve épreuve recherchée
     * @return liste des formulaires correspondant à cette épreuve
     */
    public List<FormulaireFraude> rechercherFormulaireParEpreuve(
            Epreuve epreuve) {

        List<FormulaireFraude> resultat = new ArrayList<>();

        for (FormulaireFraude formulaire : this.formulaires) {
            if (formulaire.getEpreuve().equals(epreuve)) {
                resultat.add(formulaire);
            }
        }

        return resultat;
    }

    /**
     * Recherche un étudiant à partir de son numéro d'apprenant.
     *
     * @param numero numéro d'apprenant recherché
     * @return liste contenant l'étudiant correspondant,
     *         ou une liste vide si aucun étudiant n'est trouvé
     */
    public List<Etudiant> rechercherEtudiantsParNumero(String numero) {
        List<Etudiant> resultat = new ArrayList<>();

        for (Etudiant etudiant : this.etudiants) {
            if (etudiant.getNumeroApprenant().equals(numero)) {
                resultat.add(etudiant);
            }
        }

        return resultat;
    }

    /**
     * Recherche les étudiants à partir de leur nom.
     *
     * @param nom nom recherché
     * @return liste des étudiants possédant ce nom
     */
    public List<Etudiant> rechercherEtudiantsParNom(String nom) {
        List<Etudiant> resultat = new ArrayList<>();

        for (Etudiant etudiant : this.etudiants) {
            if (etudiant.getNom().equals(nom)) {
                resultat.add(etudiant);
            }
        }

        return resultat;
    }

    /**
     * Recherche les étudiants à partir de leur prénom.
     *
     * @param prenom prénom recherché
     * @return liste des étudiants possédant ce prénom
     */
    public List<Etudiant> rechercherEtudiantsParPrenom(String prenom) {
        List<Etudiant> resultat = new ArrayList<>();

        for (Etudiant etudiant : this.etudiants) {
            if (etudiant.getPrenom().equals(prenom)) {
                resultat.add(etudiant);
            }
        }

        return resultat;
    }

    /**
     * Retourne la liste des formulaires enregistrés.
     *
     * @return liste des formulaires
     */
    public List<FormulaireFraude> getFormulaires() {
        return formulaires;
    }

    /**
     * Retourne la liste des étudiants présents dans les formulaires.
     *
     * @return liste des étudiants
     */
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    /**
     * Retourne la liste des épreuves présentes dans les formulaires.
     *
     * @return liste des épreuves
     */
    public List<Epreuve> getEpreuves() {
        return epreuves;
    }

    /**
     * Reconstruit les listes des étudiants et des épreuves.
     *
     * Les listes sont d'abord vidées, puis remplies à partir des
     * formulaires encore enregistrés. Cette méthode évite de conserver
     * un étudiant ou une épreuve qui n'est plus associé à aucun formulaire.
     */
    public void reconstruireEtudiantsEtEpreuves() {
        etudiants.clear();
        epreuves.clear();

        for (FormulaireFraude formulaire : formulaires) {
            if (!epreuves.contains(formulaire.getEpreuve())) {
                epreuves.add(formulaire.getEpreuve());
            }

            for (Etudiant etudiant : formulaire.getEtudiants()) {
                if (!etudiants.contains(etudiant)) {
                    etudiants.add(etudiant);
                }
            }
        }
    }
}