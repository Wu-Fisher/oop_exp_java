package edu.hitsz.aircraft;

// import edu.hitsz.application.Game;
// import edu.hitsz.application.Main;
import edu.hitsz.application.*;

public class HardFactory extends EnemyFactory {

    @Override
    AbstractAircraft createEnemy(String type) {
        AbstractAircraft enemy = null;
        if (type.equals("mob")) {
            enemy = new MobEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2) * 1,
                    0,
                    8,
                    60);

        } else if (type.equals("elite")) {
            enemy = new EliteEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    0,
                    8,
                    110, 3, 30);
        } else if (type.equals("boss")) {
            enemy = new BossEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                    4,
                    0,
                    1000, 6, 40);
        }
        return enemy;
    }
}