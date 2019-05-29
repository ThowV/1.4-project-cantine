package kantine;

import java.math.BigDecimal;

public class Artikel {

    private String naam;
    private BigDecimal prijs;

    /**
     * Constructor
     * @param naam naam van het artikel.
     * @param prijs de prijs van het artikel in een bigdecimal met een scale van 2.
     */
    public Artikel(String naam, BigDecimal prijs) {
        setPrijs(prijs);

        this.naam = naam;
    }

    /**
     * Constructor die een leeg artikel genereerd.
     */
    public Artikel() {
        this("NAAMLOOS", new BigDecimal(0).setScale(2));
    }

    /**
     * @return De naam van het artikel
     */
    public String getNaam() {
        return naam;
    }

    /**
     * veranderd de naam van artikel in het opgegeven naam.
     * @param naam De naam van het product.
     */
    public void setNaam(String naam) {
        this.naam = naam;
    }

    /**
     * @return Geeft de prijs in een bigdecimal met een scale van 2.
     */
    public BigDecimal getPrijs() {
        return prijs;
    }

    /**
     * @param prijs De prijs van het artikel in BigDecimal met een scale van 2.
     */
    public void setPrijs(BigDecimal prijs) {
        if(prijs.scale() == 2)
            this.prijs = prijs;
        else
            throw new IllegalArgumentException("De scale van de BigDecimal prijs dient 2 te zijn.");
    }

    /**
     * @return Geeft informatie over dit artikel terug als een string.
     */
    public String toString() {
        return "naam: " + getNaam() + ", prijs: " + getPrijs().toString();
    }
}
