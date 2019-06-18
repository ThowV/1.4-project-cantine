package nl.hanze.kantine;

import java.math.BigDecimal;

public class Pinpas extends Betaalwijze {

    private BigDecimal kredietlimiet;

    public Pinpas() {
        kredietlimiet = Geld.genereerPrijs(RandomGenerator.getRandomValue(0.0, 100.0));
    }

    /**
     * Methode om kredietlimiet te zetten
     * @param kredietlimiet
     */
    public void setKredietLimiet(BigDecimal kredietlimiet) { this.kredietlimiet = kredietlimiet; }

    /**
     * Methode om betaling af te handelen
     */
    public boolean betaal(BigDecimal tebetalen) {
        //Kijk of het bedrag dat betaald moet worden niet hoger is dan het limiet
        if(Geld.vergelijkPrijzen(kredietlimiet, tebetalen) == Geld.prijsVergelijking.Groter) {
            //Zo ja, betaal dan het bedrag
            return super.betaal(tebetalen);
        }

        return false;
    }
}
