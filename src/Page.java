import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;
import java.awt.Color;
import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * The Page class handles all Pages that the user will encounter. It is in charge of putting the correct graphics and buttons onto 
 * the screen as requested. It also holds the recipes and will actively compare the recipes to the input here.
 * 
 * Authors: Rita Li & Linda Lor
 * 
 * Author's Note: This class is very long and seems daunting but it is organized in this order:
 * 1) Page Set Ups (HomePage, Instructions, Levels, GamePage, and AddRecipes)
 * 2) Private Methods That Help Set Up Graphics (setBackHomeButton, addOntoCanvas)
 * 3) Private Methods That Are Specific To Some Pages (animateGirl, AddRecipe, PrintRecipes, PrintScore)
 * 4) Private createRecipes method that adds all the recipes 
 * 5) ToString method
 */
public class Page {

    // This map hold all the recipes, including initial recipes and recipes create by players.
    private Map<String,Queue<String>> recipes;

    private CanvasWindow canvas;
    private PlayMusic mObj;
    private String currentPage;
    
    /**
     * A Standard Constructor. The Page object should always take a name and a respective 
     * canvas- that it'll actively be making changes on. 
     * 
     * @param pageName
     * @param canvas
     */
    public Page(String pageName, CanvasWindow canvas){
        this.canvas = canvas;
        this.mObj = new PlayMusic();
        currentPage = null;
        createRecipes();  // Create the map with initial recipe templates
    }

    /**
     * This method will create the users interface of the home page of the game.
     * Start Game button: linked to the page where users choose the dish they want to cook
     * Instruction button: linked to the instruction page
     * Add Recipe button: linked to the page where users can create their own recipe and play with it later
     * Quit Game button: Close the CanvasWindow 
     */
    public void setUpHome(){
        canvas.removeAll();
        currentPage = "Home";
        // Create and add the background image to the canvas
        Image img = new Image("PngHome2.png");
        addOntoCanvas(img);
        canvas.draw();
        // canvas.pause(1000);

        animateGirl();

        //Create four buttons: Start, intruction, add, quit
        Button start = new Button("Start Game");
        Button instruction = new Button("Instructions");
        Button addRecipe = new Button("Add Recipe");
        Button quitGame = new Button("Quit");

        canvas.add(start, 400,300);
        canvas.add(instruction, 400,350);
        canvas.add(addRecipe, 400,400);
        canvas.add(quitGame, 400,450);

        //When buttons are clicked, load the page that the button linked to
        instruction.onClick(()-> {
            mObj.playClick();
            instructionPage();});
        start.onClick(()->{
            mObj.playClick();
            chooseRecipe();});
        quitGame.onClick(()->{
            mObj.playClick();
            canvas.closeWindow();});
        addRecipe.onClick(()->{
            mObj.playClick();
            addRecipesPage();});

        canvas.draw();
    }


    /**
     * The instructionPage method creates an instruction page that appears on the canvas. 
     * It has a BackHome Button, and simply displays the Instruction png Image.
     */
    public void instructionPage(){
        canvas.removeAll();
        currentPage = "Instructions";

        //Add necessary Graphics
        Image instru = new Image("PngInstruction.png");
        Button back = setBackHomeButton();

        addOntoCanvas(instru);

        back.onClick(()->{
            mObj.playClick();
            setUpHome();
        });
    }

    /**
     * The chooseRecipe method creates a page where the user can choose the recipe/level
     * they want to play. This method should provide a standard backHome button and also
     * a variety of buttons for the user to choose which recipe/level they'd like to play.
     */
    public void chooseRecipe(){
        canvas.removeAll();
        currentPage = "Levels";
        
        //Create and add the background image
        Image background = new Image("PngLevels.jpg");
        addOntoCanvas(background);

        //Create and add back to home button
        Button back = setBackHomeButton();

        back.onClick(()->{
            mObj.playClick();
            setUpHome();});

        // Add all recipes in the recipe map to the canvas
        printRecipeButtons();

    }

    /**
     * The loadGamePage method loads the primary Game Page of this entire game.
     * It should create a GamePage object which has a standard set-up (prints out
     * buttons of all Ingredients, collects UI, etc.).
     * It also features a restart and confirm button.
     * @param gamePageName
     */
    public void loadGamePage(String gamePageName){
        
        currentPage = "Game Page";
        
        Button confirm = new Button("Confirm");
        Button restart = new Button("Restart");
        GamePage game = new GamePage(gamePageName, canvas, confirm, recipes);
        canvas.add(restart,CookingGame.DEFAULT_WIDTH-120, 10);

        restart.onClick(()->{
            mObj.playClick();
            game.removeAll();
            loadGamePage(gamePageName);
        });

        confirm.onClick(()-> {
            mObj.playClick();
            game.removeAll();
            double score = game.calculateScore();
            List<String> correctRecipe = game.printCorrectRecipe();
            scorePage(score, correctRecipe);
        });
    }

    /**
     * The scorePage method creates a score page that prints on the canvas. 
     * It should print the respective score of the user after calculating the score 
     * with the respective score-value, which is calculated with the GamePage class. 
     * @param score
     * @param correctRecipe
     */
    public void scorePage(double score,List<String> correctRecipe){
        canvas.removeAll();
        currentPage = "Score Page";

        Image img = new Image("PngScore.png");
        Button back = setBackHomeButton();

        GraphicsText text = new GraphicsText("Your Score is "+score);
        text.setFont(FontStyle.BOLD, 30);

        addOntoCanvas(img);
        canvas.add(text,150,120);
        printScore(score, correctRecipe);

        back.onClick(()->{
            mObj.playClick();
            setUpHome();
        });
    }

    /**
     * The addRecipesPage method creates a page where the user can select the desired ingredients
     * and create their own recipe. The method uses a GamePage (similar to the one in loadGamePage)
     * because it requires FoodButtons. 
     * This page also features backHome, clearAll, and Confirm buttons- along with TextField where the
     * user can type the title of their recipe and save it. 
     * 
     */
    public void addRecipesPage(){
        canvas.removeAll();
        currentPage = "Add Your Own Recipe Page";
        
        Button confirm = new Button("Confirm");
        confirm.setPosition(CookingGame.DEFAULT_WIDTH-50, 10);

        Button clearAll = new Button("Clear All");
        clearAll.setPosition(CookingGame.DEFAULT_WIDTH-110, 50);

        GamePage page = new GamePage("Make Your Own \n Recipe", canvas, confirm, recipes);
        Button back = setBackHomeButton();
        GraphicsText tt = new GraphicsText("Name your recipe here: ");
        TextField text = new TextField();

        canvas.add(clearAll);
        canvas.add(tt,215,215);
        canvas.add(text,215,230);

        back.onClick(()->{
            mObj.playClick();
            page.removeAll();
            setUpHome();
        });
        
        clearAll.onClick(()->{
            mObj.playClick();
            page.removeAll();
            addRecipesPage();
        });

        confirm.onClick(()->{
            addRecipe(page, text);
            
        });

        canvas.draw();
    }


    /**
     * The setBackHomeButton sets up the default position of the Back home button.
     * This method is for all of the pages that require a backhome button (addRecipes Page,
     * score Page, chooseRecipes Page). 
     * 
     * @return // returns the button added so that they can do their "unique" onClick responses
     */
    private Button setBackHomeButton(){
        Button back = new Button("Back to Home");
        back.setPosition(CookingGame.DEFAULT_WIDTH-120, 10);
        canvas.add(back);
        return back;
    }

    /**
     * The addOnToCanvas adds an image onto the canvas in respect to the canvas's default height and 
     * width. This method is exclusively written when setting up background Images (e.g. PngLevel).
     * 
     * @param img // should be a background image only
     */
    private void addOntoCanvas(Image img){
        img.setMaxHeight(CookingGame.DEFAULT_HEIGHT);
        img.setMaxWidth(CookingGame.DEFAULT_WIDTH);
        canvas.add(img);
    }

    /**
     * Creates a gliding Image of PngGirl.png. 
     * Only to be used for the setUpHome method. 
     */
    private void animateGirl(){
        Image girl = new Image("PngGirl.png");
        girl.setMaxHeight(CookingGame.DEFAULT_HEIGHT*0.7); girl.setMaxWidth(CookingGame.DEFAULT_WIDTH*0.7);
        canvas.add(girl, -40, 265);
        canvas.draw();
        int xPos = -40; 
        int yPos = 265;
        while(xPos<50){
            girl.setPosition(xPos,yPos);
            xPos += 5;
            canvas.pause(15);
            canvas.draw();
        }
    }

    /**
     * The addRecipe method should add the user's input as a recipe on to the recipes HashMap.
     * Before doing so, it checks for certain conditions. Once added, it should change the respective
     * parameters, page and text, as needed. 
     * 
     * @param page
     * @param text
     */
    private void addRecipe(GamePage page, TextField text){
        mObj.playClick();
        if (recipes.size()> 7){
            page.removeAll();
            GraphicsText error = new GraphicsText("Too Many Recipes Added!");
            canvas.add(error, 250,200);
        }
        else{
            String recipeName = text.getText();
            text.setText("");
                if(recipeName.equals("")){
                    GraphicsText error = new GraphicsText("Enter the name of your recipe!");
                    error.setFillColor(Color.WHITE);error.setFont(FontStyle.BOLD,10);
                    canvas.add(error,320,250);
                }else{
                    recipes.put(recipeName, page.getInput());
                }
        }
    }

    
    /**
     * The printRecipeButtons method prints all of the recipe names as buttons for the user
     * to select which recipe they'd like to duplicate. This mehtod is exclusively for the 
     * chooseRecipes page and generally. 
     */
    private void printRecipeButtons(){
        //Initial height and width
        int x = 100;
        int y = 300-50;
        for(Map.Entry<String,Queue<String>> entry: recipes.entrySet()){
            String recipeName = entry.getKey();
            Button level = new Button(recipeName);
            String imgPath = "Png"+recipeName.replace(" ", "_")+"R.png"; 
            System.out.println(imgPath);
            File file = new File("res/" + imgPath);
            
            if(!file.exists()){
                imgPath = "PngSecret.png";
            }
            Image recipeImg = new Image(imgPath);
            
            recipeImg.setMaxHeight(100);recipeImg.setMaxWidth(100);
            level.setPosition(x,y);
            canvas.add(level);
            

            if (y + 20 > CookingGame.DEFAULT_HEIGHT-20) { // if Y exceeds canvas height..
                // move it to the next X position and restart the Y 
                x = x + 60;
                y = 300-20;
                canvas.add(recipeImg, x,y-95);
            }
            else{
                y +=30;
                canvas.add(recipeImg, x,y-95);
            }

            level.onClick(()->{
                mObj.playClick();
                loadGamePage(recipeName);});
            y+=60;

            if (y >= 650){
                x = 320;
                y = 250;
            }
        }
    }

    /**
     * The printScore method prints the score of the user after they play the game. 
     * This method is exclusively for the scorePage (method and generally).
     * @param score
     * @param correctRecipe
     */
    private void printScore(double score, List<String> correctRecipe){
        if(score == 100.00){
            GraphicsText congrats = new GraphicsText("Wonderful Job!! \n You are a Michelin chef!");
            congrats.setFont(FontStyle.BOLD, 30);
            congrats.setFillColor(new Color(250, 248, 209));
            canvas.add(congrats,60.0,180);
        } else{
            GraphicsText improve = new GraphicsText("Improve your cooking skill!!!");
            improve.setFont(FontStyle.BOLD, 30);
            canvas.add(improve,60.0,180);

            GraphicsText correct = new GraphicsText("The Correct Recipe is: ");
            correct.setFont(FontStyle.ITALIC,20);
            correct.setFillColor(Color.DARK_GRAY);
            canvas.add(correct,50,235);

            double x = 50.0;
            double y = 250.0;
            for(String path :correctRecipe){
                Image newImg = new Image(path);
                newImg.setMaxHeight(50);newImg.setMaxWidth(50);
                canvas.add(newImg,x,y);
                x += 70;
            }
        }
    }

    /**
     * The createRecipes method creates all of the recipes that this game contains (should be five total).
     * The recipes will then be put into the HashMap recipes. 
     * @return
     */
    private Map<String, Queue<String>> createRecipes(){
        recipes = new HashMap<String, Queue<String>>();
        Queue<String> one = new ArrayDeque<String>();
        one.add("Cereal");one.add("Milk");

        Queue<String> two = new ArrayDeque<String>();
        two.add("Bread");two.add("Beef");two.add("Ketchup");two.add("Pickles");two.add("Bread");
        
        Queue<String> three = new ArrayDeque<String>();
        three.add("Pork");three.add("Green Onion");three.add("Soy Sauce");three.add("Sesame Oil");three.add("Wonton Wrapper");

        Queue<String> four = new ArrayDeque<String>();
        four.add("Pork");four.add("Soy Sauce");four.add("Teriyaki Sauce");four.add("Cucumbers");four.add("Noodles");
        

        Queue<String> five = new ArrayDeque<String>();
        five.add("Beef");five.add("Soy Sauce");five.add("Teriyaki Sauce");five.add("Green Onion");

        recipes.put("Breakfast Cereal",  one);
        recipes.put("Hamburger",  two);
        recipes.put("Dumplings", three);
        recipes.put("Peking Noodles", four);
        recipes.put("Mongolian Beef", five);

        return recipes;
    }

    /**
     * Standard toString method. It should inform the user of the current page. 
     */
    public String toString(){
        return "Page Object | Currently on Page: " + currentPage;

    }

    
}