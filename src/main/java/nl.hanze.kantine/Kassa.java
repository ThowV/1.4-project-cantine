package nl.hanze.kantine;

import java.math.BigDecimal;
import java.util.Iterator;

public class Kassa {
    private int aantalArtikelen;
    private BigDecimal hoeveelheidGeldInKassa;

    /**
     * Constructor
     */
    public Kassa(KassaRij kassarij) {
        hoeveelheidGeldInKassa = Geld.genereerPrijs(0);
    }

    /**
     * Vraagt het aantal artikelen op een dienblad als iterator en berekend hiermee de totaalprijs,
     * die vervolgens wordt toegevoegd aan het hoeveelheid geld in de kassa.
     *
     * @param dienblad waar de producten vandaan gehaald moeten worden
     */
    public void rekenAf(Dienblad dienblad) {
        Persoon klant = dienblad.getKlant();
        Iterator<Artikel> artikelenIterator = dienblad.getArtikelenIterator();

        BigDecimal totaalPrijs = Geld.genereerPrijs(0);

        while(artikelenIterator.hasNext()) {
            Artikel artikel = artikelenIterator.next();

            totaalPrijs = totaalPrijs.add(artikel.getPrijs());
            aantalArtikelen++;
        }

        //Kijk of de klant recht heeft op korting
        boolean klantHadKorting = false;
        BigDecimal vorigeTotaalPrijs = totaalPrijs;
        BigDecimal kortingDeel = Geld.genereerPrijs(0);

        if(klant instanceof KortingskaartHouder) {
            kortingDeel = geefKortingDeel((KortingskaartHouder)klant, totaalPrijs);
            totaalPrijs = totaalPrijs.subtract(kortingDeel);
            klantHadKorting = true;
        }

        String output;

        try {
            klant.getBetaalwijze().betaal(totaalPrijs);

            //Voeg het geld toe aan het totale bedrag in de kassa
            hoeveelheidGeldInKassa = hoeveelheidGeldInKassa.add(totaalPrijs);

            output = "Betaling van " + klant.getClass().getSimpleName().toLowerCase() + " " + klant.getVoornaam() + " is gelukt met een bedrag van " + vorigeTotaalPrijs;

            if(klantHadKorting)
                output += " maar deze klant heeft ook een korting van " + kortingDeel + " dus de nieuwe prijs wordt " + totaalPrijs;

        } catch (TeWeinigGeldException | KredietLimietException exception) {
            output = klant.getVolledigeNaam() + " kan niet betalen want " + exception.getMessage();
        }

        System.out.println(output);
    }

    /**
     * Geeft de hoeveelheid geld aan korting.
     * @param klant De klant die korting krijgt.
     * @param prijs De totale prijs waarop de klant korting krijgt.
     * @return hoeveelheid korting
     */
    private BigDecimal geefKortingDeel(KortingskaartHouder klant, BigDecimal prijs) {
        //Bereken de korting in geld
        BigDecimal kortingDeel = Geld.vermenigvuldigPrijsDoor(prijs, klant.geefKortingsPercentage() * 0.01);

        //Kijken of de korting te hoog is voor het type klant
        if(klant.heeftMaximum() && Geld.vergelijkPrijzen(kortingDeel, klant.geefMaximum()) == Geld.prijsVergelijking.Groter)
            kortingDeel = klant.geefMaximum();

        return kortingDeel;
    }

    /**
     * Geeft het aantal artikelen dat de kassa heeft gepasseerd,
     * vanaf het moment dat de methode resetKassa is aangeroepen.
     *
     * @return aantal artikelen
     */
    public int getAantalArtikelen() {
        return aantalArtikelen;
    }

    /**
     * Geeft het totaalbedrag van alle artikelen die de kassa
     * zijn gepasseerd, vanaf het moment dat de methode
     * resetKassa is aangeroepen.
     *
     * @return hoeveelheid geld in de kassa
     */
    public BigDecimal getHoeveelheidGeldInKassa() {
        return hoeveelheidGeldInKassa;
    }

    /**
     * reset de waarden van het aantal gepasseerde artikelen en
     * de totale hoeveelheid geld in de kassa.
     */
    public void resetKassa() {
        // method body omitted
        aantalArtikelen = 0;
        hoeveelheidGeldInKassa = Geld.genereerPrijs(0);
    }
}
