package kantine;

import java.math.BigDecimal;

public class Artikel {

    private String naam;
    private BigDecimal prijs;

    public Artikel(String naam, BigDecimal prijs) {
        setPrijs(prijs);

        this.naam = naam;
    }

    public Artikel() {
        this("NAAMLOOS", new BigDecimal(0).setScale(2));
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public void setPrijs(BigDecimal prijs) {
        if(prijs.scale() == 2)
            this.prijs = prijs;
        else
            throw new IllegalArgumentException("De scale van de BigDecimal prijs dient 2 te zijn.");
    }

    public String toString() {
        return "naam: " + getNaam() + ", prijs: " + getPrijs().toString();
    }
}
