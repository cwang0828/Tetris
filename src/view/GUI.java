/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;

import listener.KeyListener;
import model.Board;
import play.SoundPlayer;

/**
 * This class is a graphical user interface that 
 * holds all the necessary components for the Tetris game. 
 * @author Cindy Wang
 * @version 2.0
 */
public class GUI extends JFrame {

    /**
     *  A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = 6326772823771541878L;

    /**
     * An instant variable for the delay time. 
     */
    private static final int TIMER_DELAY = 1000;

    /**
     * The text file that stores the highest score. 
     */
    private static final String SCORE_FILE = "input.txt"; 
    
    /**
     * A variable that stores the directory for the background music. 
     */
    private static final String DIRECTORY = "support_files/music/tMusic.wav";
    
    /**
     * The maximum number for the slider that stores 
     * the level/speed of the game. 
     */
    private static final int MAX_SLIDER = 20; 
    
    /**
     * The default slider value for the JSlider. 
     */
    private static final int DEFAULT_SLIDER_VALUE = 1; 
    

    /**
     * An instant variable representing the TNextPieceBoard class. 
     */
    private final TNextPieceBoard myNextPieceBoard;

    /**
     * An instant variable representing the TScoreBoard class. 
     */
    private TScoreUpdateBoard myScoreBoard;

    /**
     * An instant variable representing the TKeyBoard class. 
     */
    private final TKeyBoard myKeyBoard;

    /**
     * An instant variable for the key listener. 
     */
    private final KeyListener myKeyListener; 

    /**
     * An instant variable for the timer. 
     */
    private final Timer myTimer; 

    /**
     * An instant variable representing the Board class. 
     */
    private final Board myBoard;

    /**
     * An instant variable for the menu item class. 
     */
    private TMenuItem myMenuItem; 

    /**
     * A listener that listens to whether 
     * the pause key has been pressed or not. 
     */
    private final GetPauseListener myPauseListener;
      
    /**
     * An instant variable that represents my 
     * SoundPlayer class. 
     */
    private final SoundPlayer myPlayer; 

    
    /**
     * The constructor for the GUI class. 
     */
    public GUI() {
        super("Tetris - TCSS 305");
        final int delay = TIMER_DELAY;
        myBoard = new Board();
        myTimer = new Timer(delay, null);
        constructorHelper();        
        myPauseListener = new GetPauseListener();
        myPlayer = new SoundPlayer();     
        myKeyListener = new KeyListener(myBoard, myPlayer);
        myNextPieceBoard = new TNextPieceBoard(); 
        myKeyBoard = new TKeyBoard();
       
    }

    /**
     * A helper method for the constructor.
     */
    private void constructorHelper() {
        
        myTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.step();    
            }
        });
    }

    /**
     * Starts the Tetris game. 
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 
                                       MAX_SLIDER, DEFAULT_SLIDER_VALUE); 
        final TGameBoard gameBoard = new TGameBoard(this);
        final Integer highScore = readScore();
        myScoreBoard = new TScoreUpdateBoard(myTimer, slider, highScore);


        myMenuItem = new TMenuItem(gameBoard, myBoard, 
                                   myTimer, myScoreBoard, myKeyListener, 
                                   slider, myPauseListener,
                                   myPlayer); 
        myMenuItem.setHighestScore(highScore);
        setLayout(new BorderLayout());

        // set menu bar on to the frame
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(myMenuItem.getFileMenu());
        menuBar.add(myMenuItem.getGameMode());
        menuBar.add(myMenuItem.getEffect());
        menuBar.add(myMenuItem.getScoreInfo());
        menuBar.add(myMenuItem.getHelpMenu());
        
        setJMenuBar(menuBar);

        // set the Tetris board on to the frame
        add(gameBoard, BorderLayout.CENTER);

        // set the info board and the nextShape board on to the east panel.
        final JPanel eastPanel = new JPanel(new GridLayout(3, 1));

        eastPanel.add(myNextPieceBoard);
        eastPanel.add(myScoreBoard);
        eastPanel.add(myKeyBoard);
        eastPanel.setBackground(Color.BLACK);

        add(eastPanel, BorderLayout.EAST);
        

        // Add the listeners and observers to the board. 
        addKeyListener(myPauseListener);
        addKeyListener(myKeyListener);
        this.setFocusable(false);


        myBoard.addObserver(gameBoard);
        myBoard.addObserver(myNextPieceBoard);
        myBoard.addObserver(myScoreBoard);
        myBoard.addObserver(myMenuItem);
//        
//        myBoard.newGame();
//        myMenuItem.setIsStarted(true);
        pack();
        setVisible(true);    

    }




    /**
     * This method pauses the game. 
     * @param thePause passes whether the game has been paused or not. 
     */
    private void pauseGame(final boolean thePause) {
        if (thePause) {
            myTimer.stop();
            myKeyListener.setMyTimerIsRunning(false);
            myPlayer.pause(DIRECTORY);
        } else {
            myTimer.start();
            myKeyListener.setMyTimerIsRunning(true);
            myPlayer.play(DIRECTORY);
        }
    }

    /**
     * This method reads the score from the text file. 
     * @return the highest score that is stored 
     * in the text file. 
     */
    public Integer readScore() {
        final File inFile = new File(SCORE_FILE);
        Integer highest = null; 
        try {
            final Scanner scanner = new Scanner(inFile);

            if (scanner.hasNextInt()) {
                highest = scanner.nextInt();                
            }
            
            scanner.close();
        } catch (final FileNotFoundException exception) {
            exception.printStackTrace();
            highest = 0; 
        }
        return highest; 
    }
    
    /**
     * This method saves the score to the text file
     * when existing. 
     */
    public void saveScore() {
        final File output = new File(SCORE_FILE);
        FileWriter fWriter;
        System.out.println("outside try..." + myScoreBoard.getHighestScore());
        try {
            fWriter = new FileWriter(output);
            final PrintWriter pWriter = new PrintWriter(fWriter);
            System.out.println("highest...." + myScoreBoard.getHighestScore());
            final int num = myScoreBoard.getMyHighestScore();
            pWriter.println(num);
            pWriter.close();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
      

        
    }
    
    /**
     * Ends the game. 
     */
    @Override
    public void dispose() {
        super.dispose();
        saveScore();
        System.out.println("Dispose get called");
    }
    /**
     * An inner class that executes when the user 
     * wants to pause the game. 
     * @author Cindy Wang
     * @version 1.0
     */
    class GetPauseListener extends KeyAdapter {
        // do the pause key separaetly because you should be able to 
        // access only the pause key when the key is paused. 
        
        /**
         * An instant variable showing whether the timer has been 
         * running or not. 
         */
        private boolean myTimerIsRunning; 
        
        /**
         * @param theTimerIsRunning determines whether the 
         * timer has been running or not. 
         */
        public void setMyTimerIsRunning(final boolean theTimerIsRunning) {
            myTimerIsRunning = theTimerIsRunning;
        }
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            pauseGame(myMenuItem.togglePausedState(myTimerIsRunning, theEvent));
            
        }
    }

}
