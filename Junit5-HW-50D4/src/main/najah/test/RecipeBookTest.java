package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.*;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("RecipeBook Tests")
public class RecipeBookTest {

    RecipeBook recipeBook;
    Recipe recipe;

    @BeforeEach
    void setUp() throws RecipeException {
        recipeBook = new RecipeBook();
        recipe = new Recipe();
        recipe.setName("Espresso");
        recipe.setPrice("2");
        recipe.setAmtCoffee("3");
    }

    @Test
    @DisplayName("Test initial recipe array is empty")
    void testInitialState() {
        Recipe[] recipes = recipeBook.getRecipes();
        for (Recipe r : recipes) {
            assertNull(r);
        }
    }

    @Test
    @DisplayName("Add recipe to book returns true")
    void testAddRecipe() {
        assertTrue(recipeBook.addRecipe(recipe));
    }

    @Test
    @DisplayName("Add duplicate recipe returns false")
    void testAddDuplicateRecipe() {
        recipeBook.addRecipe(recipe);
        assertFalse(recipeBook.addRecipe(recipe));
    }

    @Test
    @DisplayName("Add recipe to full book returns false")
    void testAddToFullBook() throws RecipeException {

        for (int i = 0; i < 4; i++) {
            Recipe r = new Recipe();
            r.setName("Recipe " + i);
            assertTrue(recipeBook.addRecipe(r));
        }

        assertFalse(recipeBook.addRecipe(recipe));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    @DisplayName("Delete recipe at valid positions")
    void testDeleteRecipeValidPositions(int position) throws RecipeException {
        Recipe r = new Recipe();
        r.setName("Recipe " + position);
        recipeBook.addRecipe(r);
        assertEquals("Recipe " + position, recipeBook.deleteRecipe(position));
    }

    @Test
    @DisplayName("Delete recipe at invalid position returns null")
    void testDeleteRecipeInvalidPosition() {
        assertNull(recipeBook.deleteRecipe(-1));
        assertNull(recipeBook.deleteRecipe(4));
    }

    @Test
    @DisplayName("Edit recipe returns old name")
    void testEditRecipe() throws RecipeException {
        recipeBook.addRecipe(recipe);
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Cappuccino");
        assertEquals("Espresso", recipeBook.editRecipe(0, newRecipe));
    }

    @Test
    @DisplayName("Edit recipe at invalid position returns null")
    void testEditRecipeInvalidPosition() throws RecipeException {
        Recipe newRecipe = new Recipe();
        newRecipe.setName("Cappuccino");
        assertNull(recipeBook.editRecipe(-1, newRecipe));
        assertNull(recipeBook.editRecipe(4, newRecipe));
    }

    @Test
    @DisplayName("Get recipes returns array of correct size")
    void testGetRecipes() {
        Recipe[] recipes = recipeBook.getRecipes();
        assertEquals(4, recipes.length);
    }

    @Test
    @DisplayName("Test concurrent access to recipe book")
    void testConcurrentAccess() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            assertTrue(recipeBook.addRecipe(recipe));
        });

        Thread t2 = new Thread(() -> {
            Recipe r = new Recipe();
            r.setName("Latte");
            assertTrue(recipeBook.addRecipe(r));
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(2, countRecipes(recipeBook.getRecipes()));
    }

    private int countRecipes(Recipe[] recipes) {
        int count = 0;
        for (Recipe r : recipes) {
            if (r != null) count++;
        }
        return count;
    }
}