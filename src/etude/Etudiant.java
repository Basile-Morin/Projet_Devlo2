package etude;

import java.util.Objects;

public class Etudiant {
    String numeroApprenant;
    String nom;
    String prenom;
    Cursus cursus;

    public Etudiant(){

    }

    public String getNumeroApprenant() {
        return numeroApprenant;
    }

    public void setNumeroApprenant(String numeroApprenant) {
        this.numeroApprenant = numeroApprenant;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public Cursus getCursus() {
        return cursus;
    }

    public void setCursus(Cursus cursus) {
        this.cursus = cursus;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return Objects.equals(numeroApprenant, etudiant.numeroApprenant);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroApprenant);
    }
}
