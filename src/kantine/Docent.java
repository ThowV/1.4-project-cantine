package kantine;

import java.math.BigDecimal;

public class Docent extends Persoon {
    private String afkorting;
    private String afdeling;

    /**
     * Constructor
     * @param bsn Burger Service Nummer
     * @param voornaam Voornaam
     * @param achternaam Achternaam
     * @param geboorteDatum De geboortedatum van dit persoon
     * @param geslacht Het geslacht van deze persoon als: 'm', 'v' of 'o'
     * @param beginSaldo Het begin saldo van deze persoon
     * @param afkorting De afkorting van deze docent als een 4 letter String
     * @param afdeling De afdeling waar deze docent in werkt.
     */
    public Docent(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, BigDecimal beginSaldo, String afkorting, String afdeling) {
        super(bsn, voornaam, achternaam, geboorteDatum, geslacht, beginSaldo);

        setAfkorting(afkorting);
        this.afdeling = afdeling;
    }

    /**
     * @return De afkorting van deze docent.
     */
    public String getAfkorting() { return afkorting; }

    /**
     * Stelt de afkorting in van deze docent (moet uit een 4 letterige String bestaan)
     * @param afkorting de afkorting van deze docent als 4 letter String.
     */
    public void setAfkorting(String afkorting) {
        if(afkorting.length() == 4 && afkorting.toUpperCase().matches("[A-Z]+"))
            this.afkorting = afkorting.toUpperCase();
        else
            throw new IllegalArgumentException("Afkorting dient altijd uit 4 letters te bestaan.");
    }

    /**
     * @return geeft de afdeling waar deze docent in werkt.
     */
    public String getAfdeling() { return afdeling; }

    /**
     * Zet de afdeling waar deze docent in werkt.
     * @param afdeling De afdeling waar deze docent in werkt.
     */
    public void setAfdeling(String afdeling) { this.afdeling = afdeling; }

    /**
     * @return Een string met informatie over deze docent.
     */
    public String toString(){
        return "Docent: " + super.toString() + ", afkorting: " + getAfkorting() + ", afdeling: " + getAfdeling();
    }
}
