package kantine;
import java.util.ArrayList;

public class KassaRij {

    private ArrayList<Dienblad> personenInRij;


    /**
     * Constructor
     */
    public KassaRij() {
        personenInRij = new ArrayList<>();
    }

    public KassaRij(Dienblad klant) {
        this();
        personenInRij.add(klant);
    }

    public KassaRij(ArrayList<Dienblad> klanten) {
        this();
        personenInRij.addAll(klanten);
    }


    /**
     * Persoon sluit achter in de rij aan
     *
     * @param klant
     */
    public void sluitAchteraan(Dienblad klant) {
        personenInRij.add(klant);
    }

    /**
     * Indien er een rij bestaat, de eerste klant uit
     * de rij verwijderen en retourneren.
     * Als er niemand in de rij staat geeft deze null terug.
     *
     * @return Eerste klant in de rij of null
     */
    public Dienblad eerstePersoonInRij() {
        if(erIsEenRij()) {
            Dienblad eerstePersoon = personenInRij.get(0);
            personenInRij.remove(0);
            return eerstePersoon;
        }

        return null;
    }

    /**
     * Methode kijkt of er personen in de rij staan.
     *
     * @return Of er wel of geen rij bestaat
     */
    public boolean erIsEenRij() {
        return (personenInRij.size() != 0);
    }
}