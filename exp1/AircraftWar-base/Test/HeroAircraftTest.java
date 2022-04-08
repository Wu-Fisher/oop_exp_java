
import edu.hitsz.aircraft.HeroAircraft;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Hero aircraft test.
 */
class HeroAircraftTest {

    /**
     * The My hero aircraft.
     */
    HeroAircraft myHeroAircraft=HeroAircraft.getInstance(
                0,0,0,0,100,2,30
    );

    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test HeroAircraft--");
    }

    /**
     * Sets up.
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("--One Test Executed--");
    }

    /**
     * Tear down.
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("--One Test Finished--");
    }

    /**
     * After all.
     */
    @org.junit.jupiter.api.AfterAll
    static void afterAll() {
        System.out.println("--Test HeroAircraft Finished--");
    }

    /**
     * Decrease hp.
     * 测试减血函数
     */
    @org.junit.jupiter.api.Test
    void decreaseHp() {
        System.out.println("test:decreaseHp");
        System.out.println("判断减血函数是否导致数据异常：");
        myHeroAircraft.setHp(myHeroAircraft.getMaxHp());
        myHeroAircraft.decreaseHp(2*myHeroAircraft.getMaxHp());
        assertFalse(myHeroAircraft.getHp()<0);
        System.out.println("test success");
    }

    /**
     * Recover hp.
     * 测试加血函数
     */
    @org.junit.jupiter.api.Test
    void recoverHp() {
        System.out.println("test:recoverHp");
        System.out.println("判断加血函数是否导致数据异常：");
        myHeroAircraft.setHp(1);
        myHeroAircraft.recoverHp(2*myHeroAircraft.getMaxHp());
        assertFalse(myHeroAircraft.getHp()>myHeroAircraft.getMaxHp());
        System.out.println("test success");
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
       System.out.println("test:getSingleInstance");
       System.out.println("判断单例模式是否正确生效：");
        HeroAircraft myHeroAircraft2 = HeroAircraft.getInstance(0,0,0,0,100,2,30);
        assertEquals(myHeroAircraft,myHeroAircraft2);
        System.out.println("test success");
    }


}