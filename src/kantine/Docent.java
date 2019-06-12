public class Docent extends Persoon {
    String afkorting;
    String afdeling;

    public Docent(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, String afkorting, String afdeling) {
        super(bsn, voornaam, achternaam, geboorteDatum, geslacht);

        this.afkorting = afkorting;
        this.afdeling = afdeling;
    }

    public String getAfkorting() { return afkorting; }
    public void setAfkorting(String afkorting) { this.afkorting = afkorting; }

    public String getAfdeling() { return afdeling; }
    public void setAfdeling(String afdeling) { this.afdeling = afdeling; }
}
