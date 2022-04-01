import edu.hitsz.aircraft.*;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
/**
 * The type Elite enemy test.
 */
class EliteEnemyTest {
    /**
     * The Elite enemy.
     */
    EliteEnemy eliteEnemy;

    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test EliteAircraft--");
    }

    /**
     * Sets up.
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        eliteEnemy = new EliteEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                0,
                6,
                60, 2, 10);
        System.out.println("--One Test Executed--");
    }

    /**
     * Tear down.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {

        eliteEnemy= null;
        System.out.println("--One Test Finished--");
    }

    /**
     * After all.
     */
    @org.junit.jupiter.api.AfterAll
    static void afterAll() {
        System.out.println("--Test EliteAircraft Finished--");
    }

    /**
     * vanish
     * 判断是否反应一个敌机有效或无效
     */
    @Test
    void vanish() {
        System.out.println("--Test EliteAircraft notValid--");
        System.out.println("判断刚刚生成正常敌机是否为存活：");
        assertFalse(eliteEnemy.notValid());
        System.out.println("判断被击毁的敌机是否为存活：");
        eliteEnemy.vanish();
        assertTrue(eliteEnemy.notValid());
        System.out.println("--Test Success--");
    }

    /**
     * Forward.
     * 判断敌机移动到边界后是否会自动失效
     */
    @Test
    void forward() {
        System.out.println("--Test EliteAircraft forward--");
        System.out.println("判断敌机移动到边界后是否会自动失效：");
        eliteEnemy.setLocation(0,Main.WINDOW_HEIGHT+100);
        eliteEnemy.forward();
        assumeTrue(eliteEnemy.notValid());
        System.out.println("--Test Success--");
    }

    /**
     * Shoot.
     * 判断敌机发射的子弹速度方向是否正确
     */
    @Test
    void shoot() {
        System.out.println("--Test EliteAircraft shoot--");
        System.out.println("判断敌机是否正常发射子弹：");
        List<BaseBullet> enemyBullets = new LinkedList<>(eliteEnemy.shoot());
        assumeFalse(enemyBullets.isEmpty());
        System.out.println("判断敌机发射的子弹速度方向是否正确：");
        for(BaseBullet bullet:enemyBullets){
            assumeFalse(bullet.getSpeedY()<=0);
        }
        System.out.println("--Test Success--");
    }
}