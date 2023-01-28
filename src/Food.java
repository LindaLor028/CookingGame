import edu.macalester.graphics.Image;
/**
 * This Food Class create a new data type that holds the image, name, and image path of 
 * each food ingredients to be used in other classes
 * 
 * Authors: Rita Li & Linda Lor
 */
public class Food {

    private String name;
    private Image image;
    private String path;

    /**
     * Constructor for the food class. Each food should have a name, an image and a path of the image.
     * @param name
     * @param image
     * @param path
     */
    public Food(String name, Image image, String path){
        this.name = name;
        this.image = image;
        this.path = path;

        //Set the max Height and max Width of the image
        image.setMaxHeight(50);
        image.setMaxWidth(50);

        
    }

    /**
     * @return the name of the food
     */
    public String getFoodName(){
        return name;
    }

    /**
     * @return the image of the food
     */
    public Image getFoodImage(){
        return image;
    }

    /**
     * @return return the path of the image of the food
     */
    public String getImagePath(){
        return path;
    }

    /**
     * A standard toString Method. 
     */
    public String toString(){
        return "Food :" + name;
    }
    
}
