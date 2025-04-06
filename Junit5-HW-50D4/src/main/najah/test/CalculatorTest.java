package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.Calculator;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Calculator Tests")
@Execution(ExecutionMode.CONCURRENT)
public class CalculatorTest {

    Calculator calc;

    @BeforeAll
    static void initAll() {
        System.out.println("===> Starting Calculator tests...");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("--> Test setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--> Test complete");
    }

    @AfterAll
    static void doneAll() {
        System.out.println("===> All Calculator tests finished.");
    }

    @Test
    @Order(1)
    @DisplayName("Addition with multiple numbers")
    void testAddMultipleNumbers() {
        assertAll("Addition tests",
            () -> assertEquals(6, calc.add(1, 2, 3)),
            () -> assertEquals(0, calc.add()),
            () -> assertEquals(5, calc.add(5)),
            () -> assertEquals(15, calc.add(1, 2, 3, 4, 5))
        );
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource({
        "10, 2, 5",
        "0, 5, 0",
        "-10, 2, -5",
        "10, -2, -5"
    })
    @DisplayName("Division with valid inputs")
    void testDivisionValidInputs(int a, int b, int expected) {
        assertEquals(expected, calc.divide(a, b));
    }

    @Test
    @Order(3)
    @DisplayName("Division by zero throws exception")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(ints = {0, 1, 2, 3, 5, 10})
    @DisplayName("Factorial of non-negative numbers")
    void testFactorialNonNegative(int num) {
        assertTrue(calc.factorial(num) >= 1);
    }

    @Test
    @Order(5)
    @DisplayName("Factorial of zero is one")
    void testFactorialZero() {
        assertEquals(1, calc.factorial(0));
    }

    @Test
    @Order(6)
    @DisplayName("Factorial of negative number throws exception")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }

    @Test
    @Order(7)
    @Timeout(1)
    @DisplayName("Performance test for factorial")
    void testFactorialPerformance() {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(500), () -> {
            assertEquals(3628800, calc.factorial(10));
        });
    }

    @Test
    @Order(8)
    @DisplayName("Edge case for maximum factorial")
    void testFactorialEdgeCase() {
        assertDoesNotThrow(() -> calc.factorial(12));
    }

    @Test
    @Order(9)
    @Disabled("Known issue: factorial of 13 causes integer overflow")
    @DisplayName("Failing test for factorial overflow (disabled)")
    void testFactorialOverflow() {
        assertTrue(calc.factorial(13) > 0);
   }
}