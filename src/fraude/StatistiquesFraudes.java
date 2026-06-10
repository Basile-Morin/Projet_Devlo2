package fraude;

import etude.Etudiant;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StatistiquesFraudes {

    public int nombreTotalFormulaires(List<FormulaireFraude> formulaires) {
        if (formulaires == null) {
            return 0;
        }

        return formulaires.size();
    }

    public int nombreEtudiantsDistincts(List<FormulaireFraude> formulaires) {
        if (formulaires == null) {
            return 0;
        }

        Set<String> numerosEtudiants = new HashSet<>();

        for (FormulaireFraude formulaire : formulaires) {
            for (Etudiant etudiant : formulaire.getEtudiants()) {
                numerosEtudiants.add(etudiant.getNumeroApprenant());
            }
        }

        return numerosEtudiants.size();
    }

    public int nombreTotalFraudes(List<FormulaireFraude> formulaires) {
        if (formulaires == null) {
            return 0;
        }

        int total = 0;

        for (FormulaireFraude formulaire : formulaires) {
            total += formulaire.getFraudes().size();
        }

        return total;
    }

    public double moyenneFraudesParFormulaire(List<FormulaireFraude> formulaires) {
        if (formulaires == null || formulaires.isEmpty()) {
            return 0;
        }

        return (double) nombreTotalFraudes(formulaires) / formulaires.size();
    }

    public double ecartTypeFraudesParFormulaire(List<FormulaireFraude> formulaires) {
        if (formulaires == null || formulaires.isEmpty()) {
            return 0;
        }

        double moyenne = moyenneFraudesParFormulaire(formulaires);
        double sommeEcartsAuCarre = 0;

        for (FormulaireFraude formulaire : formulaires) {
            int nombreFraudes = formulaire.getFraudes().size();
            double ecart = nombreFraudes - moyenne;
            sommeEcartsAuCarre += ecart * ecart;
        }

        return Math.sqrt(sommeEcartsAuCarre / formulaires.size());
    }
}