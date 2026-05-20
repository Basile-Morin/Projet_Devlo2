package fraude;

public class FraudeCalculatrice extends Fraude {
    private String marque;
    private String programmeStocke;

    public FraudeCalculatrice(String marque, String programmeStocke) {
        super();
        this.marque = marque;
        this.programmeStocke = programmeStocke;
    }

    public FraudeCalculatrice() {
        super();
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getProgrammeStocke() {
        return programmeStocke;
    }

    public void setProgrammeStocke(String programmeStocke) {
        this.programmeStocke = programmeStocke;
    }
}
