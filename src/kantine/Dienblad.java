package kantine;

import java.math.BigDecimal;
import java.util.Stack;

public class Dienblad {

    private Stack<Artikel> artikelen;
    private Persoon klant;

    /**
     * Constructor
     */
    public Dienblad() {
        artikelen = new Stack<>();
    }

    /**
     * Constructor
     * @param klant De klant waarvan het dienblad is.
     */
    public Dienblad(Persoon klant) {
        this();

        this.klant = klant;
    }

    /**
     * Methode om artikel aan dienblad toe te voegen
     *
     * @param artikel
     */
    public void voegToe(Artikel artikel) {
        artikelen.add(artikel);
    }

    /**
     * Methode om aantal artikelen op dienblad te tellen
     *
     * @return Het aantal artikelen
     */
    public int getAantalArtikelen() {
        return artikelen.size();
    }

    /**
     * Methode om de totaalprijs van de artikelen
     * op dienblad uit te rekenen
     *
     * @return De totaalprijs
     */
    public BigDecimal getTotaalPrijs() {
        BigDecimal totaalPrijs = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_EVEN);

        for (Artikel artikel: artikelen) {
            totaalPrijs = totaalPrijs.add(artikel.getPrijs());
        }

        return totaalPrijs;
    }

    /**
     * Methode om de klant terugtegeven wie dit dienblad gebruikt.
     *
     * @return De klant die dit dienblad gebruikt.
     */
    public Persoon getKlant() {
        return klant;
    }

    /**
     * Set de klant die dit dienblad gebruikt.
     *
     * @param klant De klant die dit dienblad gebruikt.
     */
    public void setKlant(Persoon klant) {
        this.klant = klant;
    }
}

