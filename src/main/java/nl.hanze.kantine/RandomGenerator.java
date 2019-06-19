package nl.hanze.kantine;

import java.util.Random;

public class RandomGenerator {
    public static Random random = new Random();

    /**
     * Methode om een random getal tussen min(incl)
     * en max(incl) te genereren.
     * @param min Integer dat minimum aangeeft.
     * @param max Integer dat maximum aangeeft.
     * @return Een random integer.
     */
    public static int getRandomValue(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    /**
     * Methode om een random getal tussen min(incl)
     * en max(incl) te genereren.
     * @param min Double dat minimum aangeeft.
     * @param max Double dat maximum aangeeft.
     * @return Een random double.
     */
    public static double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Methode om een array van random getallen liggend tussen
     * min en max van de gegeven lengte te genereren.
     * @param lengte Lengte van de array.
     * @param min Integer dat minimum aangeeft.
     * @param max Integer dat maximum aangeeft.
     * @return Een array met random getallen.
     */
    public static int[] getRandomArray(int lengte, int min, int max) {
        int[] temp = new int[lengte];
        for(int i = 0; i < lengte ;i++) {
            temp[i] = RandomGenerator.getRandomValue(min, max);
        }

        return temp;
    }

    /**
     * @param lengte de lengte van de boolean array die terug moet worden gegeven.
     * @param minimaalProductenMetKorting het minimaal aantal producten dat zowiezo korting op zich heeft.
     * @return geeft een random boolean array terug met het opgegeven lengte.
     */
    public static boolean[] getRandomBooleanArray(int lengte, int minimaalProductenMetKorting) {
        if(minimaalProductenMetKorting > lengte)
            throw new IllegalArgumentException("Het minimaalProductenMetKorting moet midner zijn dan de lengte");

        boolean[] temp = new boolean[lengte];
        for (int i = 0; i < lengte; i++) {
            temp[i] = (i <= minimaalProductenMetKorting) || random.nextBoolean();
        }

        return shuffleBooleanArray(temp);
    }

    /**
     * Fisher–Yates shuffle array function
     * @param array the array to shuffle
     */
    private static boolean[] shuffleBooleanArray(boolean[] array)
    {
        boolean[] shuffledArray = array;

        int index;
        Random random = new Random();
        for (int i = shuffledArray.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                shuffledArray[index] ^= shuffledArray[i];
                shuffledArray[i] ^= shuffledArray[index];
                shuffledArray[index] ^= shuffledArray[i];
            }
        }

        return shuffledArray;
    }
}
