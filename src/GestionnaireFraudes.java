import etude.Epreuve;
import etude.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireFraudes {
    private List<FormulaireFraude> formulaires =  new ArrayList<>();
    private List<Etudiant> etudiants = new ArrayList<>();
    private List<Epreuve> epreuves = new ArrayList<>();
    private int currentId=0;


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

    public List<FormulaireFraude> rechercherFormulairesParEtudiants(Etudiant etudiant) {
        List<FormulaireFraude> resultat = new ArrayList<>();
        for(FormulaireFraude formulaire: this.formulaires){
            if(formulaire.getEtudiants().contains(etudiant)){
                resultat.add(formulaire);
            }
        }
        return resultat;
    }


    public List<FormulaireFraude> rechercherFormulaireParEpreuve(Epreuve preuve) {
        List<FormulaireFraude> resultat = new ArrayList<>();
        for(FormulaireFraude formulaire: this.formulaires){
            if(formulaire.getEpreuve().equals(preuve)){
                resultat.add(formulaire);
            }
        }
        return resultat;
    }


    public List<Etudiant> rechercherEtudiantsParNumero(String numero) {
        for(Etudiant etudiant: this.etudiants){
            if(etudiant.getNumeroApprenant().equals(numero)){
                return etudiants;
            }
        }
        return null;
    }


    public List<Etudiant> rechercherEtudiantsParNom(String nom) {
        List<Etudiant> resultat = new ArrayList<>();
        for(Etudiant etudiant: this.etudiants){
            if(etudiant.getNom().equals(nom)){
                resultat.add(etudiant);
            }
        }
        return resultat;
    }


    public List<Etudiant> rechercherEtudiantsParPrenom(String prenom) {
        List<Etudiant> resultat = new ArrayList<>();
        for(Etudiant etudiant: this.etudiants){
            if(etudiant.getPrenom().equals(prenom)){
                resultat.add(etudiant);
            }
        }
        return resultat;
    }

    public List<FormulaireFraude> getFormulaires() {
        return formulaires;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public List<Epreuve> getEpreuves() {
        return epreuves;
    }

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
