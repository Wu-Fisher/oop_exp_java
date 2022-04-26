package edu.hitsz.aircraft;

import edu.hitsz.application.*;

/**
 * @author Wufisher
 */
public class EasyFactory extends EnemyFactory {

    @Override
    AbstractAircraft createEnemy(String type) {
        AbstractAircraft enemy = null;
        switch (type) {
            case "mob":
                enemy = new MobEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                        0,
                        4,
                        60);
                break;
            case "elite":
                enemy = new EliteEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1),
                        0,
                        3,
                        120, 1, 20);
                break;
            case "boss":
                enemy = new BossEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1),
                        2,
                        0,
                        2000, 1, 20);
                break;
            default:
                break;
        }
        return enemy;
    }
    @Override
     AbstractAircraft createEnemy(String type, double damageTimes, double healthTimes)
    {
        return createEnemy(type);
    };
}
