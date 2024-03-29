package nl.hanze.kantine;

import nl.hanze.kantine.json.importer.JSONConverter;
import nl.hanze.kantine.markdown.*;
import nl.hanze.kantine.queries.Queries;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

public class KantineSimulatie {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("ProjectKantine");

    private EntityManager manager;

    // kantine
    private Kantine kantine;

    // kantineaanbod
    private KantineAanbod kantineaanbod;

    // random generator
    private Random random;

    // Artikelen
    private static final Artikel[] artikelen = JSONConverter.GenerateArtikelen();
    private static final int AANTAL_ARTIKELEN = artikelen.length;

    // minimum en maximum aantal artikelen per soort
    private static final int MIN_ARTIKELEN_PER_SOORT = 10000;
    private static final int MAX_ARTIKELEN_PER_SOORT = 20000;

    // minimum en maximum aantal personen per dag
    private static final int MIN_PERSONEN_PER_DAG = 50;
    private static final int MAX_PERSONEN_PER_DAG = 100;

    // minimum en maximum artikelen per persoon
    private static final int MIN_ARTIKELEN_PER_PERSOON = 1;
    private static final int MAX_ARTIKELEN_PER_PERSOON = 4;

    // minimum en maximum begin saldo per persoon
    private static final double MIN_BEGINSALDO_PER_PERSOON = 0.0;
    private static final double MAX_BEGINSALDO_PER_PERSOON = 1000.0;

    private MarkdownGenerator markdownGenerator;


    /**
     * Constructor
     *
     */
    public KantineSimulatie() {
        manager = ENTITY_MANAGER_FACTORY.createEntityManager();

        markdownGenerator = new MarkdownGenerator();

        random = new Random();

        kantine = new Kantine(manager);
        int[] hoeveelheden = RandomGenerator.getRandomArray(AANTAL_ARTIKELEN, MIN_ARTIKELEN_PER_SOORT, MAX_ARTIKELEN_PER_SOORT);
        kantineaanbod = new KantineAanbod(artikelen, hoeveelheden);

        kantine.setKantineAanbod(kantineaanbod);
    }

    /**
     * Methode om op basis van een array van indexen voor de array
     * artikelnamen de bijhorende array van artikelnamen te maken
     *
     * @param indexen
     * @return De array met artikelnamen
     */
    private String[] geefArtikelNamen(int[] indexen) {
        String[] artikelNamen = new String[indexen.length];

        for(int i = 0; i < indexen.length; i++) {
            artikelNamen[i] = artikelen[indexen[i]].getNaam();
        }

        return artikelNamen;
    }

    /**
     * Deze methode simuleert een aantal dagen
     * in het verloop van de kantine
     *
     * @param dagen
     */
    public void simuleer(int dagen) {
        BigDecimal[] omzet = new BigDecimal[dagen];
        int[] aantal = new int[dagen];

        int week = 0;
        int dag = 0;

        // for lus voor dagen
        for(int i = 0; i < dagen; i++) {
            // geeft de week als titel in markdown.
            if(i % 7 == 0 || i == 0) {
                week = i / 7;
                markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("Week: " + (week + 1)), TitleSize.H1));
            }

            // geeft de dag als titel in markdown.
            dag = i + 1 - 7 * week;
            markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("Dag: " + dag), TitleSize.H2));
            
            // Zet een korting op een random artikel.
            boolean[] dagAanbod = RandomGenerator.getRandomBooleanArray(AANTAL_ARTIKELEN, 1);
            ArrayList<String> dagAanbiedingenNamen = getDagAanbiedingenFromAanbodBoolean(dagAanbod);
            kantineaanbod.setDagAanbiedingen(dagAanbiedingenNamen);
            printDagAanbiedingen(dagAanbiedingenNamen);

            // bedenk hoeveel personen vandaag binnen lopen
            int aantalpersonen = RandomGenerator.getRandomValue(MIN_PERSONEN_PER_DAG, MAX_PERSONEN_PER_DAG);
            markdownGenerator.add(new MarkdownString("Aantal personen op de dag: " + aantalpersonen));

            // start met het genereren van een table
            String table = MarkdownStringGenerator.generateTableHead(
                new MarkdownString[]{
                    new MarkdownString("BSN"),
                    new MarkdownString("Voornaam"),
                    new MarkdownString("Achternaam"),
                    new MarkdownString("Geboortedatum"),
                    new MarkdownString("Geslacht"),
                    new MarkdownString("Saldo")
                }
            );

            // laat de personen maar komen...
            for(int j = 0; j < aantalpersonen; j++) {
                int randomTypeGetal = random.nextInt(100);

                Persoon persoon;

                BigDecimal randomBeginSaldo = Geld.genereerPrijs(RandomGenerator.getRandomValue(MIN_BEGINSALDO_PER_PERSOON, MAX_BEGINSALDO_PER_PERSOON));

                if(randomTypeGetal == 0)
                    persoon = new KantineMedewerker("111222333", "Voornaam" + j, "Achternaam", new Datum(4, 1, 2004), 'm', randomBeginSaldo, j, true);
                else {
                    if(randomTypeGetal < 89)
                        persoon = new Student("111222333", "Voornaam" + j, "Achternaam", new Datum(4, 1, 2004), 'm', randomBeginSaldo, j, "HBO-ICT");
                    else
                        persoon = new Docent("111222333", "Voornaam" + j, "Achternaam", new Datum(4, 1, 2004), 'm', randomBeginSaldo, "ACVO", "ICT");
                }

                System.out.println("Er komt iemand: " + persoon.toString());

                //Voeg de persoon aan de markdown tabel toe
                table += MarkdownStringGenerator.generateTableContent(
                    new MarkdownString[]{
                        new MarkdownString(persoon.getBurgerServiceNummer()),
                        new MarkdownString(persoon.getVoornaam()),
                        new MarkdownString(persoon.getAchternaam()),
                        new MarkdownString(persoon.getGeboorteDatum()),
                        new MarkdownString(persoon.getGeslacht()),
                        new MarkdownString(persoon.getBeginSaldo().toString()),
                    }
                );

                // maak persoon en dienblad aan, koppel ze
                // en bedenk hoeveel artikelen worden gepakt
                int aantalartikelen = RandomGenerator.getRandomValue(MIN_ARTIKELEN_PER_PERSOON, MAX_ARTIKELEN_PER_PERSOON);

                // genereer de "artikelnummers", dit zijn indexen
                // van de artikelnamen
                int[] tePakkenArtikelen = RandomGenerator.getRandomArray(aantalartikelen, 0, AANTAL_ARTIKELEN-1);

                // vind de artikelnamen op basis van
                // de indexen hierboven
                String[] artikelen = geefArtikelNamen(tePakkenArtikelen);

                // loop de kantine binnen, pak de gewenste
                // artikelen, sluit aan
                kantine.loopPakSluitAan(persoon, artikelen);
            }

            // verwerk rij voor de kassa
            kantine.verwerkRijVoorKassa(i);

            omzet[i] = kantine.getKassa().getHoeveelheidGeldInKassa();
            aantal[i] = kantine.getKassa().getAantalArtikelen();

            // toon dagtotalen (artikelen en geld in kassa)
            System.out.println("Dag: " + (i + 1) + " - Artikelen: " + aantal[i] + " - Geld: " + omzet[i]);
            // voeg de dagtotalen toe aan de markdown
            markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("Dagtotalen:"), TitleSize.H3));
            markdownGenerator.add(new MarkdownString("Aantal artikelen: " + aantal[i]));
            markdownGenerator.add(new MarkdownString("Hoeveelheid geld: " + omzet[i]));

            // reset de kassa voor de volgende dag
            kantine.getKassa().resetKassa();

            //Voeg de tabel toe aan de markdown
            markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("Personen in de winkel:"), TitleSize.H3));
            markdownGenerator.add(table);
        }

        // print eindwaardoen
        Administratie.printGemiddeldAantal(aantal);
        Administratie.printGemiddeldeOmzet(omzet);
        Administratie.printDagOmzet(omzet);

        // voeg eindwaardoen toe aan markdown
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("Eindwaarden:"), TitleSize.H2));
        markdownGenerator.add(new MarkdownString("Gemiddelde aantal artikelen: " + Administratie.berekenGemiddeldAantal(aantal)));
        markdownGenerator.add(new MarkdownString("Gemiddelde hoeveelheid omzet: " + Administratie.berekenGemiddeldeOmzet(omzet)));

        String tabel = MarkdownStringGenerator.generateTableHead(
            new MarkdownString[]{
                new MarkdownString("Dag"),
                new MarkdownString("Omzet")
            }
        );
        BigDecimal[] dagOmzet = Administratie.berekenDagOmzet(omzet);
        for (int i = 0; i < dagOmzet.length; i++){
            tabel += MarkdownStringGenerator.generateTableContent(
                new MarkdownString[]{
                    new MarkdownString(Datum.getWeekDagAsString(i)),
                    new MarkdownString(dagOmzet[i].toString())
                }
            );
        }
        markdownGenerator.add(tabel);

        Queries.PrintTotaleOmzetEnKorting(manager);
        Queries.PrintGemiddeldeOmzetEnKorting(manager);
        Queries.PrintOmzetEnKortingPerFactuur(manager, 1);
        Queries.PrintHoogsteOmzetFacturen(manager);

        Queries.GetTotaleOmzetEnKortingPerArtikel(manager, "Appel");
        Queries.GetTotaleOmzetEnKortingPerArtikelPerDatum(manager, "Appel", new Datum(20, 06, 2019));
        Queries.PrintPopulairsteArtikelen(manager);
        Queries.PrintPopulairsteArtikelenQuaOmzet(manager);

        // stop de simulatie
        sluitSimulatie();
    }

    private void printDagAanbiedingen(ArrayList<String> dagAanbiedingenNamen) {
        System.out.print("De dagaanbiedingen van vandaag zijn: ");
        for(String naam : dagAanbiedingenNamen) {
            System.out.print(naam + ", ");
        }
        System.out.println();
    }

    private void sluitSimulatie() {
        manager.close();
        ENTITY_MANAGER_FACTORY.close();

        /*
        //Header
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("test1"), TitleSize.H1));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("test1").toBold(), TitleSize.H2));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("test1").toItalic(), TitleSize.H2));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("test1").totBoldItalic(), TitleSize.H2));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());
        markdownGenerator.add(MarkdownStringGenerator.generateTitle(new MarkdownString("test1").toStrikeThrough(), TitleSize.H2));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());

        //List
        markdownGenerator.add(MarkdownStringGenerator.generateList(
            new MarkdownString[]{
                new MarkdownString("test1").toBold(),
                new MarkdownString("test1").toItalic(),
                new MarkdownString("test1").setListIndent(ListIndent.I2),
                new MarkdownString("test1").setListIndent(ListIndent.I3)
            }
        ));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());

        //Link
        markdownGenerator.add(MarkdownStringGenerator.generateLink(new MarkdownString("test1"), "test2", new MarkdownString("test3")));
        markdownGenerator.add(MarkdownStringGenerator.generateLink(new MarkdownString("test1"), "test2", new MarkdownString("")));
        markdownGenerator.add(MarkdownStringGenerator.generateLink(new MarkdownString("test1"), "http://google.com", new MarkdownString("Google")));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());

        //Code
        markdownGenerator.add(MarkdownStringGenerator.generateCodeBlock(
            new MarkdownString[]{
                new MarkdownString("line 1"),
                new MarkdownString("line 2"),
                new MarkdownString("line 3")
            }
        ));
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());

        //Table
        markdownGenerator.add(
            MarkdownStringGenerator.generateTable(
                new MarkdownString[] {
                    new MarkdownString("title 1").toItalic(),
                    new MarkdownString("title2").toBold()
                },
                new MarkdownString[][] {
                    {
                        new MarkdownString("R1 I1").toBold(),
                        new MarkdownString("R1 I2").toItalic(),
                    },
                    {
                        new MarkdownString("R2 I1").toBold(),
                        new MarkdownString("R2 I2").toItalic(),
                    }
                }
            )
        );
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());

        markdownGenerator.add(new MarkdownString("test").toBlockquote());
        markdownGenerator.add(MarkdownStringGenerator.generateHorizontalLine());
        */

        markdownGenerator.generateMarkdown();
    }

    private ArrayList<String> getDagAanbiedingenFromAanbodBoolean(boolean[] dagAanbod){
        ArrayList<String> dagaanbiedingen = new ArrayList<String>();

        for (int x = 0; x < dagAanbod.length; x++) {
            if(dagAanbod[x])
                dagaanbiedingen.add(artikelen[x].getNaam());
        }

        return dagaanbiedingen;
    }

    /**
     * Start een simulatie
     */
    public static void main(String[] args) {
        if (args.length == 0)
            System.err.println("Er moet een argument voor het aantal dagen mee worden gegeven aan het programma.");
        else {
            try {
                int dagen = Integer.parseInt(args[0]);
                KantineSimulatie kantineSimulatie = new KantineSimulatie();
                kantineSimulatie.simuleer(dagen);

            } catch (NumberFormatException e) {
                System.err.println("Het eerste aangegeven argument moet een geheel getal zijn.");
            }
        }
    }
}
