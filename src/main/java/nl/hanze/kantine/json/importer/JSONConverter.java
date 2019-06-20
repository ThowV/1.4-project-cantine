package nl.hanze.kantine.json.importer;

import nl.hanze.kantine.Artikel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;

public final class JSONConverter {
    private JSONConverter() {}

    private static final String ARTIKEL_JSON_PATH = "/collections/artikel.json";

    public static Artikel[] GenerateArtikelen() {
        JSONArray array = JSONImporter.getJSONArrayFromFile(ARTIKEL_JSON_PATH);
        Artikel[] artikelen = new Artikel[array.length()];

        for(int i = 0; i < array.length(); i++)
        {
            JSONObject artikel = array.getJSONObject(i);
            artikelen[i] = new Artikel(artikel.getString("naam"), artikel.getBigDecimal("prijs").setScale(2, BigDecimal.ROUND_HALF_EVEN));
        }

        return artikelen;
    }
}
