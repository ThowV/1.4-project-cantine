package kantine;

public class KantineMedewerker extends Persoon {
    private int medewerkersNummer;
    private boolean magAchterKassa;

    /**
     * Constructor
     * @param bsn Burger Service Nummer
     * @param voornaam Voornaam
     * @param achternaam Achternaam
     * @param geboorteDatum De geboortedatum van dit persoon
     * @param geslacht Het geslacht van deze persoon als: 'm', 'v' of 'o'
     * @param medewerkersNummer Het medewerkers nummer
     * @param magAchterKassa of deze medewerker achter de kassa mag staan of niet.
     */
    public KantineMedewerker(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, int medewerkersNummer, boolean magAchterKassa) {
        super(bsn, voornaam, achternaam, geboorteDatum, geslacht);

        this.medewerkersNummer = medewerkersNummer;
        this.magAchterKassa = magAchterKassa;
    }

    /**
     * @return Het medewerkers nummer.
     */
    public int getMedewerkersNummer() { return medewerkersNummer; }

    /**
     * Zet het medewerkersnummer
     * @param medewerkersNummer Het medewerkersnummer deze persoon moet krijgen
     */
    public void setMedewerkersNummer(int medewerkersNummer) { this.medewerkersNummer = medewerkersNummer; }

    /**
     * @return true als deze persoon achter de kassa mag staan.
     */
    public boolean getMagAchterKassa() { return magAchterKassa; }

    /**
     * Stelt in of deze persoon achter de kassa mag staan.
     * @param magAchterKassa of deze persoon achter de kassa mag staan.
     */
    public void setMagAchterKassa(boolean magAchterKassa) { this.magAchterKassa = magAchterKassa; }
}
