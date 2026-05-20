package fraude;

import etude.Etudiant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Fraude {
    protected LocalDate dateReleve;
    protected String description;
    protected String contenu;
    protected List<Etudiant> etudiants;

    public Fraude(){
        etudiants = new ArrayList<>();
        dateReleve=LocalDate.now();
    }

    public void addEtudiant(Etudiant etudiant){
        etudiants.add(etudiant);
    }

    public void removeEtudiant(Etudiant etudiant){
        etudiants.remove(etudiant);
    }

    public List<Etudiant> getEtudiants() {return etudiants;}

    public void setEtudiants(List<Etudiant> etudiants) {this.etudiants = etudiants;}

    public String getContenu() {return contenu;}

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateReleve() {
        return dateReleve;
    }

    public void setDateReleve(LocalDate dateReleve) {
        this.dateReleve = dateReleve;
    }
}
