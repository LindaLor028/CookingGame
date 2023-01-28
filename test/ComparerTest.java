import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComparerTest {
    private Queue<String> dumplings, hamburger, inputDumpling, inputBurger, ingredientsRecipe, shortRecipe, duplicate;

    @BeforeEach
    public void setup(){
        // Standard Dumpling Template Recipe
        dumplings = new ArrayDeque<String>();
        dumplings.add("Ground Pork");dumplings.add("Minced Garlic");dumplings.add("Green Onion");dumplings.add("Soy Sauce");dumplings.add("Sesame Oil");dumplings.add("Wonton Wrapper");

        // Standard Hamburger Template Recipe
        hamburger = new ArrayDeque<String>();
        hamburger.add("Bread");hamburger.add("Beef");hamburger.add("Pickles");hamburger.add("Ketchup");hamburger.add("Bread");
        
        // Standard Input Dumpling Recipe
        inputDumpling = new ArrayDeque<String>();
        inputDumpling.add("Ground Pork");inputDumpling.add("Minced Garlic");inputDumpling.add("Green Onion");inputDumpling.add("Soy Sauce");inputDumpling.add("Sesame Oil");inputDumpling.add("Wonton Wrapper");

        // Standard Hamburger Template Recipe
        inputBurger = new ArrayDeque<String>();
        inputBurger.add("Bread");inputBurger.add("Beef");inputBurger.add("Pickles");inputBurger.add("Ketchup");inputBurger.add("Bread");

        // Recipe with Correct Ingredients Wrong Order 
        ingredientsRecipe = new ArrayDeque<String>();
        ingredientsRecipe.add("Minced Garlic"); ingredientsRecipe.add("Ground Pork"); ingredientsRecipe.add("Soy Sauce");  ingredientsRecipe.add("Wonton Wrapper"); ingredientsRecipe.add("Green Onion");ingredientsRecipe.add("Sesame Oil");

        // Recipe Cut Short
        shortRecipe = new ArrayDeque<String>();
        shortRecipe.add("Ground Pork");shortRecipe.add("Minced Garlic"); shortRecipe.add("Green Onion");

        // Recipe with correct order and ingredients but duplicates it twice
        duplicate = new ArrayDeque<String>();
        duplicate.add("Ground Pork");duplicate.add("Minced Garlic");duplicate.add("Green Onion");duplicate.add("Soy Sauce");duplicate.add("Sesame Oil");duplicate.add("Wonton Wrapper");
        duplicate.add("Ground Pork");duplicate.add("Minced Garlic");duplicate.add("Green Onion");duplicate.add("Soy Sauce");duplicate.add("Sesame Oil");duplicate.add("Wonton Wrapper");

        

    }

    /**
     * TEST 1 : testMatch
     * Tests a 100% match for the template recipes. 
     */
    @Test
    public void testMatch(){
        RecipeComparer comparer = new RecipeComparer(inputDumpling, dumplings);
        comparer.compareRecipes();

        RecipeComparer comparer2 = new RecipeComparer(inputBurger, hamburger);
        comparer2.compareRecipes();

        assertEquals(100.0, comparer.getScore());
        assertEquals(100.0, comparer2.getScore());
    }

    /**
     * TEST 2: testIngredientsOnly
     * Tests a correct list of ingredients, but an incorrect order for dumplings. 
     */
    @Test
    public void testIngredientsOnly(){
        RecipeComparer comparer = new RecipeComparer(ingredientsRecipe, dumplings);
        comparer.compareRecipes();
        assertEquals(80.0, comparer.getScore());

    }

    /**
     * TEST 3: testShort
     * Tests a list of correct ingredients, but cut short by half for dumplings. 
     */
    @Test
    public void testShort(){
        RecipeComparer comparer = new RecipeComparer(shortRecipe, dumplings);
        comparer.compareRecipes();

        assertEquals(50.0, comparer.getScore());
    }

    /**
     * TEST 4: testDouble
     * Tests a list of correct ingredients in the right order. But it doubles for dumplings.
     */
    @Test
    public void testDouble(){
        RecipeComparer comparer = new RecipeComparer(shortRecipe, dumplings);
        comparer.compareRecipes();

        assertEquals(50.0, comparer.getScore());
    }

    /**
     * TEST 5: test 0 matches
     */
    @Test
    public void TestZero(){
        RecipeComparer comparer = new RecipeComparer(dumplings, hamburger);
        comparer.compareRecipes();

        RecipeComparer comparer2 = new RecipeComparer(hamburger, shortRecipe);
        comparer2.compareRecipes();

        assertEquals(0, comparer.getScore());
        assertEquals(0, comparer2.getScore());
    }

    /**
     * TEST 6: test reverses 
     */
    @Test
    public void testReverse(){
        RecipeComparer comparer = new RecipeComparer(inputBurger, hamburger); 
        comparer.compareRecipes();

        RecipeComparer reverse = new RecipeComparer(hamburger, inputBurger);
        reverse.compareRecipes();

        RecipeComparer shortComp = new RecipeComparer(shortRecipe, dumplings);
        shortComp.compareRecipes();

        RecipeComparer revShort = new RecipeComparer(dumplings, shortRecipe);
        revShort.compareRecipes();

        assertEquals(50.0, shortComp.getScore());
        assertEquals(87.0, revShort.getScore());

        assertEquals(100, comparer.getScore());
        assertEquals(100, reverse.getScore());


    }
}
