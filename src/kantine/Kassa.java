package kantine;

import java.math.BigDecimal;

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
     * @param klant die moet afrekenen als dienblad
     */
    public void rekenAf(Dienblad klant) {
        var artikelenIterator = klant.getArtikelenIterator();

        var totaalPrijs = Geld.genereerPrijs(0);

        while(artikelenIterator.hasNext()) {
            var artikel = artikelenIterator.next();

            totaalPrijs = totaalPrijs.add(artikel.getPrijs());
            aantalArtikelen++;
        }

        hoeveelheidGeldInKassa = hoeveelheidGeldInKassa.add(totaalPrijs);
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