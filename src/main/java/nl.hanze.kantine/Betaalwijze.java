package nl.hanze.kantine;

import java.math.BigDecimal;

public abstract class Betaalwijze {

    protected BigDecimal saldo;

    /**
     * Methode om krediet te initialiseren
     * @param saldo Het bedrag waar de saldo gelijk aan moet staan
     */
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Methode om het saldo te krijgen. Normaal is dit niet acceptabel. Momenteel wel vanwege het debuggen
     * @return Geeft het saldo terug
     */
    public BigDecimal getSaldo() { return saldo; }

    /**
     * Methode om betaling af te handelen
     * @param tebetalen
     * @return Boolean om te kijken of er voldoende saldo is
     */
    public boolean betaal(BigDecimal tebetalen) {
        if(Geld.vergelijkPrijzen(saldo, tebetalen) == Geld.prijsVergelijking.Groter) {
            //De saldo is hoger dan het te betalen bedrag dus betaal

            saldo = saldo.subtract(tebetalen);

            return true;
        }

        return false;
    }
}