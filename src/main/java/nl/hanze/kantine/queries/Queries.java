package nl.hanze.kantine.queries;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class Queries {
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
}
