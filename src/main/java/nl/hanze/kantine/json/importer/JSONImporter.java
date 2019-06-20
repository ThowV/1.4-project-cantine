package nl.hanze.kantine.json.importer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public final class JSONImporter {

    /**
     * Private Constructor
     */
    private JSONImporter() {}

    /**
     * Maakt een JSONObject doormiddel van een bestandspad
     * @param path Het pad naar een .json bestand.
     * @return geeft een JSONObject terug van het opgegeven bestand.
     */
    public static JSONObject getJSONObjectFromFile(String path) {
        return new JSONObject(getJSONStringFromFile(path));
    }

    /**
     * Maakt een JSONArray doormiddel van het opgegeven bestand
     * @param path het bestandspad van het .json bestand
     * @return geeft een JSONArray terug van het opgegeven .json bestand
     */
    public static JSONArray getJSONArrayFromFile(String path) {
        return new JSONArray(getJSONStringFromFile(path));
    }

    /**
     * Geeft het opgegeven JSON bestand terug als een string.
     * @param path het pad naar het .json bestand.
     * @return Het opgegeven JSON bestand als een string.
     */
    private static String getJSONStringFromFile(String path) {
        Scanner scanner;
        InputStream in = inputStreamFromFile(path);
        scanner = new Scanner(in);
        String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
        closeInputStream(in);

        return json;
    }

    /**
     * Maakt een inputstream met het bestand op het opgegeven pad.
     * @param path het pad naar het bestand waar een inputstream gemaakt voor moet worden.
     * @return geeft een inputstream terug van het bestand op het opgegeven pad.
     */
    private static InputStream inputStreamFromFile(String path) {
        try {
            InputStream inputStream = JSONImporter.class.getResourceAsStream(path);
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sluit een inputstream
     * @param inputStream de inputstream die gesloten moet worden.
     */
    private static void closeInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
