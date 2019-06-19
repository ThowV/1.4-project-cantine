package nl.hanze.kantine;

import java.math.BigDecimal;

public class Artikel {
    private String naam;
    private BigDecimal prijs;
    private BigDecimal korting;

    /**
     * Constructor
     * @param naam naam van het artikel.
     * @param prijs de prijs van het artikel in een bigdecimal met een scale van 2.
     */
    public Artikel(String naam, BigDecimal prijs) {
        this(naam, prijs, Geld.genereerPrijs(0));
    }

    /**
     * @param naam naam van het artikel.
     * @param prijs de prijs van het artikel in een bigdecimal met een scale van 2.
     * @param korting De korting van het artikel in BigDecimal met een scale van 2.
     */
    public Artikel(String naam, BigDecimal prijs, BigDecimal korting) {
        setPrijs(prijs);
        setKorting(korting);
        this.naam = naam;
    }

    /**
     * Constructor die een leeg artikel genereerd.
     */
    public Artikel() {
        this("NAAMLOOS", Geld.genereerPrijs(0), Geld.genereerPrijs(0));
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
     * @return geeft de korting terug als een BigDecimal met een scale van 2.
     */
    public BigDecimal getKorting(){
        return korting;
    }

    /**
     * @param korting De korting van het artikel in BigDecimal met een scale van 2.
     */
    public void setKorting(BigDecimal korting){
        if(korting.scale() == 2)
            this.korting = korting;
        else
            throw new IllegalArgumentException("De scale van de BigDecimal korting dient 2 te zijn.");
    }

    /**
     * @return Geeft informatie over dit artikel terug als een string.
     */
    public String toString() {
        return "naam: " + getNaam() + ", prijs: " + getPrijs();
    }
}
