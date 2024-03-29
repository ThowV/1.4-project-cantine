package nl.hanze.kantine;

import java.math.BigDecimal;

public class Student extends Persoon {
    private int studentnummer;
    private String studierichting;

    /**
     * Constructor
     * @param bsn Burger Service Nummer
     * @param voornaam Voornaam
     * @param achternaam Achternaam
     * @param geboorteDatum De geboortedatum van dit persoon
     * @param geslacht Het geslacht van deze persoon als: 'm', 'v' of 'o'
     * @param beginSaldo Het begin saldo van deze persoon
     * @param studentnummer het studentennummer van deze student als getal.
     * @param studierichting De studierichting die deze student heeft gekozen.
     */
    public Student(String bsn, String voornaam, String achternaam, Datum geboorteDatum, char geslacht, BigDecimal beginSaldo, int studentnummer, String studierichting) {
        super(bsn, voornaam, achternaam, geboorteDatum, geslacht, beginSaldo);

        this.studentnummer = studentnummer;
        this.studierichting = studierichting;
    }

    /**
     * @return Geeft de studie richting van deze student terug.
     */
    public int getStudentnummer() { return studentnummer; }

    /**
     * Zet het studentennummer van deze student.
     * @param studentnummer het studentennummer van deze student.
     */
    public void setStudentnummer(int studentnummer) { this.studentnummer = studentnummer; }

    /**
     * @return geeft de studierichting terug die deze student heeft gekozen.
     */
    public String getStudierichting() { return studierichting; }

    /**
     * Stel de studierichting in die deze student heeft gekozen.
     * @param studierichting de studierichting van de student.
     */
    public void setStudierichting(String studierichting) { this.studierichting = studierichting; }

    /**
     * @return Een string met informatie over deze student.
     */
    public String toString(){
        return "Student: " + super.toString() + ", studentnummer: " + getStudentnummer() + ", studierichting: " + getStudierichting();
    }
}
