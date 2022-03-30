import edu.hitsz.props.BloodProps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodPropsTest {

    BloodProps bloodProps;
    @BeforeAll
    static void beforeAll() {
        System.out.println("--Test BloodProps--");
    }
    @BeforeEach
    void setUp() {
        System.out.println("--One Test Executed--");
        bloodProps = new BloodProps(50, 50,0,0);
    }

    @AfterEach
    void tearDown() {
        bloodProps = null;
        System.out.println("--One Test Finished--");
    }

    @Test
    void crash() {
    }

    @Test
    void effectCrash() {
    }
}