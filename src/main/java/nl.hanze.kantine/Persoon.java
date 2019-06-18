package nl.hanze.kantine;

import java.math.BigDecimal;

public class Persoon {

    private String burgerServiceNummer;

    private String voornaam;
    private String achternaam;

    private Datum geboorteDatum;

    private char geslacht;

    private Betaalwijze betaalwijze = new Pinpas();

    /**
     * Constructor
     * @param bsn Burger Service Nummer
     * @param voornaam Voornaam
     * @param achternaam Achternaam
     * @param geboorteDatum De geboortedatum van dit persoon
     * @param geslacht Het geslacht van deze persoon als: 'm', 'v' of 'o'
     */
    public Persoon(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, BigDecimal beginSaldo) {
        setBurgerServiceNummer(bsn);
        setGeslacht(geslacht);

        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;

        betaalwijze.setSaldo(beginSaldo);
    }

    /**
     * Constructor dat een leeg Persoon maakt.
     */
    public Persoon() {
        this("00000000", "VOORNAAM", "ACHTERNAAM", new Datum(), 'o', Geld.genereerPrijs(0));
    }

    /**
     * @return Geeft het BSN terug van deze persoon.
     */
    public String getBurgerServiceNummer() {
        return burgerServiceNummer;
    }

    /**
     * Zet de BSN van deze persoon.
     *
     * @param burgerServiceNummer Burger Service Nummer
     */
    public void setBurgerServiceNummer(String burgerServiceNummer) {
        if(isValidBurgerServiceNummer(burgerServiceNummer))
            this.burgerServiceNummer = burgerServiceNummer;
        else
            throw new IllegalArgumentException("Het BurgerServiceNummer mag enkel uit 8-9 cijfers bestaan en voldoen aan de 11-proef.");
    }

    /**
     * Checkt of een BSN geldig is.
     *
     * @param burgerServiceNummer Burger Service Nummer
     * @return true als het een geldig BSN is.
     */
    public static boolean isValidBurgerServiceNummer(String burgerServiceNummer) {
        if(burgerServiceNummer.matches("[0-9]+") && (burgerServiceNummer.length() == 8 || burgerServiceNummer.length() == 9)) {
            char[] bsn = burgerServiceNummer.toCharArray();

            //11-proef, zie: https://nl.wikipedia.org/wiki/Burgerservicenummer#11-proef
            int proef_getal = 0;
            int index = 0;

            for(int i = burgerServiceNummer.length(); i > 0; i--) {
                int bsn_getal = Character.getNumericValue(bsn[index++]);

                if(i == 1)
                    proef_getal -= i * bsn_getal;
                else
                    proef_getal += i * bsn_getal;
            }

            if(proef_getal % 11 == 0)
                return true;
        }

        return false;
    }

    /**
     * @return Geeft de voornaam terug van deze persoon.
     */
    public String getVoornaam() {
        return voornaam;
    }

    /**
     * Zet de voornaam van deze persoon.
     *
     * @param voornaam De nieuwe voornaam voor deze persoon.
     */
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    /**
     * @return Geeft de achternaam van deze persoon terug.
     */
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * @return Geeft de volledige naam van deze persoon terug.
     */
    public String getVolledigeNaam(){
        return getVoornaam() + " " + getAchternaam();
    }

    /**
     * Zet de achternaam van deze persoon.
     *
     * @param achternaam De nieuwe achternaam voor deze persoon.
     */
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    /**
     * @return Geeft de geboortedatum van deze persoon terug.
     */
    public String getGeboorteDatum() {
        return geboorteDatum.getDatumAsString();
    }

    /**
     * Zet de gebootedatum van deze persoon.
     *
     * @param geboorteDatum De nieuwe geboortedatum voor deze persoon.
     */
    public void setGeboorteDatum(Datum geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    /**
     * @return Geeft het geslacht van deze persoon als een 'm', 'v' of een 'o'
     */
    public String getGeslacht() {
        switch(geslacht) {
            case 'm':
                return "Man";
            case 'v':
                return "Vrouw";
            default:
                return "Onbekend";
        }
    }

    /**
     * Zet het geslacht voor deze persoon
     *
     * @param geslacht De enige valide opties zijn: 'm', 'v' of een 'o'
     */
    public void setGeslacht(char geslacht) {
        char geslacht_lowercase = Character.toLowerCase(geslacht);

        if(geslacht_lowercase == 'm' || geslacht_lowercase == 'v')
            this.geslacht = geslacht;
        else
            this.geslacht = 'o';
    }

    /**
     * @return Een string met informatie over deze persoon.
     */
    public String toString() {
        return "bsn: " + getBurgerServiceNummer()
                + ", voornaam: " + getVoornaam()
                + ", achternaam: " + getAchternaam()
                + ", geboortedatum: " + getGeboorteDatum()
                + ", geslacht: " + getGeslacht()
                + ", saldo: " + betaalwijze.getSaldo();
    }

    /**
     * @return Geeft de betaalwijze.
     */
    public Betaalwijze getBetaalwijze() { return betaalwijze; }

    /**
     * Zet de manier van betalen van deze persoon.
     * @param betaalwijze De manier waarop deze persoon moet betalen.
     */
    public void setBetaalwijze(Betaalwijze betaalwijze) { this.betaalwijze = betaalwijze; }
}
