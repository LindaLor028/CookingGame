import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;
/**
 * The GamePage class is in charge of creating a page that is uniquely itself because it prints all
 * of the FoodButtons and handles the User Input. 
 * 
 * Authors: Rita Li & Linda Lor
 */
public class GamePage {
    
    private String recipeName;
    private CanvasWindow canvas;
    private Button confirmButton;
    private FoodButtonHandler fButtonHandler; 
    private Queue<String> input, templateRecipe;
    private double score;
    private Food[] ingredientNames ;

    /**
     * A standard constructor. Each GamePage must have a canvas, recipeName, confirm button, and set Map of recipes to reference. 
     * @param recipeName
     * @param canvas
     * @param confirmButton
     * @param recipes
     */
    public GamePage(String recipeName, CanvasWindow canvas, Button confirmButton, Map<String, Queue<String>> recipes){
        generateIngredients();
        this.recipeName = recipeName;
        this.canvas = canvas;
        this.confirmButton = confirmButton;
        this.fButtonHandler= new FoodButtonHandler(canvas);
        this.input = new ArrayDeque<>();

        templateRecipe = recipes.get(recipeName);

        setUp();
    }

    /**
     * The setUp method sets up how a general Game Page should look like (printed FoodButtons and respective text 
     * depending on the level of users).
     */
    public void setUp(){
        canvas.removeAll();

        Image bg = new Image("PngLevel.png");
        bg.setMaxHeight(CookingGame.DEFAULT_HEIGHT);
        bg.setMaxWidth(CookingGame.DEFAULT_WIDTH);
        canvas.add(bg);
        GraphicsText text = new GraphicsText(recipeName);
        text.setFont(FontStyle.BOLD, 35);
        canvas.add(text,210,140);

        fButtonHandler.printIngredients(ingredientNames,50,50);


        canvas.onClick(event ->
        {
            FoodButton clicked = fButtonHandler.onClick(event.getPosition().getX(), event.getPosition().getY());
            if (clicked != null){
                input.add(clicked.getName());
            }
        });

        // add the confirm button to the canvas
        canvas.add(confirmButton,400,600);
    }

    /**
     * The removeAll method removes all items on the canvas. This method is needed because canvas cannot remove
     * FoodButtons respectively, so the FoodButtonHandler's method deleteAllButtons() method is called. 
     */
    public void removeAll(){
        fButtonHandler.deleteAllButtons();
    }

    /**
     * Calculates the accuracy of the user's input recipe to the original recipe. 
     * The score is given as a ratio in percentages. 
     * Additional algorithm for the score can be found in the README.
     * @return
     */
    public double calculateScore(){
        RecipeComparer comparer = new RecipeComparer(input, templateRecipe);
        comparer.compareRecipes();
        score = comparer.getScore();
        return score;
    }

    /**
     * The printCorrectRecipe prints the correct recipe on the Score page (if the user does not score a 100%).
     * @return
     */
    public List<String> printCorrectRecipe(){
        List<String> correctRecipe = new ArrayList<String>();
        for(String ingredient: templateRecipe){
            for(Food food: ingredientNames){
                if(food.getFoodName().equals(ingredient)){
                    correctRecipe.add(food.getImagePath());
                }
            }
        }
        return correctRecipe;
    }
    
    /**
     * A standard Getter method. Gets the input recipe of the user. 
     * @return
     */
    public Queue<String> getInput(){
        return input;
    } 

    /**
     * The generateIngredients method creates an array of ingredients. It should be called 
     * in the instantiation of every GamePage. 
     */
    private void generateIngredients(){
        Food[] generated = {new Food("Milk",new Image("PngMilk.png"),"PngMilk.png"),
        new Food("Pork",new Image("PngPork.png"),"PngPork.png"),
        new Food("Bread",new Image("PngBread.png"),"PngBread.png"),
        new Food("Pickles",new Image("PngPickles.png"),"PngPickles.png"),
        new Food("Soy Sauce",new Image("PngSoySauce.png"),"PngSoySauce.png"),
        new Food("Green Onion",new Image("PngGreenOnion.png"),"PngGreenOnion.png"),
        new Food("Ketchup",new Image("PngKetchup.png"),"PngKetchup.png"),
        new Food("Cereal",new Image("PngCereal.png"),"PngCereal.png"),
        new Food("Beef",new Image("PngBeef.png"),"PngBeef.png"),
        new Food("Wonton Wrapper",new Image("PngWrapper.png"),"PngWrapper.png"),
        new Food("Sesame Oil",new Image("PngSesameOil.png"),"PngSesameOil.png"),
        new Food("Noodles", new Image("PngNoodles.png"), "PngNoodles.png"),
        new Food("Teriyaki Sauce", new Image("PngTeriyaki.png"), "PngTeriyaki.png"),
        new Food("Cucumbers", new Image("PngCucumber.png"), "PngCucumber.png")};

        ingredientNames = generated;
    }


    /**
     * A standard toString method that informs the user of the GamePage Object. 
     */
    public String toString(){
        return "Game Page Object";
    }
    
}
