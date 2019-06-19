package nl.hanze.kantine;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Geld {
    public enum prijsVergelijking { Kleiner, Groter, Gelijk };

    /**
     * Maakt een BigDecimal aan met de gewenste eigenschappen.
     * @param prijs De prijs als een double, halven worden afgerond naar een even getal.
     * @return Geeft een BigDecimal terug met de juiste eigenschappen.
     */
    public static BigDecimal genereerPrijs(double prijs){
        return new BigDecimal(prijs).setScale(2, BigDecimal.ROUND_HALF_EVEN);
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

    /**
     * Deelt een BigDecimal met een integer.
     * @param prijs De prijs als BigDecimal.
     * @param deelGetal De integer waarmee de prijs gedeeld wordt.
     * @return Geeft een BigDecimal terug dat gedeeld is door deelGetal.
     */
    public static BigDecimal deelPrijsDoor(BigDecimal prijs, int deelGetal) {
        return deelPrijsDoor(prijs, genereerPrijs((double)deelGetal));
    }

    /**
     * Vermenigvuldigd een Bigdecimal met een BigDecimal.
     * @param prijs De prijs als BigDecimal.
     * @param vermigvuldigGetal De BigDecimal waarmee de prijs vermenigvuldigd wordt
     * @return Geeft een BigDecimal terug dat vermenigvuldigd is door vermenigvuldigGetal afgerond op 2 decimalen
     */
    public static BigDecimal vermenigvuldigPrijsDoor(BigDecimal prijs, BigDecimal vermigvuldigGetal) {
        return prijs.multiply(vermigvuldigGetal).setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Vermenigvuldigd een Bigdecimal met een double.
     * @param prijs De prijs als BigDecimal.
     * @param vermigvuldigGetal De double waarmee de prijs vermenigvuldigd wordt
     * @return Geeft een BigDecimal terug dat vermenigvuldigd is door vermenigvuldigGetal
     */
    public static BigDecimal vermenigvuldigPrijsDoor(BigDecimal prijs, double vermigvuldigGetal) {
        return vermenigvuldigPrijsDoor(prijs, genereerPrijs(vermigvuldigGetal));
    }

    /**
     * Vergelijkt twee BigDecimals met elkaar.
     * @param teVergelijken De BigDecimal die je wilt vergelijken
     * @param vergelijkenMet De BigDecimal waarmee je wilt vergelijken
     * @return Geeft een prijsVergelijking enum terug.
     */
    public static prijsVergelijking vergelijkPrijzen(BigDecimal teVergelijken, BigDecimal vergelijkenMet) {
        int resultaat = teVergelijken.compareTo(vergelijkenMet);

        if(resultaat == -1)
            return prijsVergelijking.Kleiner; //Want vergelijkenMet is groter dan teVergelijken
        else if(resultaat == 1)
            return prijsVergelijking.Groter; //Want vergelijkenMet is kleiner dan teVergelijken

        return prijsVergelijking.Gelijk; //Want vergelijkenMet is gelijk aan teVergelijken
    }
}