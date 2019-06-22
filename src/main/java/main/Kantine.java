import javax.persistence.*;
import java.time.LocalDate;
import javax.persistence.EntityManager;

public class Kantine {

    private Kassa kassa;
    private KassaRij kassarij;
    private KantineAanbod aanbod;
    private EntityManager manager;

    /**
     * Constructor
     */
    public Kantine(EntityManager manager) {
        kassarij = new KassaRij();
        kassa = new Kassa(kassarij, manager);
        this.manager = manager;
    }

    /**
     * Deze methode handelt de rij voor de kassa af.
     */
    public void verwerkRijVoorKassa(LocalDate date) {
        while(kassarij.erIsEenRij()) {
            kassa.rekenAf(kassarij.eerstePersoonInRij(), date);
        }
    }

    public Kassa getKassa() {
        return kassa;
    }

    public KantineAanbod getAanbod() {
        return aanbod;
    }

    public void setAanbod(KantineAanbod aanbod) {
        this.aanbod = aanbod;
    }

    public KassaRij getKassarij() {
        return kassarij;
    }
}