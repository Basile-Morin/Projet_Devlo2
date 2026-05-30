package fraude;

public class FraudeIAGConnectee extends FraudeIAG {
    private String adresseIP;

    public FraudeIAGConnectee() {
        super();
    }

    public FraudeIAGConnectee(String nomService, String adresseIP){
        super(nomService);
        this.adresseIP=adresseIP;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }
}
