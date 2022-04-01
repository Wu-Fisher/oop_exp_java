import edu.hitsz.props.BloodProps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.hitsz.aircraft.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
/**
 * The type Blood props test.
 */
class BloodPropsTest {

    /**
     * The Blood props.
     */
    BloodProps bloodProps;

    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test BloodProps--");
    }

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        System.out.println("--One Test Executed--");
        bloodProps = new BloodProps(50, 50,0,0);
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown() {
        bloodProps = null;
        System.out.println("--One Test Finished--");
    }

    /**
     * Crash.
     * 测试是否检测到正常碰撞
     */
    @Test
    void crash() {
        System.out.println("--Test crash--");
        System.out.println("检测是否能和相同位置敌机碰撞：");
        HeroAircraft hero = HeroAircraft.getInstance(50,50,0,0,100);
        boolean t=bloodProps.crash(hero);
        assumeTrue(t);
        System.out.println("测试是否能和不同位置的英雄机碰撞：");
        hero.setLocation(300,300);
        t=bloodProps.crash(hero);
        assumeFalse(t);
        System.out.println("--Test crash Finished--");
    }

    /**
     * Effect crash.
     * 测试碰撞后是否正确生效
     */
    @Test
    void effectCrash() {
        System.out.println("--Test effectCrash--");
        System.out.println("检测碰撞后的函数生效：");
        bloodProps.effectCrash();
        System.out.println("--Test effectCrash Finished--");
    }
}