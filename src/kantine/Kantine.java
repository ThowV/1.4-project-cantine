package kantine;

import java.math.BigDecimal;

public class Kantine {

    private Kassa kassa;
    private KassaRij kassarij;

    /**
     * Constructor
     */
    public Kantine() {
        kassarij = new KassaRij();
        kassa = new Kassa(kassarij);
    }

    /**
     * In deze methode wordt een Persoon en Dienblad gemaakt
     * en aan elkaar gekoppeld. Maak twee Artikelen aan
     * en plaats deze op het dienblad. Tenslotte sluit de
     * Persoon zich aan bij de rij voor de kassa.
     */
    public void loopPakSluitAan() {
        //Maak een persoon en een dienblad.
        Persoon persoon = new Persoon("181905589", "Voornaam", "Achternaam", new Datum(7, 10, 2000), 'm');
        Dienblad dienblad = new Dienblad(persoon);

        //2 Artikelen toevoegem
        dienblad.voegToe(new Artikel("Banaan", new BigDecimal(1.29).setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        dienblad.voegToe(new Artikel("Cake", new BigDecimal(2.25).setScale(2, BigDecimal.ROUND_HALF_EVEN)));

        //Voeg de persoon toe aan de rij voor de kassa
        kassarij.sluitAchteraan(dienblad);
    }

    /**
     * Deze methode handelt de rij voor de kassa af.
     */
    public void verwerkRijVoorKassa() {
        while(kassarij.erIsEenRij()) {
            kassa.rekenAf(kassarij.eerstePersoonInRij());
        }
    }

    /**
     * Deze methode telt het geld uit de kassa
     *
     * @return hoeveelheid geld in kassa
     */
    public BigDecimal hoeveelheidGeldInKassa() {
        return kassa.getHoeveelheidGeldInKassa();
    }

    /**
     * Deze methode geeft het aantal gepasseerde artikelen.
     *
     * @return het aantal gepasseerde artikelen
     */
    public int aantalArtikelen() {
        return kassa.getAantalArtikelen();
    }

    /**
     * Deze methode reset de bijgehouden telling van
     * het aantal artikelen en "leegt" de inhoud van de kassa.
     */
    public void resetKassa() {
        kassa.resetKassa();
    }
}