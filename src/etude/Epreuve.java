package etude;

import java.time.LocalDate;
import java.time.LocalTime;

public class Epreuve {
    String codeECUE;
    LocalDate date;
    LocalTime heure;
    int duree;
    Modalite modalite;

    public String getCodeECUE() {
        return codeECUE;
    }

    public void setCodeECUE(String codeECUE) {
        this.codeECUE = codeECUE;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }


    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }


    public Modalite getModalite() {
        return modalite;
    }

    public void setModalite(Modalite modalite) {
        this.modalite = modalite;
    }


}