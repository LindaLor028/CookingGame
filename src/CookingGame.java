

import edu.macalester.graphics.CanvasWindow;

/**
 * The CookingGame class create a game that is cooking simulator in a simplier version. Players are allowed to choose
 * the dish they want cook and get a cooking score by add correct ingredient of that dish in correct order.
 * This game initially have five recipes. Players can also add their own recipes and play with them.
 * 
 * Author: Rita Li & Linda Lor
 */
public class CookingGame {
    
    private CanvasWindow canvas;
    private PlayMusic music;

    // Default witdth and height of the canvas window.
    public final static int DEFAULT_WIDTH = 525;
    public final static int DEFAULT_HEIGHT = 685;

    /**
     * Standard Constructor, Create a canvas window with default size and music player
     */
    public CookingGame(){
        canvas = new CanvasWindow("Cooking Game", DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.music = new PlayMusic();

    }
    
    /**
     * This method setup the users interface of the cooking game, and play the music
     */
    private void setUpUI(){
        Page setUp = new Page("CookingGame", canvas);
        // String location = "res/KirbyYarn.wav";
        // music.playMusic(location, canvas);
        setUp.setUpHome();
        canvas.draw();
    }

    /**
     * A Standard toString method. Informs users that this is the Cooking Game Class. 
     */
    public String toString(){
        return "Cooking Game Class";
    }

    public static void main(String[] args) {
        CookingGame game = new CookingGame();
        game.setUpUI();

    }
}
