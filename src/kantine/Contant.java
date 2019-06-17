package kantine;

import java.math.BigDecimal;

public class Contant extends Betaalwijze {
    /**
     * Methode om betaling af te handelen
     */
    public boolean betaal(BigDecimal tebetalen) {
        return super.betaal(tebetalen);
    }
}