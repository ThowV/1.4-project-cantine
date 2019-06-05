package kantine;

import java.math.BigDecimal;

public class Geld {
    /**
     * Maakt een BigDecimal aan met de gewenste eigenschappen.
     * @param prijs De prijs als een double, halven worden afgerond naar een even getal.
     * @return Geeft een BigDecimal terug met de juiste eigenschappen.
     */
    public static BigDecimal genereerPrijs(double prijs){
        return new BigDecimal(prijs).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}

