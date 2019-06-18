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
    public void betaal(BigDecimal tebetalen) {
        //Kijk of het bedrag dat betaald moet worden niet hoger is dan het limiet
        if(Geld.vergelijkPrijzen(kredietlimiet, tebetalen) == Geld.prijsVergelijking.Groter)
            super.betaal(tebetalen);
        else
            throw new KredietLimietException("het bedrag is te hoog voor het aangegeven kridietlimiet");
    }
}
