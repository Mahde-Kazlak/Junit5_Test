package main.najah.test;

import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Tests")
public class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Test Product", 100.0);
    }

    @Test
    @DisplayName("Test product construction with valid price")
    void testValidConstruction() {
        assertEquals("Test Product", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(0, product.getDiscount());
    }

    @Test
    @DisplayName("Test product construction with invalid price")
    void testInvalidConstruction() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Invalid", -10.0));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 100.0",
            "10, 90.0",
            "50, 50.0"
    })
    @DisplayName("Parameterized test for discounts")
    void testApplyDiscount(double discount, double expectedPrice) {
        product.applyDiscount(discount);
        assertEquals(discount, product.getDiscount());
        assertEquals(expectedPrice, product.getFinalPrice());
    }

    @Test
    @DisplayName("Test invalid discount application")
    void testInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-5.0));
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(51.0));
    }

    @Test
    @Timeout(1)
    @DisplayName("Test discount calculation performance")
    void testDiscountPerformance() {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(500), () -> {
            product.applyDiscount(25.0);
            assertEquals(75.0, product.getFinalPrice());
        });
    }

    @Test
    @DisplayName("Test multiple product operations")
    void testMultipleOperations() {
        product.applyDiscount(10.0);

        assertAll("product",
            () -> assertEquals("Test Product", product.getName()),
            () -> assertEquals(100.0, product.getPrice()),
            () -> assertEquals(10.0, product.getDiscount()),
            () -> assertEquals(90.0, product.getFinalPrice())
        );
    }
}