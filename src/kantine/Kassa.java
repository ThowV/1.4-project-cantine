package kantine;

import java.math.BigDecimal;

public class Kassa {
    private int aantalArtikelen;
    private BigDecimal hoeveelheidGeldInKassa;

    /**
     * Constructor
     */
    public Kassa(KassaRij kassarij) {
        hoeveelheidGeldInKassa = new BigDecimal(0).setScale(2);
    }

    /**
     * Vraag het aantal artikelen en de totaalprijs op.
     * Tel deze gegevens op bij de controletotalen die voor
     * de kassa worden bijgehouden. De implementatie wordt
     * later vervangen door een echte betaling door de persoon.
     *
     * @param klant die moet afrekenen
     */
    public void rekenAf(Dienblad klant) {
        if(klant.getAantalArtikelen() > 0) {
            aantalArtikelen += klant.getAantalArtikelen();
            hoeveelheidGeldInKassa.add(klant.getTotaalPrijs());
        }
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
     * Geeft het totaalbedrag van alle artikelen die de kass
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
        hoeveelheidGeldInKassa = new BigDecimal(0).setScale(2);
    }
}