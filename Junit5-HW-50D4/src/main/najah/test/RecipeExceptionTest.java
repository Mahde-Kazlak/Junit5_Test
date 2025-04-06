package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import main.najah.code.RecipeException;

@DisplayName("RecipeException Tests")
public class RecipeExceptionTest {

    @Test
    @DisplayName("Throw RecipeException manually")
    void testRecipeException() {
        Exception exception = assertThrows(RecipeException.class, () -> {
            throw new RecipeException("Invalid input");
        });

        assertEquals("Invalid input", exception.getMessage());
    }
}