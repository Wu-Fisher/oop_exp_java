package junit.demo;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
class CalculatorTest {
    private Calculator calculator;
    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        calculator = new Calculator();
    }
    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        calculator = null;
    }
    @DisplayName("Test add method")
    @Test
    void add() {
        System.out.println("**--- Test add method executed ---**");
        assertEquals(10,calculator.add(8, 2));
    }
    @DisplayName("Test sub method")
    @Test
    void sub() {
        System.out.println("**--- Test sub method executed ---**");
        assertEquals(6,calculator.sub(8, 2));
    }
    @DisplayName("Test mul method")
    @Test
    void mul() {
        System.out.println("**--- Test mul method executed ---**");
        assertEquals(16,calculator.mul(8, 2));
    }
    @DisplayName("Test div method")
    @Test
    void div() {
        System.out.println("**--- Test div method executed ---**");
        assertEquals(4,calculator.div(8, 2));
    }
    @Test
    @Disabled("implementation pending")
    void div2() {
    }
    @AfterAll
    static void afterAll() {
        System.out.println("**--- Executed once after all test methods in this class ---**");
    }
}
