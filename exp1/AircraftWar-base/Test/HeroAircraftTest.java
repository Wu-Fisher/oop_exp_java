import edu.hitsz.aircraft.HeroAircraft;
import org.junit.platform.engine.support.descriptor.FileSystemSource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    HeroAircraft myHeroAircraft=HeroAircraft.getInstance(
                0,0,0,0,100,2,30
    );

    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test HeroAircraft--");
    }
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.out.println("--One Test Executed--");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.out.println("--One Test Finished--");
    }
    @org.junit.jupiter.api.AfterAll
    static void afterAll() {
        System.out.println("--Test HeroAircraft Finished--");
    }

    @org.junit.jupiter.api.Test
    void decreaseHp() {
        System.out.println("test:decreaseHp");
        myHeroAircraft.setHp(myHeroAircraft.getMaxHp());
        myHeroAircraft.decreaseHp(2*myHeroAircraft.getMaxHp());
        assertFalse(myHeroAircraft.getHp()<0);
        System.out.println("test success");
    }

    @org.junit.jupiter.api.Test
    void recoverHp() {
        System.out.println("test:recoverHp");
        myHeroAircraft.setHp(1);
        myHeroAircraft.recoverHp(2*myHeroAircraft.getMaxHp());
        assertFalse(myHeroAircraft.getHp()>myHeroAircraft.getMaxHp());
        System.out.println("test success");
    }

//    @org.junit.jupiter.api.Disabled()
//    void getHp() {
//    }

    @org.junit.jupiter.api.Test
    void getInstance() {
       System.out.println("test:getSingleInstance");
        HeroAircraft myHeroAircraft2 = HeroAircraft.getInstance(0,0,0,0,100,2,30);
        assertEquals(myHeroAircraft,myHeroAircraft2);
        System.out.println("test success");
    }


//    @org.junit.jupiter.api.Disabled
//    void forward() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void crash() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getLocationX() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getLocationY() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void setLocation() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void setSpeedX() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getSpeedY() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getSpeedX() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getImage() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getWidth() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void getHeight() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void notValid() {
//    }
//
//    @org.junit.jupiter.api.Disabled
//    void vanish() {
//    }
//
//
//
//    @org.junit.jupiter.api.Disabled
//    void Forward() {
//    }

    @org.junit.jupiter.api.Disabled("waiting for the new version")
    void shoot() {
    }
}