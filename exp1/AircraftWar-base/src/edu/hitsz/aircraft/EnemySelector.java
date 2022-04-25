
package edu.hitsz.aircraft;

import java.util.Random;

public class EnemySelector {
    private static String[] enemyTypes = { "mob", "elite", "boss" };
    private static final Random random = new Random();

    public static String selectoString() {
        int a = random.nextInt(10);
        if (a < 3) {
            return enemyTypes[1];
        }
        else {
            return enemyTypes[0];
        }
    }

}
