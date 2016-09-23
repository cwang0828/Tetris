/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Board;
import play.SoundPlayer;

/**
 * 
 * @author cinwan12
 * @version 1.0
 */
public class KeyListener extends KeyAdapter {

    /**
     * A variable that stores the music directory for 
     * the left and right click buttons. 
     */
    private static final String LEFTRIGHT = "support_files/music/left.wav";
    
    /**
     * A variable that stores the music directory for 
     * the clock-wise and counter clock-wise click buttons. 
     */
    private static final String ROTATE = "support_files/music/ccw.wav";
    
    /**
     * A variable that stores the music directory for 
     * the down click buttons. 
     */
    private static final String DOWN = "support_files/music/down.wav";
    
    /**
     * A variable that stores the music directory for 
     * the drop click buttons. 
     */
    private static final String DROP = "support_files/music/drop.wav";

    /**
     * An instant variable for the Board class. 
     */
    private final Board myKey; 

    /**
     * An instant variable that determines whether 
     * the timer is running or not. 
     */
    private boolean myTimerIsRunning; 

    /**
     * An instant variable that determines whether
     * the music has been paused or not. 
     */
    private boolean myMusicIsNotPaused; 

    /**
     * An instant variable that represents the SoundPlayer class. 
     */
    private final SoundPlayer myPlayer; 


    /**
     * The constructor for the keyListener class. 
     * @param theBoard is the Board class.
     * @param thePlayer is the SoundPlayer class. 
     */
    public KeyListener(final Board theBoard, final SoundPlayer thePlayer) {
        super();
        myKey = theBoard; 
        myPlayer = thePlayer;
    }


    /**
     * 
     * @param theMusicIsPaused sets my music player 
     * to be paused or not. 
     */
    public void setMyMusicIsPaused(final boolean theMusicIsPaused) {
        myMusicIsNotPaused = theMusicIsPaused;
    }

    /**
     * 
     * @param theTimerIsRunning sets the timer to run
     * or not. 
     */
    public void setMyTimerIsRunning(final boolean theTimerIsRunning) {
        myTimerIsRunning = theTimerIsRunning;
    }






    /**
     * This method allows the user to change the position
     * of the block. 
     * @param theEvent is the key pressed event. 
     */
    public void keyPressed(final KeyEvent theEvent) {
//        System.out.println(myTimerIsRunning);

        if (myTimerIsRunning) {
            final int key = theEvent.getKeyCode();
            playMusic(key);


            if (key == KeyEvent.VK_LEFT) {
                // move left one block 
                myKey.left();
            }

            if (key == KeyEvent.VK_RIGHT) {
                // move right one block
                myKey.right();
            }

            if (key == KeyEvent.VK_UP) {
                // move down one block
                myKey.down();
            }

            if (key == KeyEvent.VK_DOWN) {
                // move to the bottom directly
                myKey.drop();
            }

            if (key == KeyEvent.VK_3) {
                // rotate CW
                myKey.rotateCW();
            }

            if (key == KeyEvent.VK_1) {
                // rotate cCW
                myKey.rotateCCW();
            }


        }

    }

    /**
     * @param theKey is the key event 
     * when a certain key is pressed. 
     */
    public void playMusic(final int theKey) {

        if (myMusicIsNotPaused) {

            if (theKey == KeyEvent.VK_LEFT || theKey == KeyEvent.VK_RIGHT) {
                myPlayer.play(LEFTRIGHT);
            }

            if (theKey == KeyEvent.VK_UP) {
                myPlayer.play(DOWN);
            }

            if (theKey == KeyEvent.VK_DOWN) {
                myPlayer.play(DROP);
            }

            if (theKey == KeyEvent.VK_3 || theKey == KeyEvent.VK_1) {
                myPlayer.play(ROTATE);
            }
        }
    }

}
