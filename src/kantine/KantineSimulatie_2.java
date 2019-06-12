package kantine;

import java.math.BigDecimal;
import java.util.*;

public class KantineSimulatie {

    // kantine
    private Kantine kantine;

    // kantineaanbod
    private KantineAanbod kantineaanbod;

    // random generator
    private Random random;

    // aantal artikelen
    private static final int AANTAL_ARTIKELEN = 4;

    // artikelen
    private static final String[] artikelnamen = new String[] {
            "Koffie",
            "Broodje pindakaas",
            "Broodje kaas",
            "Appelsap"
    };

    // prijzen
    private static BigDecimal[] artikelprijzen = new BigDecimal[] {
            Geld.genereerPrijs(1.50),
            Geld.genereerPrijs(2.10),
            Geld.genereerPrijs(1.65),
            Geld.genereerPrijs(1.65)
    };

    // minimum en maximum aantal artikelen per soort
    private static final int MIN_ARTIKELEN_PER_SOORT = 10000;
    private static final int MAX_ARTIKELEN_PER_SOORT = 20000;

    // minimum en maximum aantal personen per dag
    private static final int MIN_PERSONEN_PER_DAG = 50;
    private static final int MAX_PERSONEN_PER_DAG = 100;

    // minimum en maximum artikelen per persoon
    private static final int MIN_ARTIKELEN_PER_PERSOON = 1;
    private static final int MAX_ARTIKELEN_PER_PERSOON = 4;

    /**
     * Constructor
     *
     */
    public KantineSimulatie() {
        random = new Random();

        kantine = new Kantine();
        int[] hoeveelheden = getRandomArray(AANTAL_ARTIKELEN, MIN_ARTIKELEN_PER_SOORT, MAX_ARTIKELEN_PER_SOORT);
        kantineaanbod = new KantineAanbod(artikelnamen, artikelprijzen, hoeveelheden);

        kantine.setKantineAanbod(kantineaanbod);
    }

    /**
     * Methode om een array van random getallen liggend tussen
     * min en max van de gegeven lengte te genereren
     *
     * @param lengte
     * @param min
     * @param max
     * @return De array met random getallen
     */
    private int[] getRandomArray(int lengte, int min, int max) {
        int[] temp = new int[lengte];
        for(int i = 0; i < lengte ;i++) {
            temp[i] = getRandomValue(min, max);
        }

        return temp;
    }

    /**
     * Methode om een random getal tussen min(incl)
     * en max(incl) te genereren.
     *
     * @param min
     * @param max
     * @return Een random getal
     */
    private int getRandomValue(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Methode om op basis van een array van indexen voor de array
     * artikelnamen de bijhorende array van artikelnamen te maken
     *
     * @param indexen
     * @return De array met artikelnamen
     */
    private String[] geefArtikelNamen(int[] indexen) {
        String[] artikelen = new String[indexen.length];

        for(int i = 0; i < indexen.length; i++) {
            artikelen[i] = artikelnamen[indexen[i]];

        }

        return artikelen;
    }

    /**
     * Deze methode simuleert een aantal dagen
     * in het verloop van de kantine
     *
     * @param dagen
     */
    public void simuleer(int dagen) {
        BigDecimal[] omzet = new BigDecimal[dagen];
        int[] aantal = new int[dagen];

        // for lus voor dagen
        for(int i = 0; i < dagen; i++) {

            // bedenk hoeveel personen vandaag binnen lopen
            int aantalpersonen = getRandomValue(MIN_PERSONEN_PER_DAG, MAX_PERSONEN_PER_DAG);

            // laat de personen maar komen...
            for(int j = 0; j < aantalpersonen; j++) {
                var randomTypeGetal = random.nextInt(100);

                Persoon persoon;

                if(randomTypeGetal == 0)
                    persoon = new KantineMedewerker("111222333", "Voornaam" + j, "Achternaam", new Datum(10, 9, 1999), 'm', j, true);
                else {
                    if(randomTypeGetal < 89)
                        persoon = new Student("111222333", "Voornaam" + j, "Achternaam", new Datum(10, 9, 1999), 'm', j, "HBO-ICT");
                    else
                        persoon = new Docent("111222333", "Voornaam" + j, "Achternaam", new Datum(10, 9, 1999), 'm', "ACVO", "ICT");
                }

                System.out.println("Er komt iemand: " + persoon.toString());

                // maak persoon en dienblad aan, koppel ze
                // en bedenk hoeveel artikelen worden gepakt
                int aantalartikelen = getRandomValue(MIN_ARTIKELEN_PER_PERSOON, MAX_ARTIKELEN_PER_PERSOON);

                // genereer de "artikelnummers", dit zijn indexen
                // van de artikelnamen
                int[] tePakkenArtikelen = getRandomArray(
                    aantalartikelen, 0, AANTAL_ARTIKELEN-1);

                // vind de artikelnamen op basis van
                // de indexen hierboven
                String[] artikelen = geefArtikelNamen(tePakkenArtikelen);

                // loop de kantine binnen, pak de gewenste
                // artikelen, sluit aan
                kantine.loopPakSluitAan(persoon, artikelen);
            }

            // verwerk rij voor de kassa
            kantine.verwerkRijVoorKassa();

            // toon dagtotalen (artikelen en geld in kassa)
            System.out.println("Dag: " + (i + 1) + " - Artikelen: " + kantine.getKassa().getAantalArtikelen() + " - Geld: " + kantine.getKassa().getHoeveelheidGeldInKassa());

            omzet[i] = kantine.getKassa().getHoeveelheidGeldInKassa();
            aantal[i] = kantine.getKassa().getAantalArtikelen();

            // reset de kassa voor de volgende dag
            kantine.getKassa().resetKassa();
        }

        Administratie.PrintGemiddeldAantal(aantal);
        Administratie.berekenGemiddeldeOmzet(omzet);
        Administratie.berekenDagOmzet(omzet);
    }

    /**
     * Start een simulatie
     */
    public static void main(String[] args) {
        int dagen = Integer.parseInt(args[0]);

        KantineSimulatie kantineSimulatie = new KantineSimulatie();

        kantineSimulatie.simuleer(dagen);
    }
}