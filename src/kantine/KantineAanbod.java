package kantine;

import java.math.BigDecimal;
import java.util.*;

public class KantineAanbod {
    // interne opslag voorraad
    private HashMap<String, ArrayList<Artikel>> aanbod;
    private HashMap<String, Integer> startVoorraad;
    private HashMap<String, BigDecimal> prijzen;
    
    /**
     * Constructor. Het eerste argument is een lijst met artikelnamen,
     * het tweede argument is een lijst met prijzen en het derde argument
     * is een lijst met hoeveelheden. Let op: de dimensies van de drie arrays
     * moeten wel gelijk zijn!
     */
    public KantineAanbod(String[] artikelnaam, BigDecimal[] prijs, int[] aantal) {
        aanbod = new HashMap<String, ArrayList<Artikel>>();
        startVoorraad = new HashMap<String, Integer>();
        prijzen = new HashMap<String, BigDecimal>();

        for(int i = 0; i < artikelnaam.length; i++) {
            ArrayList<Artikel> artikelen = new ArrayList<Artikel>();

            //Voor elk artikel voeg het aantal[i] keer toe aan artikelen
            for(int j = 0; j < aantal[i]; j++) {
                artikelen.add(new Artikel(artikelnaam[i], prijs[i]));
            }

            startVoorraad.put(artikelnaam[i], aantal[i]);
            prijzen.put(artikelnaam[i], prijs[i]);
            aanbod.put(artikelnaam[i], artikelen);
        }
    }

    /**
     * Vult het vooraad van het gegeven product aan tot de standaardwaarde.
     * De standaardwaarde is in de constructor gezet.
     * @param productnaam naam (van het product)
     */
    private void vulVoorraadAan(String productnaam){
    	ArrayList<Artikel> huidigeVoorraad = aanbod.get(productnaam);
    	int startAantal = startVoorraad.get(productnaam);
    	int huidigeAantal = huidigeVoorraad.size();

    	BigDecimal prijs = prijzen.get(productnaam);

        for(int j = huidigeAantal; j < startAantal; j++) {
            huidigeVoorraad.add(new Artikel(productnaam, prijs));
        }

        aanbod.put(productnaam, huidigeVoorraad);
    }
    
    /**
     * Private methode om de lijst van artikelen te krijgen op basis van de
     * naam van het artikel. Retourneert null als artikel niet bestaat.
     */
    private ArrayList<Artikel> getArrayList(String productnaam) {
        return aanbod.get(productnaam);
    }

    /**
     * Private methode om een Artikel van de stapel artikelen af te pakken. 
     * Retourneert null als de stapel leeg is.
     */
    private Artikel getArtikel(ArrayList<Artikel> stapel) {
        if(stapel != null && stapel.size() != 0) {
            Artikel artikel = stapel.get(0);
            stapel.remove(0);

            if(stapel.size() <= 10)
                vulVoorraadAan(artikel.getNaam());

            return artikel;
        }

        return null;
    }

    /**
     * Publieke methode om een artikel via naam van de stapel te pakken.
     * Retouneert null als artikel niet bestaat of niet op voorraad is.
     * @param productnaam (van artikel)
     * @return artikel (of null)
     */
    public Artikel getArtikel(String productnaam) {
        return getArtikel(getArrayList(productnaam));
    }
}
