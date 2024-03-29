package nl.hanze.kantine;

import java.math.BigDecimal;
import java.util.*;

public class KantineAanbod {
    // interne opslag voorraad
    private HashMap<String, ArrayList<Artikel>> aanbod;
    private HashMap<String, Integer> startVoorraad;
    private HashMap<String, BigDecimal> prijzen;

    private String[] dagAanbiedingen;

    /**
     * Constructor. Het eerste argument is een lijst met artikelnamen,
     * het tweede argument is een lijst met prijzen en het derde argument
     * is een lijst met hoeveelheden. Let op: de dimensies van de drie arrays
     * moeten wel gelijk zijn!
     */
    public KantineAanbod(Artikel[] artikelenArray, int[] aantal) {
        aanbod = new HashMap<String, ArrayList<Artikel>>();
        startVoorraad = new HashMap<String, Integer>();
        prijzen = new HashMap<String, BigDecimal>();

        for(int i = 0; i < artikelenArray.length; i++) {
            ArrayList<Artikel> artikelen = new ArrayList<Artikel>();

            //Voor elk artikel voeg het aantal[i] keer toe aan artikelen
            for(int j = 0; j < aantal[i]; j++) {
                artikelen.add(new Artikel(artikelenArray[i].getNaam(), artikelenArray[i].getPrijs()));
            }

            startVoorraad.put(artikelenArray[i].getNaam(), aantal[i]);
            prijzen.put(artikelenArray[i].getNaam(), artikelenArray[i].getPrijs());
            aanbod.put(artikelenArray[i].getNaam(), artikelen);
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

    	if(isProductInDeAanbieding(productnaam)){
            for(int j = huidigeAantal; j < startAantal; j++) {
                huidigeVoorraad.add(new Artikel(productnaam, prijs, Geld.vermenigvuldigPrijsDoor(prijs, 0.20)));
            }
        } else {
            for(int j = huidigeAantal; j < startAantal; j++) {
                huidigeVoorraad.add(new Artikel(productnaam, prijs));
            }
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

    public String[] getDagAanbiedingen() {
        return dagAanbiedingen;
    }

    public void setDagAanbiedingen(ArrayList<String> dagAanbiedingen) {
        this.dagAanbiedingen = new String[dagAanbiedingen.size()];

        for(int i = 0; i < dagAanbiedingen.size(); i++) {
            this.dagAanbiedingen[i] = dagAanbiedingen.get(i);
            updateKortingVoorArtikel(this.dagAanbiedingen[i]);
        }
    }

    private boolean isProductInDeAanbieding(String productnaam) {
        for (String aanbiedingProductNaam : dagAanbiedingen) {
            if (productnaam.equals(aanbiedingProductNaam))
                return true;
        }

        return false;
    }

    public void updateKortingVoorArtikel(String productnaam) {
        Iterator<Artikel> huidigeVoorraad = aanbod.get(productnaam).iterator();

        if(isProductInDeAanbieding(productnaam)) {
            while(huidigeVoorraad.hasNext()) {
                Artikel artikel = huidigeVoorraad.next();
                artikel.setKorting(Geld.vermenigvuldigPrijsDoor(artikel.getPrijs(), 0.20));
            }
        } else {
            while(huidigeVoorraad.hasNext()) {
                Artikel artikel = huidigeVoorraad.next();
                artikel.setKorting(Geld.genereerPrijs(0));
            }
        }
    }
}
