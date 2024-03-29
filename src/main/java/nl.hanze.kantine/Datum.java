package nl.hanze.kantine;

public class Datum {

    private int dag;
    private int maand;
    private int jaar;

    private int[] februari = {28, 29};
    private int[] dagInMaand = {31, februari[0], 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final String[] WEEK_DAGEN = { "Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag", "Zondag"};

    public Datum() {
        dag = maand = jaar = 0;
    }

    public Datum(int dag, int maand, int jaar) {
        this(); //Zet de standaard waarden

        if(bestaatDatum(dag, maand, jaar)) {
            this.dag = dag;
            this.maand = maand;
            this.jaar = jaar;
        }
    }


    public boolean bestaatDatum(int dag, int maand, int jaar) {
        int laasteDagFebruari = 28;

        //Kijk of de gegeven datum binnen de randvoorwaarden ligt
        if(dag >= 1 && maand >= 1 && maand <= 12 && jaar >= 1900 && jaar <= 2100) {
            //Kijk of het gegeven jaar een schrikkeljaar is
            if(isSchrikkeljaar(jaar))
                laasteDagFebruari = 29;

            //Kijk of de gegeven dag mogelijk is voor het gegeven jaar
            if(dag <= dagInMaand[maand - 1])
                return true;
        }

        return false;
    }

    /**
     * Kijkt of het gegeven jaar een schrikkeljaar is
     * @param jaar Het gegeven jaar
     */
    private static boolean isSchrikkeljaar(int jaar) {
        return ((jaar % 4 == 0) && (jaar % 100 != 0 || jaar % 400 == 0));
    }

    /**
     * Getter voor Sting weergave van datum
     * @return Geboortedatum
     */
    public String getDatumAsString() {
        return jaar + "-" + maand + "-" + dag;
    }

    public int getDag() {
        return dag;
    }

    public void setDag(int dag) {
        this.dag = dag;
    }

    public int getMaand() {
        return maand;
    }

    public void setMaand(int maand) {
        this.maand = maand;
    }

    public int getJaar() {
        return jaar;
    }

    public void setJaar(int jaar) {
        this.jaar = jaar;
    }

    public static String getWeekDagAsString(int weekDag){
        if(weekDag < 7)
            return WEEK_DAGEN[weekDag];
        else
            throw new IllegalArgumentException("De weekdag moet tussen 0-6 liggen.");
    }
}