package edu.hitsz.aircraft;

// import edu.hitsz.application.Game;
// import edu.hitsz.application.Main;
import edu.hitsz.application.*;

public class HardFactory extends EnemyFactory {

    int count=0;
    @Override
    AbstractAircraft createEnemy(String type) {
        AbstractAircraft enemy = null;
        if (type.equals("mob")) {
            enemy = new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    0,
                    6,
                    120);

        } else if (type.equals("elite")) {
            enemy = new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    0,
                    5,
                    360, 2, 60);
        } else if (type.equals("boss")) {
            enemy = new BossEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    4,
                    0,
                    8000, 3, 40);
        }
        return enemy;
    }
    @Override
    AbstractAircraft createEnemy(String type, double damageTimes, double healthTimes) {
        AbstractAircraft enemy = null;
        if (type.equals("mob")) {
            enemy = new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    0,
                    7,
                    (int)(120*healthTimes));

        } else if (type.equals("elite")) {
            enemy = new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    0,
                    5,
                    (int)(360*healthTimes), 2, (int)(60*damageTimes));
        } else if (type.equals("boss")) {
            enemy = new BossEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    4,
                    0,
                    (int)((8000+count*500)*healthTimes), 3, (int)(40*damageTimes));
            count++;
            if(count!=0)
            {
                System.out.println("BOSS机随数量血量增加");
            }
        }
        return enemy;
    }
}