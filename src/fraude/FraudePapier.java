package fraude;

import java.awt.*;

public class FraudePapier extends Fraude {
    private Point dimension;
    private boolean plie;


    public FraudePapier(boolean plie, Point dimension) {
        super();
        this.plie = plie;
        this.dimension = dimension;
    }

    public FraudePapier() {
        super();
    }

    public Point getDimension() {
        return dimension;
    }

    public void setDimension(Point dimension) {
        this.dimension = dimension;
    }

    public boolean isPlie() {
        return plie;
    }

    public void setPlie(boolean plie) {
        this.plie = plie;
    }
}
