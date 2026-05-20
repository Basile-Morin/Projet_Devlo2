package fraude;

public class FraudeIAG extends Fraude {
    private String nomService;

    public FraudeIAG(){
        super();
    }

    public FraudeIAG(String nomService){
        super();
        this.nomService=nomService;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
}
