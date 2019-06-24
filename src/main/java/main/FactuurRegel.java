import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.EntityManager;

public class FactuurRegel implements Serializable {

    private Long id;
    private Factuur factuur;
    private Artikel artikel;
    public FactuurRegel() {}
    public FactuurRegel(Factuur factuur, Artikel artikel) {
        this.factuur = factuur;
        this.artikel = artikel;
    }
    /**
     * @return een printbare factuurregel
     */
    public String toString() {
        // method body omitted
    }
}
