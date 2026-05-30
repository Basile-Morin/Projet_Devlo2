import etude.Epreuve;
import etude.Etudiant;
import fraude.Fraude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormulaireFraude {
    private int id;
    private LocalDateTime dateCreation;
    private LocalDateTime dateDerniereModification;
    private Epreuve epreuve;
    private final List<Etudiant> etudiants;
    private final List<Fraude> fraudes;

    public FormulaireFraude() {
        etudiants = new ArrayList<>();
        fraudes = new ArrayList<>();
        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;
    }

    public FormulaireFraude(List<Fraude> fraudes) {
        etudiants = new ArrayList<>();
        this.fraudes = new ArrayList<>();

        if (fraudes != null) {
            fraudes.forEach(this::ajouterFraudeConstatee);
        }

        dateCreation = LocalDateTime.now();
        dateDerniereModification = dateCreation;
    }

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

    public void ajouterFraudeConstatee(Fraude fraude) {
        if (fraude == null) {
            return;
        }

        fraudes.add(fraude);

        if (fraude.getEtudiants() != null) {
            fraude.getEtudiants().stream()
                    .filter(etudiant -> etudiant != null)
                    .filter(etudiant -> !this.etudiants.contains(etudiant))
                    .forEach(etudiants::add);
        }

        dateDerniereModification = LocalDateTime.now();
    }

    // GETTERS & SETTERS

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
        if (dateDerniereModification == null) {
            return;
        }

        this.dateDerniereModification = dateDerniereModification;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        if (dateCreation == null) {
            return;
        }

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