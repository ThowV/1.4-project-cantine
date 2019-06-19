package nl.hanze.kantine;

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
                new Artikel[]{
                    new Artikel("Banaan", Geld.genereerPrijs(1.29)),
                    new Artikel("Appel", Geld.genereerPrijs(2.10)),
                    new Artikel("Peer", Geld.genereerPrijs(0.80))
                }, new int[] { 23, 21, 42 });
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
    public void verwerkRijVoorKassa(int dag) {
        while(kassarij.erIsEenRij()) {
            kassa.rekenAf(kassarij.eerstePersoonInRij(), dag);
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