public class Kantinemedewerker extends Persoon {
    int medewerkersnummer;
    boolean magAchterKassa;

    public Kantinemedewerker(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, int medewerkersnummer, boolean magAchterKassa) {
        super(bsn, voornaam, achternaam, geboorteDatum, geslacht);

        this.medewerkersnummer = medewerkersnummer;
        this.magAchterKassa = magAchterKassa;
    }

    public int getMedewerkersnummer() { return medewerkersnummer; }
    public void setMedewerkersnummer(int medewerkersnummer) { this.medewerkersnummer = medewerkersnummer; }

    public boolean isMagAchterKassa() { return magAchterKassa; }
    public void setMagAchterKassa(boolean magAchterKassa) { this.magAchterKassa = magAchterKassa; }
}
