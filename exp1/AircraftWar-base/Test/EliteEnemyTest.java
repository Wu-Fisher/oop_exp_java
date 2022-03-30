import edu.hitsz.aircraft.*;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {
    EliteEnemy eliteEnemy;
    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test EliteAircraft--");
    }
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        eliteEnemy = new EliteEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.1) * 1,
                0,
                6,
                60, 2, 10);
        System.out.println("--One Test Executed--");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

        eliteEnemy= null;
        System.out.println("--One Test Finished--");
    }
    @org.junit.jupiter.api.AfterAll
    static void afterAll() {
        System.out.println("--Test EliteAircraft Finished--");
    }
    @Test
    void notValid() {
        System.out.println("--Test EliteAircraft notValid--");
        assertFalse(eliteEnemy.notValid());
        System.out.println("--Test Success--");
    }

    @Test
    void forward() {
        System.out.println("--Test EliteAircraft forward--");
        eliteEnemy.setLocation(0,Main.WINDOW_HEIGHT+100);
        eliteEnemy.forward();
        assertTrue(eliteEnemy.notValid());
        System.out.println("--Test Success--");
    }

    @Test
    void shoot() {
        System.out.println("--Test EliteAircraft shoot--");
        List<BaseBullet>  enemyBullets = new LinkedList<>();
        enemyBullets.addAll(eliteEnemy.shoot());
        for(BaseBullet bullet:enemyBullets){
            assertFalse(bullet.getSpeedY()<=0);
        }
        System.out.println("--Test Success--");
    }
}