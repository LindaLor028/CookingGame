import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;


/**
 * The FoodButton class instantiates a Button that is represented by an Image. It holds unique values, such as
 * the button's name, the path of its image, and its x and y position. This button is intended to be unique from 
 * other UI buttons because it can be represented by an Image- yet still detect user touch. 
 * 
 * Author: Rita Li & Linda Lor 
 */
public class FoodButton {

    private Image image;
    private String path;
    private double xPos, yPos; 
    private String name;
    private PlayMusic mObj;
    private Food food;

    public final double MAX_LEN = 80.0;

    /**
     * Standard Constructor. Each time a FoodButton is instantiated it needs a name and a path.
     * @param name
     * @param path // should be a String leading to the Image path
     */
    public FoodButton(Food food){
        this.food = food;
        this.name = food.getFoodName();
        this.path = food.getImagePath();
        this.image = new Image(path);
        mObj = new PlayMusic();
        image.setMaxHeight(MAX_LEN);image.setMaxWidth(MAX_LEN);
    }

    /**
     * This method puts the FoodButton on the canvas. Instead of doing,
     * canvas.add(foodButton), this method is needed because FoodButton's
     * image is the only thing that needs to be added onto the canvas. 
     * This method should also print the name of the FoodButton.
     * 
     * @param canvas
     * @param xPos
     * @param yPos
     */
    public void putOn(CanvasWindow canvas, double xPos, double yPos){
        this.xPos = xPos;
        this.yPos = yPos; 
        canvas.add(image);
        image.setCenter(xPos, yPos);
        GraphicsText text = new GraphicsText(name);
        text.setCenter(xPos,yPos+38);
        canvas.add(text);
        canvas.draw();
    }

    /**
     * The clicked method is a generic response expected from a FoodButton each time it is clicked. 
     * It is expected to print an Image of itself to the right. 
     * @param canvas
     * @param moveY
     */
    public void clicked(CanvasWindow canvas, double moveY){
    
        mObj.playClick();
        Image newImg = new Image(path);

        newImg.setMaxHeight(MAX_LEN);
        newImg.setMaxWidth(MAX_LEN);
        
        newImg.setPosition(400,moveY );
        canvas.add(newImg);
        canvas.draw();
    }

    /**
     * The remove method removes the FoodButton from the canvas. This method is unique because it
     * simply removes the image- which is the only presence of the FoodButton. 
     * 
     * @param canvas
     */
    public void remove(CanvasWindow canvas){
        canvas.remove(image);
    }

    /**
     * A Getter method. Gets the name of the food. 
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * A Getter method. Gets the X Position of the FoodButton/Image.
     * @return
     */
    public double getX(){
        return xPos;
    }

    /**
     * A Getter method. Gets the Y position of the FoodButton/Image. 
     * @return
     */
    public double getY(){
        return yPos;
    }

    /**
     * The toString method returns the name of itself. 
     */
    public String toString(){
        return "Food Button Object | Name :" + name;
    }


    
    
}
