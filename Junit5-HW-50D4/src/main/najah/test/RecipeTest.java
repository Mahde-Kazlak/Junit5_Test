package main.najah.test;

import main.najah.code.Recipe;
import main.najah.code.RecipeException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Recipe Tests")
public class RecipeTest {
    
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
    }

    @Test
    @DisplayName("Test default constructor values")
    void testDefaultConstructor() {
        assertEquals("", recipe.getName());
        assertEquals(0, recipe.getPrice());
        assertEquals(0, recipe.getAmtCoffee());
        assertEquals(0, recipe.getAmtMilk());
        assertEquals(0, recipe.getAmtSugar());
        assertEquals(0, recipe.getAmtChocolate());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "10", "100"})
    @DisplayName("Test valid coffee amounts")
    void testValidCoffeeAmounts(String amount) throws RecipeException {
        recipe.setAmtCoffee(amount);
        assertEquals(Integer.parseInt(amount), recipe.getAmtCoffee());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "invalid", "1.5"})
    @DisplayName("Test invalid coffee amounts")
    void testInvalidCoffeeAmounts(String amount) {
        assertThrows(RecipeException.class, () -> recipe.setAmtCoffee(amount));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "10", "100"})
    @DisplayName("Test valid milk amounts")
    void testValidMilkAmounts(String amount) throws RecipeException {
        recipe.setAmtMilk(amount);
        assertEquals(Integer.parseInt(amount), recipe.getAmtMilk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "10", "100"})
    @DisplayName("Test valid sugar amounts")
    void testValidSugarAmounts(String amount) throws RecipeException {
        recipe.setAmtSugar(amount);
        assertEquals(Integer.parseInt(amount), recipe.getAmtSugar());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "10", "100"})
    @DisplayName("Test valid chocolate amounts")
    void testValidChocolateAmounts(String amount) throws RecipeException {
        recipe.setAmtChocolate(amount);
        assertEquals(Integer.parseInt(amount), recipe.getAmtChocolate());
    }

    @ParameterizedTest
    @CsvSource({
            "Coffee, 5, 3, 1, 0, 0",
            "Latte, 4, 2, 2, 2, 0",
            "Mocha, 6, 3, 1, 1, 2"
    })
    @DisplayName("Test full recipe configuration")
    void testFullRecipeConfiguration(String name, String price, String coffee, 
                                   String milk, String sugar, String chocolate) 
                                   throws RecipeException {
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        
        assertAll("recipe",
            () -> assertEquals(name, recipe.getName()),
            () -> assertEquals(Integer.parseInt(price), recipe.getPrice()),
            () -> assertEquals(Integer.parseInt(coffee), recipe.getAmtCoffee()),
            () -> assertEquals(Integer.parseInt(milk), recipe.getAmtMilk()),
            () -> assertEquals(Integer.parseInt(sugar), recipe.getAmtSugar()),
            () -> assertEquals(Integer.parseInt(chocolate), recipe.getAmtChocolate())
        );
    }

    @Test
    @DisplayName("Test hashCode implementation")
    void testHashCode() {
        Recipe r1 = new Recipe();
        r1.setName("Coffee");
        
        Recipe r2 = new Recipe();
        r2.setName("Coffee");
        
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    @DisplayName("Test equals with different class")
    void testEqualsWithDifferentClass() {
        assertNotEquals(recipe, new Object());
    }

    @Test
    @DisplayName("Test equals with null")
    void testEqualsWithNull() {
        assertNotEquals(recipe, null);
    }

    @Test
    @DisplayName("Test equals with same object")
    void testEqualsWithSameObject() {
        assertEquals(recipe, recipe);
    }

    @Test
    @Timeout(1)
    @DisplayName("Test recipe operations performance")
    void testPerformance() throws RecipeException {
        assertTimeoutPreemptively(java.time.Duration.ofMillis(500), () -> {
            for (int i = 0; i < 100; i++) {
                recipe.setAmtCoffee("1");
                recipe.getAmtCoffee();
            }
        });
    }
}