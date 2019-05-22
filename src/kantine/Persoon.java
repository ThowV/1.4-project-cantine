package kantine;

public class Persoon {

    private String burgerServiceNummer;

    private String voornaam;
    private String achternaam;

    private Datum geboorteDatum;

    private char geslacht;

    public Persoon(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht) {
        setBurgerServiceNummer(bsn);
        setGeslacht(geslacht);

        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
    }

    public Persoon() {
        this("00000000", "VOORNAAM", "ACHTERNAAM", new Datum(), 'o');
    }

    public String getBurgerServiceNummer() {
        return burgerServiceNummer;
    }

    public void setBurgerServiceNummer(String burgerServiceNummer) {
        if(isValidBurgerServiceNummer(burgerServiceNummer))
            this.burgerServiceNummer = burgerServiceNummer;
        else
            throw new IllegalArgumentException("Het BurgerServiceNummer mag enkel uit 8-9 cijfers bestaan en voldoen aan de 11-proef.");
    }

    public static boolean isValidBurgerServiceNummer(String burgerServiceNummer) {
        if(burgerServiceNummer.matches("[0-9]+") && (burgerServiceNummer.length() == 8 || burgerServiceNummer.length() == 9)) {
            var bsn = burgerServiceNummer.toCharArray();

            //11-proef, zie: https://nl.wikipedia.org/wiki/Burgerservicenummer#11-proef
            int proef_getal = 0;
            int index = 0;

            for(int i = burgerServiceNummer.length(); i > 0; i--) {
                var bsn_getal = Character.getNumericValue(bsn[index++]);

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

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getGeboorteDatum() {
        return geboorteDatum.getDatumAsString();
    }

    public void setGeboorteDatum(Datum geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

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

    public void setGeslacht(char geslacht) {
        var geslacht_lowercase = Character.toLowerCase(geslacht);

        if(geslacht_lowercase == 'm' || geslacht_lowercase == 'v')
            this.geslacht = geslacht;
        else
            this.geslacht = 'o';
    }

    public String toString() {
        return "bsn: " + getBurgerServiceNummer()
                + ", voornaam: " + getVoornaam()
                + ", achternaam: " + getAchternaam()
                + ", geboortedatum: " + getGeboorteDatum()
                + ", geslacht: " + getGeslacht();
    }
}
