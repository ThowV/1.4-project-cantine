package kantine;

import java.util.Random;

public class RandomGenerator {
    public static Random random = new Random();

    /**
     * Methode om een random getal tussen min(incl)
     * en max(incl) te genereren.
     * @param min Integer dat minimum aangeeft
     * @param max Integer dat maximum aangeeft
     * @return Een random integer
     */
    public static int getRandomValue(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    /**
     * Methode om een random getal tussen min(incl)
     * en max(incl) te genereren.
     * @param min Double dat minimum aangeeft
     * @param max Double dat maximum aangeeft
     * @return Een random double
     */
    public static double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
