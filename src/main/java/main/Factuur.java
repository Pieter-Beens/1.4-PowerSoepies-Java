import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.EntityManager;

@Entity

public class Factuur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "datum")
    private LocalDate date;
    @Column(name = "subtotaal")
    private double subtotaal;
    @Column(name = "korting")
    private double korting;
    @Column(name = "totaalprijs")
    private double totaal;

    public Factuur() {
        korting = 0;
    }

    public Factuur(Dienblad dienblad, LocalDate datum) {
        this();
        date = datum;
        totaal = getTotaalPrijs(dienblad);
        subtotaal = getTotaalPrijs(dienblad);
        verwerkBestelling(dienblad);
    }
    /**
     * Verwerk artikelen en pas kortingen toe.
     *
     * Zet het totaal te betalen bedrag en het
     * totaal aan ontvangen kortingen.
     *
     * @param dienblad
     */

    private void verwerkBestelling(Dienblad dienblad) {
        // Eventuele korting wordt doorgerekend.
        if (dienblad.getKlant() instanceof KortingskaartHouder) {
            korting = ((KortingskaartHouder) dienblad.getKlant()).geefKortingsPercentage() * subtotaal;
            if (korting > ((KortingskaartHouder) dienblad.getKlant()).geefMaximum()) {
                korting = ((KortingskaartHouder) dienblad.getKlant()).geefMaximum();
            }
            totaal -= korting;
        }
    }

    /**
     * Methode om de totaalprijs van de artikelen
     * op dienblad uit te rekenen
     *
     * @return De totaalprijs
     */
    public double getTotaalPrijs(Dienblad dienblad) {
        double total = 0;
        for (int i = 0; i < dienblad.getArtikelen().size(); i++) {
                total += dienblad.getArtikelen().peek().getPrijs();
            }
        return total;
    }

    public double getTotaal() {
        return totaal;
    }

    /**
     * @return de toegepaste korting
     */
    public double getKorting() {
        return korting;
    }

    /**
     * @return een printbaar bonnetje
     */
    public String toString() {
        return "Datum: " + date + " | Subtotaal: " + subtotaal + " | Korting: " + korting + " | Totaalprijs: " + totaal;
    }


}