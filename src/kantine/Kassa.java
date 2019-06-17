package kantine;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;

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
        Iterator<Artikel> artikelenIterator = dienblad.getArtikelenIterator();

        BigDecimal totaalPrijs = Geld.genereerPrijs(0);

        while(artikelenIterator.hasNext()) {
            Artikel artikel = artikelenIterator.next();

            totaalPrijs = totaalPrijs.add(artikel.getPrijs());
            aantalArtikelen++;
        }

        //Trek het geld af van de klant
        boolean betaald = dienblad.getKlant().getBetaalwijze().betaal(totaalPrijs);

        if(betaald) {
            //Voeg het geld toe aan het totale bedrag in de kassa
            hoeveelheidGeldInKassa = hoeveelheidGeldInKassa.add(totaalPrijs);
            System.out.println("Betaling van persoon " + dienblad.getKlant().getVoornaam() + " is gelukt met een bedrag van " + totaalPrijs);
        }
        else
            System.out.println("Betaling van persoon " + dienblad.getKlant().getVoornaam() + " is gefaald");
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