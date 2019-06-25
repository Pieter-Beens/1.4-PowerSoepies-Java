import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.EntityManager;


public class Artikel {

    private String naam;

    private double prijs;

    public Artikel() {
        prijs = 0;
        naam = "Lucht";
    }

    public Artikel(String naam, double prijs) {
        this.prijs = prijs;
        this.naam = naam;
    }

    public String toString() {
        return "Naam: " + this.getNaam() + ", Prijs: €" + this.getPrijs();
    }

    public String getNaam() {
        return naam;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setNaam(String nieuwenaam) {
        naam = nieuwenaam;
    }

    public void setPrijs(double nieuweprijs) {
        prijs = nieuweprijs;
    }

}
