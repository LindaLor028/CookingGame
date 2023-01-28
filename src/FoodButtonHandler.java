import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
/**
 * The FoodButtonHandler class handles ALL of the Food Buttons.
 * It can print all the FoodButtons and can also detect when a specific button is being clicked on. 
 * 
 * Author: Rita Li & Linda Lor
 */
public class FoodButtonHandler {

    // Instance Variables 
    private List<FoodButton> buttons;
    private CanvasWindow canvas;
    private int moveY; // Used to calculate the respective Y to move when printing duplicates 

    /**
     * Constructor Method. 
     * Should instantiate the buttons list and set moveY to default value (500). 
     * @param canvas
     */
    public FoodButtonHandler(CanvasWindow canvas){
        buttons = new ArrayList<FoodButton>();
        this.canvas = canvas;
        moveY = 500;
    }

    /**
     * This method prints all of the Ingredients in its respective position based on the default height of the canvas. 
     * While doing this, it will create a FoodButton object of each ingredient and add it onto its buttons list. 
     * @param ingredients
     * @param startX
     * @param startY
     */
    public void printIngredients(Food[] ingredients, double startX, double startY){
        double incrementer = 90;
        double xPos = startX; 
        double yPos = startY - incrementer;
        for(Food currentIng: ingredients){
            // create FoodButton
            FoodButton fButton = new FoodButton(currentIng);
            buttons.add(fButton);
            // add it onto the screen
            if (yPos + incrementer > CookingGame.DEFAULT_HEIGHT-20) { // if Y exceeds canvas height..
                // move it to the next X position and restart the Y 
                xPos = startX + incrementer;
                yPos = startY;
                fButton.putOn(canvas, xPos, yPos);
            }
            else{
                yPos += incrementer;
                fButton.putOn(canvas, xPos, yPos);
            }

        }
    }
        
    /**
     * The onClick method is intended to detect when the canvas is clicked, which button is/isn't being clicked.
     * It does this by checking the bounds of the FoodButton. If the user's mouse is within the bounds, the 
     * FoodButton is expected to print a duplicate of itself on the far right and then return itself. 
     * 
     * @param mouseX
     * @param mouseY
     * @return
     */
    public FoodButton onClick(double mouseX, double mouseY){
        FoodButton clickedButton = null;
        for (FoodButton button: buttons) {
            double xPos = button.getX(); double yPos = button.getY();

            // check if in bounds
            if (mouseX > xPos - button.MAX_LEN/2 && mouseX < xPos + button.MAX_LEN/2 &&
            mouseY > yPos - button.MAX_LEN/2 && mouseY < yPos + button.MAX_LEN/2) {
                button.clicked(canvas,moveY);
                moveY-=50;
                clickedButton = button;
            }
        }
    return clickedButton;
    }

    /**
     * The deleteAllButtons method deletes all FoodButtons from the canvas. 
     * This is uniquely needed because all FoodButtons are actually images, so we 
     * must remove the image- not necessarily the FoodButton. 
     * It also resets the moveY and buttons list. 
     */
    public void deleteAllButtons(){
        moveY = 500;
        for(FoodButton button : buttons){
            button.remove(canvas);
        }
        buttons = new ArrayList<FoodButton>();

        canvas.draw();
    }

    /**
     * A standard toString method which informs the user that this is a FoodButtonHandler object. 
     */
    public String toString(){
        return "FoodButtonHandler Object";
    }

    
}
