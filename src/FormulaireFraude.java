import etude.Epreuve;
import etude.Etudiant;
import fraude.Fraude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FormulaireFraude {
    private int id;
    private LocalDateTime dateCreation ;
    private LocalDateTime dateDerniereModification;
    private Epreuve epreuve;
    private List<Etudiant> etudiants;
    private List<Fraude> fraudes;

    public FormulaireFraude() {
        etudiants = new ArrayList<>();
        fraudes = new ArrayList<>();
        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;
    }

    public FormulaireFraude(List<Fraude> fraudes) {
        etudiants = new ArrayList<>();
        this.fraudes = new ArrayList<>();
        fraudes.forEach(this::ajouterFraudeConstatee);

        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;

    }



    public void retirerFraudeConstatee(Fraude fraude){
        fraudes.remove(fraude);

        List<Etudiant> aSupprimer = new ArrayList<>(etudiants);

        fraudes.forEach(fraudeRestante ->
                aSupprimer.removeAll(fraudeRestante.getEtudiants())
        );

        etudiants.removeAll(aSupprimer);
        dateDerniereModification = LocalDateTime.now();
    }


    public void ajouterFraudeConstatee(Fraude fraude){
        fraudes.add(fraude);
        fraude.getEtudiants().stream()
                .filter(etudiant -> !this.etudiants.contains(etudiant))
                .forEach(etudiant -> etudiants.add(etudiant));

        dateDerniereModification = LocalDateTime.now();
    }


    //GETTERS & SETTERS

    public List<Fraude> getFraudes() {
        return List.copyOf(fraudes);
    }


    public List<Etudiant> getEtudiants() {
        return List.copyOf(etudiants);
    }

    public Epreuve getEpreuve() {
        return epreuve;
    }

    public void setEpreuve(Epreuve epreuve) {
        dateDerniereModification = LocalDateTime.now();
        this.epreuve = epreuve;
    }

    public LocalDateTime getDateDerniereModification() {
        return dateDerniereModification;
    }

    public void setDateDerniereModification(LocalDateTime dateDerniereModification) {
        this.dateDerniereModification = dateDerniereModification;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        dateDerniereModification = LocalDateTime.now();
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        dateDerniereModification = LocalDateTime.now();
        this.id = id;
    }
}