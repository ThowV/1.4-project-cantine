package nl.hanze.kantine;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;

@Entity
@Table(name = "factuur")
public class Factuur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "korting")
    private BigDecimal korting;

    @Column(name = "totaal")
    private BigDecimal totaal;

    @Column(name = "aantal_artikelen")
    private int aantalArtikelen;

    @Transient
    private Dienblad dienblad;


    public Factuur() {
        totaal = Geld.genereerPrijs(0);
        korting = Geld.genereerPrijs(0);
    }

    public Factuur(Dienblad dienblad, int dag) {
        this();
        this.datum = LocalDate.now().plusDays(dag);
        this.aantalArtikelen = 0;
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

        Persoon klant = dienblad.getKlant();
        korting = verwerkKorting(totaal, klant);
    }

    /**
     * Geeft het bedrag dat alle artiekelen op het dienblad samen gaat kosten in BigDecimal.
     * @return Totale bedrag.
     */
    private BigDecimal verwerkTotaal() {
        BigDecimal totaal = Geld.genereerPrijs(0);

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
    private BigDecimal verwerkKorting(BigDecimal totaal, Persoon klant) {
        BigDecimal totaalBedrag = totaal;
        BigDecimal korting = Geld.genereerPrijs(0);

        if(klant instanceof KortingskaartHouder) {
            Iterator<Artikel> artikelen = dienblad.getArtikelenIterator();

            while (artikelen.hasNext()) {
                Artikel artikel = artikelen.next();

                korting = korting.add(artikel.getKorting());

                if(!artikel.getKorting().equals(Geld.genereerPrijs(0)))
                    totaalBedrag = totaalBedrag.subtract(artikel.getPrijs());
            }

            KortingskaartHouder kortingskaartHouder = (KortingskaartHouder) klant;

            //Bereken de korting
            korting = korting.add(Geld.vermenigvuldigPrijsDoor(totaalBedrag, kortingskaartHouder.geefKortingsPercentage() * 0.01));

            //Kijken of de korting te hoog is voor het type klant, zo ja pas de korting aan
            if(kortingskaartHouder.heeftMaximum() && Geld.vergelijkPrijzen(korting, kortingskaartHouder.geefMaximum()) == Geld.prijsVergelijking.Groter)
                korting = kortingskaartHouder.geefMaximum();
        } else {
            Iterator<Artikel> artikelen = dienblad.getArtikelenIterator();

            while (artikelen.hasNext()) {
                Artikel artikel = artikelen.next();
                korting = korting.add(artikel.getKorting());
            }
        }

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
