package nl.hanze.kantine.queries;

import nl.hanze.kantine.Datum;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public final class Queries {
    /**
     * Private Constructor
     */
    private Queries() {}

    /**
     * Print de totale omzet en de totale korting
     * @param manager de manager die de query moet uitvoeren.
     */
    public static void PrintTotaleOmzetEnKorting(EntityManager manager) {
        try {
        Query query = manager.createQuery("SELECT SUM(totaal), SUM(korting) FROM Factuur");
        Object[] result = (Object[]) query.getSingleResult();

        System.out.println("Totale Omzet: " + result[0]);
        System.out.println("Totale Korting: " + result[1]);
        } catch (NoResultException e) {
            System.out.println("Er kon geen resultaat worden gevonden.");
        }
    }

    /**
     * Print de gemiddelde omzet en de gemiddelde korting
     * @param manager de manager die de query moet uitvoeren.
     */
    public static void PrintGemiddeldeOmzetEnKorting(EntityManager manager) {
        try {
            Query query = manager.createQuery("SELECT AVG(totaal), AVG(korting) FROM Factuur");
            Object[] result = (Object[]) query.getSingleResult();

            System.out.println("Gemiddelde Omzet: " + result[0]);
            System.out.println("Gemiddelde Korting: " + result[1]);
        } catch (NoResultException e) {
            System.out.println("Er kon geen resultaat worden gevonden.");
        }
    }

    /**
     * Print de omzet en korting voor het opgegeven factuurid
     * @param manager de manager die de query moet uitvoeren.
     * @param factuurid Het id van de factuur waar de omzet en korting voor opgevraagd moet worden.
     */
    public static void PrintOmzetEnKortingPerFactuur(EntityManager manager, long factuurid){
        if(factuurid < 1)
            throw new IllegalArgumentException("het meegegeven factuurid moet 1 of hoger zijn");

        try {
            Query query = manager.createQuery("SELECT totaal, korting FROM Factuur WHERE id = " + factuurid);
            Object[] result = (Object[]) query.getSingleResult();

            System.out.println("Omzet Factuur " + factuurid + ": " + result[0]);
            System.out.println("Korting Factuur " + factuurid + ": " + result[1]);
        } catch (NoResultException e) {
            System.out.println("Er kon geen resultaat worden gevonden.");
        }
    }

    /**
     * Print de 3 hoogste totalen van de facturen.
     * @param manager de manager die de query moet uitvoeren.
     */
    public static void PrintHoogsteOmzetFacturen(EntityManager manager){
        try {
            Query query = manager.createQuery("SELECT totaal FROM Factuur ORDER BY totaal DESC");
            query.setMaxResults(3);
            List<BigDecimal> resultList = query.getResultList();
            System.out.print("Top drie factuur omzetten: ");

            resultList.forEach(r -> {
                System.out.print(r + ", ");
            });

            System.out.println("");
        } catch (NoResultException e) {
            System.out.println("Er konden geen resultaat worden gevonden.");
        }
    }

    /**
     * @param manager de manager die de query moet uitvoeren.
     * @param artikelnaam de naam van het artikel waar de omzet en korting voor uitgeprint moet worden.
     */
    public static void GetTotaleOmzetEnKortingPerArtikel(EntityManager manager, String artikelnaam) {
        try {
            Query query = manager.createQuery("SELECT SUM(artikel_prijs), SUM(artikel_korting) FROM FactuurRegel GROUP BY artikel_naam HAVING artikel_naam = '" + artikelnaam + "'");
            Object[] result = (Object[]) query.getSingleResult();

            System.out.println("Omzet " + artikelnaam + ": " + result[0]);
            System.out.println("Korting " + artikelnaam + ": " + result[1]);
        } catch (NoResultException e) {
            System.out.println("Er konden geen resultaat worden gevonden.");
        }
    }

    /**
     * geeft de totale omzet van een artikel op een bepaalde datum.
     * @param manager de manager die de query moet uitvoeren.
     * @param artikelnaam de naam van het artikel waar de omzet en korting voor uitgeprint moet worden.
     * @param datum de specifieke datum waarbij het moet worden berekend
     */
    public static void GetTotaleOmzetEnKortingPerArtikelPerDatum(EntityManager manager, String artikelnaam, Datum datum) {
        try {
            Query query = manager.createQuery("SELECT f.datum, SUM(r.artikel_prijs), SUM(r.artikel_korting) FROM FactuurRegel r JOIN Factuur f ON r.factuur = f.id GROUP BY r.artikel_naam, f.datum HAVING r.artikel_naam = '" + artikelnaam + "' AND  f.datum = '" + datum.getDatumAsString() + "'");
            Object[] result = (Object[]) query.getSingleResult();

            System.out.println(artikelnaam + ": datum: " + result[0] + ", totale omzet: " + result[1] + ", totale korting: " + result[2]);

        } catch (NoResultException e) {
            System.out.println("Er konden geen resultaat worden gevonden.");
        }
    }

    /**
     * Geeft de 3 populairste artikelen op basis van gekochte aantalen.
     * @param manager de manager die de query moet uitvoeren.
     */
    public static void PrintPopulairsteArtikelen(EntityManager manager){
        try {
            Query query = manager.createQuery("SELECT artikel_naam, count(artikel_naam) FROM FactuurRegel GROUP BY artikel_naam ORDER BY count(artikel_naam) DESC");
            query.setMaxResults(3);
            List<Object[]> resultList = query.getResultList();
            System.out.print("Top drie producten: ");

            resultList.forEach(r -> {
                System.out.print(r[0] + ": " + r[1] + ", ");
            });

            System.out.println("");
        } catch (NoResultException e) {
            System.out.println("Er konden geen resultaat worden gevonden.");
        }
    }

    /**
     * Geeft de 3 populairste artikelen op basis van omzet.
     * @param manager de manager die de query moet uitvoeren.
     */
    public static void PrintPopulairsteArtikelenQuaOmzet(EntityManager manager){
        try {
            Query query = manager.createQuery("SELECT artikel_naam, SUM(artikel_prijs) FROM FactuurRegel GROUP BY artikel_naam ORDER BY SUM(artikel_prijs) DESC");
            query.setMaxResults(3);
            List<Object[]> resultList = query.getResultList();
            System.out.print("Top drie producten qua omzet: ");

            resultList.forEach(r -> {
                System.out.print(r[0] + ": " + r[1] + ", ");
            });

            System.out.println("");
        } catch (NoResultException e) {
            System.out.println("Er konden geen resultaat worden gevonden.");
        }
    }
}
