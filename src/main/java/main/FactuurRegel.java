import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.EntityManager;

@Entity
@Table(name = "regels")
public class FactuurRegel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "factuur_id")
    private Factuur factuur;
    @Column(name = "artikel")
    private String artikelnaam;

    public FactuurRegel() {}

    public FactuurRegel(Factuur factuur, Artikel artikel) {
        this.factuur = factuur;
        this.artikelnaam = artikel.getNaam();
    }
    /**
     * @return een printbare factuurregel
     */
    public String toString() {
        return "FACTUURREGELtemp";
    }
}
