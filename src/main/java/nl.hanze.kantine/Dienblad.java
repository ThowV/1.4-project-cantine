package nl.hanze.kantine;

import java.util.Iterator;
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
     * @return Een iterator voor de artikelen op het Dienblad
     */
    public Iterator<Artikel> getArtikelenIterator(){
        return artikelen.iterator();
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

