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

    /**
     * Deelt een BigDecimal met een integer.
     * @param prijs De prijs als BigDecimal.
     * @param deelGetal De integer waarmee de prijs gedeeld wordt.
     * @return Geeft een BigDecimal terug dat gedeeld is door deelGetal.
     */
    public static BigDecimal deelPrijsDoor(BigDecimal prijs, int deelGetal) {
        BigDecimal deelGetalBD = genereerPrijs((double)deelGetal);

        return prijs.divide(deelGetalBD, 4, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Deelt een BigDecimal met een BigDecimal.
     * @param prijs De prijs als BigDecimal.
     * @param deelGetal De BigDecimal waarmee de prijs gedeeld wordt.
     * @return Geeft een BigDecimal terug dat gedeeld is door deelGetal.
     */
    public static BigDecimal deelPrijsDoor(BigDecimal prijs, BigDecimal deelGetal) {
        return prijs.divide(deelGetal, 4, BigDecimal.ROUND_HALF_EVEN);
    }
}