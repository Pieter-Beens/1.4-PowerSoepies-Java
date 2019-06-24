import java.util.Iterator;
import java.util.Stack;
import java.math.*;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.Session;


public class Kassa {

    private KassaRij kassarij;
    private int aantalklanten;
    public int aantalartikelen;
    public double geld;
    public Factuur factuur;
    private EntityManager manager;

    /**
     * Constructor
     */
    public Kassa(KassaRij kassarij, EntityManager manager) {
        this.kassarij = kassarij;
        this.manager = manager;
        resetKassa();
    }

    /**
     * Vraag het aantal artikelen en de totaalprijs op.
     * Tel deze gegevens op bij de controletotalen die voor
     * de kassa worden bijgehouden. De implementatie wordt
     * later vervangen door een echte betaling door de persoon.
     *
     * @param dienblad die moet afrekenen
     */
    public void rekenAf(Dienblad dienblad, LocalDate date) {

        int artikelenopdienblad = getAantalArtikelenOpDienblad(dienblad);

        // Factuur wordt aangemaakt, deze berekent automatisch zelf de totaalprijs (ook korting).
        factuur = new Factuur(dienblad, date);

        System.out.println(factuur.toString());

        double totaalprijs = factuur.getTotaal();

        // Saldo wordt gecheckt, en indien toereikend wordt er geld van de klant naar de kassa overgemaakt.
        // Als saldo niet toereikend is dan vertrekt de klant zonder iets te kopen. Hoe dan ook komt hierna de volgende klant.
        EntityTransaction tx = null;
        try {
            dienblad.getKlant().getBetaalwijze().betaal(totaalprijs);
            aantalartikelen += artikelenopdienblad;
            geld += totaalprijs;
            tx = manager.getTransaction();
            tx.begin();
            manager.persist(factuur);
        } catch(TeWeinigGeldException e) {
            System.out.println(e + dienblad.getKlant().getVoornaam() + " " + dienblad.getKlant().getAchternaam());
        }
        if (tx != null) {
            tx.commit();
        }
        aantalklanten++;
        kassarij.naarVolgendeKlant();
    }

    public int getAantalKlanten() {
        return aantalklanten;
    }

    /**
     * Geeft het aantal artikelen dat de kassa heeft gepasseerd,
     * vanaf het moment dat de methode resetWaarden is aangeroepen.
     *
     * @return aantal artikelen
     */

    public int getAantalArtikelen() {
        return aantalartikelen;
    }

    /**
     * Methode om aantal WERKELIJKE artikelen op dienblad te tellen
     * Trekt het aantal Lucht-artikelen (geselecteerde artikelen waar geen voorraad van was) af van het totaal
     *
     * @return Het aantal artikelen
     */
    public int getAantalArtikelenOpDienblad(Dienblad dienblad) {
        int hoeveelheidlucht = 0;
        for (int i = 0; i < dienblad.getArtikelen().size(); i++) {
            if (dienblad.getArtikelen().get(i).getNaam().equals("Lucht")) hoeveelheidlucht++;
        }
        return dienblad.getArtikelen().size() - hoeveelheidlucht;
    }

    /**
     * Geeft het totaalbedrag van alle artikelen die de kass
     * zijn gepasseerd, vanaf het moment dat de methode
     * resetKassa is aangeroepen.
     *
     * @return hoeveelheid geld in de kassa
     */
    public double hoeveelheidGeldInKassa() {
        return geld;
    }

    /**
     * reset de waarden van het aantal gepasseerde artikelen en
     * de totale hoeveelheid geld in de kassa.
     */
    public void resetKassa() {
        aantalklanten = 0;
        aantalartikelen = 0;
        geld = 0;
    }

    public KassaRij getKassarij() {
        return kassarij;
    }
}