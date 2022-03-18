
package edu.hitsz.aircraft;

import java.util.Random;

public class EnemySelector {
    private static String[] enemyTypes = { "mob", "elite", "boss" };
    private static final Random random = new Random();

    public static String selectoString_easy() {
        return enemyTypes[(int) (random.nextInt(enemyTypes.length-1))];

    }

}
