import java.io.File;
import edu.macalester.graphics.CanvasWindow;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The playMusic class is in charge of playing music. It should throw respective errors as needed
 * when the wrong file is being read or there are exceptions. This class is written originally by 
 * Max O'Didily- additional notes on edits of the program are added in additional JavaDoc. 
 * 
 * Author: Max O'Didily; playClick() method added by Linda Lor
 * Source: Taken from (https://www.youtube.com/watch?v=TErboGLHZGA&t=0s)
 */
public class PlayMusic {

    /** 
     * Constructor. Standard; Does not need much. 
     */
    public PlayMusic(){

    }

    /**
     * The playMusic method was originally written by Max O'Didily. It should play 
     * music in respect to using Clips, and should loop it continously.
     * The line: JOptionPane.showMessageDialog(null, "Press OK to stop playing") was
     * deleted by Linda Lor in order to make music stop when the canvas is closed instead. 
     * 
     * @param musicLocation
     * @param canvas
     */
    public void playMusic(String musicLocation,CanvasWindow canvas){
        try
        {
            File musicPath = new File(musicLocation);

            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
            else{
                System.out.println("Can't find file");
            }
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * The playClick method is a modified version of Max O'Didily's playMusic method. Instead,
     * it only plays a sucessful Click sound that happens whenever the user clicks a button. It 
     * also does NOT loop. 
     */
    public void playClick(){
        String path = "res/Click.wav";

        try
        {
            File musicPath = new File(path);

            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            }
            else{
                System.out.println("Can't find file");
            }
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * A standard toString method that credits the original author of the class. 
     */
    public String toString(){
        return "PlayMusic object| Originally written by Max O'Didily";
    }

}
