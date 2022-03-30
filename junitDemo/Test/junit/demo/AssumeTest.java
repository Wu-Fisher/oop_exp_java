package junit.demo;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
public class AssumeTest {
    @Test
    void testAssumeTrue() {
//如果假设传入的值为 True，那么就会执行后面测试，否则直接停止执行assumeTrue(false);
        System.out.println("This will not be implemented.");
        assertEquals("Hello", "Hello2");
    }
    @Test
    void testAssumeFalse() {
//如果假设传入的值为 False，那么就会执行后面测试，否则直接停止执行assumeFalse(false);
        System.out.println("This will be implemented.");
        assertEquals("Hello", "Hello2");
    }
    @Test
    @DisplayName("test executes only on Saturday")
    public void testAssumeTrueSaturday() {
        LocalDateTime dt = LocalDateTime.now();
        assumeTrue(dt.getDayOfWeek().getValue() == 6);
        System.out.println("further code will execute only if above assumption holds true");
    }
}
