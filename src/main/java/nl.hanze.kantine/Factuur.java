package nl.hanze.kantine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Iterator;

public class Factuur {
    private Long id;
    private LocalDate datum;
    private BigDecimal korting;
    private BigDecimal totaal;
    private int aantalArtikelen;
    private Dienblad dienblad;

    public Factuur() {
        totaal = Geld.genereerPrijs(0);
        korting = Geld.genereerPrijs(0);
    }

    public Factuur(Dienblad dienblad, LocalDate datum) {
        this();
        this.datum = datum;
        this.dienblad = dienblad;

        verwerkBestelling();
    }

    /**
    * Verwerk artikelen en pas kortingen toe.
    *
    * Zet het totaal te betalen bedrag en het
    * totaal aan ontvangen kortingen.
    */
    private void verwerkBestelling() {
        //Geef het totaal bedrag
        totaal = verwerkTotaal();

        //Kijk of de klant recht heeft op korting, zo ja, geef de korting
        Persoon klant = dienblad.getKlant();

        if(klant instanceof KortingskaartHouder)
            korting = verwerkKorting(totaal);
    }

    /**
     * Geeft het bedrag dat alle artiekelen op het dienblad samen gaat kosten in BigDecimal.
     * @return Totale bedrag.
     */
    private BigDecimal verwerkTotaal() {
        //Bereken hoeveel het zonder kosting kost en over hoeveel artikelen de klant beschikt
        Iterator<Artikel> artikelenIterator = dienblad.getArtikelenIterator();

        while(artikelenIterator.hasNext()) {
            Artikel artikel = artikelenIterator.next();

            totaal = totaal.add(artikel.getPrijs());
            aantalArtikelen++;
        }

        return totaal;
    }

    /**
     * Geeft de hoeveelheid geld aan korting.
     * @param totaal De totale prijs waarop de klant korting krijgt.
     * @return Hoeveelheid korting
     */
    private BigDecimal verwerkKorting(BigDecimal totaal) {
        KortingskaartHouder klant = (KortingskaartHouder)dienblad.getKlant();

        //Bereken de korting
        BigDecimal korting = Geld.vermenigvuldigPrijsDoor(totaal, klant.geefKortingsPercentage() * 0.01);

        //Kijken of de korting te hoog is voor het type klant, zo ja pas de korting aan
        if(klant.heeftMaximum() && Geld.vergelijkPrijzen(korting, klant.geefMaximum()) == Geld.prijsVergelijking.Groter)
            korting = klant.geefMaximum();

        return korting;
    }

    /**
    * @return het totaalbedrag
    */
    public BigDecimal getTotaal() { return totaal; }

    /**
    * @return de toegepaste korting
    */
    public BigDecimal getKorting() { return korting; }

    /**
    * @return een printbaar bonnetje
    */
    public String toString() {
        String klantSoortNaam = dienblad.getKlant().getClass().getSimpleName();
        String klant = Character.toUpperCase(klantSoortNaam.charAt(0)) + klantSoortNaam.substring(1).toLowerCase() + " " + dienblad.getKlant().getVoornaam();

        return "\nBon van klant: " + klant +
                "\nDe klant heeft betaald op: " + datum +
                "\nHet totaalbedrag was: " + totaal.toPlainString() +
                "\nDe klant had een korting van: " + korting.toPlainString() +
                "\nDe klant moest totaal: " + (totaal.subtract(korting).toPlainString()) + " betalen";
    }
}
