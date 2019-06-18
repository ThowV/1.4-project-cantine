package nl.hanze.kantine;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Kassa {
    private int aantalArtikelen;
    private BigDecimal hoeveelheidGeldInKassa;

    /**
     * Constructor
     */
    public Kassa(KassaRij kassarij) {
        hoeveelheidGeldInKassa = Geld.genereerPrijs(0);
    }

    /**
     * Vraagt het aantal artikelen op een dienblad als iterator en berekend hiermee de totaalprijs,
     * die vervolgens wordt toegevoegd aan het hoeveelheid geld in de kassa.
     *
     * @param dienblad waar de producten vandaan gehaald moeten worden
     */
    public void rekenAf(Dienblad dienblad, int dag) {
        Persoon klant = dienblad.getKlant();
        Factuur factuur = new Factuur(dienblad, dag); //TIJDELIJKE DATUM VERANDER DIT!

        BigDecimal totaalZonderKorting = factuur.getTotaal(); //De totale prijs van alle producten
        BigDecimal korting = factuur.getKorting(); //De totale korting over alle producten
        BigDecimal totaal = totaalZonderKorting.subtract(korting); //De totale prijs met korting afgetrokken als die er is

        //Voeg het geld toe aan de kassa en geef output
        String output;

        try {
            klant.getBetaalwijze().betaal(totaal);

            //Voeg het geld toe aan het totale bedrag in de kassa
            hoeveelheidGeldInKassa = hoeveelheidGeldInKassa.add(totaal);

            output = factuur.toString();

        } catch (TeWeinigGeldException | KredietLimietException exception) {
            output = "\nBetaling van persoon " + klant.getVoornaam() + " is gefaald want " + exception.getMessage();
        }

        System.out.println(output);
    }

    /**
     * Geeft het aantal artikelen dat de kassa heeft gepasseerd,
     * vanaf het moment dat de methode resetKassa is aangeroepen.
     *
     * @return aantal artikelen
     */
    public int getAantalArtikelen() { return aantalArtikelen; }

    /**
     * Geeft het totaalbedrag van alle artikelen die de kassa
     * zijn gepasseerd, vanaf het moment dat de methode
     * resetKassa is aangeroepen.
     *
     * @return hoeveelheid geld in de kassa
     */
    public BigDecimal getHoeveelheidGeldInKassa() { return hoeveelheidGeldInKassa; }

    /**
     * reset de waarden van het aantal gepasseerde artikelen en
     * de totale hoeveelheid geld in de kassa.
     */
    public void resetKassa() {
        // method body omitted
        aantalArtikelen = 0;
        hoeveelheidGeldInKassa = Geld.genereerPrijs(0);
    }
}
