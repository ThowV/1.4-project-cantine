package kantine;

import java.math.BigDecimal;

public class Kantine {

    private Kassa kassa;
    private KassaRij kassarij;
    private KantineAanbod kantineAanbod;

    /**
     * Constructor
     */
    public Kantine() {
        kassarij = new KassaRij();
        kassa = new Kassa(kassarij);
        kantineAanbod = new KantineAanbod(
                new String[]{ "banaan", "appel", "peer" },
                new BigDecimal[]{
                        new BigDecimal(1.29).setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        new BigDecimal(2.10).setScale(2, BigDecimal.ROUND_HALF_EVEN)
                        new BigDecimal(0.80).setScale(2, BigDecimal.ROUND_HALF_EVEN)
                    },
                new int[]{ 23, 21, 42 }
                );
    }

    /**
     * In deze methode wordt een Persoon en Dienblad gemaakt
     * en aan elkaar gekoppeld. Maak twee Artikelen aan
     * en plaats deze op het dienblad. Tenslotte sluit de
     * Persoon zich aan bij de rij voor de kassa.
     */
    public void loopPakSluitAan(Persoon persoon, String[] artikelnamen) {
        Dienblad dienblad = new Dienblad(persoon);

        //Voeg elk artikel aan het dienblad toe dat gegeven is
        for (String artikelnaam : artikelnamen) {
            dienblad.voegToe(kantineAanbod.getArtikel(artikelnaam));
        }

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
     * @return Geeft de kassa terug die in de kantine staat.
     */
    public Kassa getKassa(){
        return kassa;
    }

    public KantineAanbod getKantineAanbod() {
        return kantineAanbod;
    }

    public void setKantineAanbod(KantineAanbod kantineAanbod) {
        this.kantineAanbod = kantineAanbod;
    }
}