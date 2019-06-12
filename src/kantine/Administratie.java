package kantine;

import java.math.BigDecimal;
import java.util.Arrays;

public class Administratie {

    private static int DAYS_IN_WEEK = 7;

    /**
     * Deze methode berekent van de int array aantal de gemiddelde waarde
     *
     * @param aantal
     * @return het gemiddelde
     */
    public static double berekenGemiddeldAantal(int[] aantal) {
        double totaalAantal = 0;

        for(int i = 0; i < aantal.length; i++)
            totaalAantal += aantal[i];

        return totaalAantal / aantal.length;
    }

    /**
     * Deze methode berekent van de BigDecimal array omzet de gemiddelde waarde
     *
     * @param omzet
     * @return het gemiddelde
     */
    public static BigDecimal berekenGemiddeldeOmzet(BigDecimal[] omzet) {
        BigDecimal totaaltotaalOmzet = Geld.genereerPrijs(0.00);

        for(int i = 0; i < omzet.length; i++)
            totaaltotaalOmzet = totaaltotaalOmzet.add(omzet[i]);

        return Geld.deelPrijsDoor(totaaltotaalOmzet, omzet.length);
    }

    /**
     * Methode om dagomzet uit te rekenen.
     *
     * @param omzet Alle omzetten in een periode.
     * @return array (7 elementen) met de opgetelde dagomzetten.
     */
    public static BigDecimal[] berekenDagOmzet(BigDecimal[] omzet) {
        BigDecimal[] temp = new BigDecimal[DAYS_IN_WEEK];
        Arrays.fill(temp, Geld.genereerPrijs(0));

        /* Oude methode:
        for(int i = 0; i < DAYS_IN_WEEK; i++) {
            int j = 0;

            while(i + DAYS_IN_WEEK * j < omzet.length) {
                temp[i] = temp[i].add(omzet[i + DAYS_IN_WEEK * j]);
                j++;
            }
        }
        */

        for(int i = 0; i < omzet.length; i++) {
            int dag = i % DAYS_IN_WEEK;
            temp[dag] = temp[dag].add(omzet[i]);
        }

        return temp;
    }

    public static void printDagOmzet(BigDecimal[] omzet){
        BigDecimal[] dagOmzet = berekenDagOmzet(omzet);

        System.out.println("Dagomzet: ");
        for (int i = 0; i < dagOmzet.length; i++){
            System.out.println(Datum.getWeekDagAsString(i) + ": " + dagOmzet[i].toString());
        }
        System.out.println("___________");
    }

    public static void printGemiddeldeOmzet(BigDecimal[] omzet){
        System.out.println("Gemiddelde omzet: " + berekenGemiddeldeOmzet(omzet));
    }

    public static void printGemiddeldAantal(int[] aantal){
        System.out.println("Gemiddeld aantal: " + berekenGemiddeldAantal(aantal));
    }
}
