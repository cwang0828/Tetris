/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import listener.KeyListener;
import model.Board;
import play.SoundPlayer;
import view.GUI.GetPauseListener;



/**
 * This class stores all the menu items for the Tetris board.
 * @author Cindy Wang
 * @version 1.0
 */
public class TMenuItem extends JMenuBar implements Observer {
    /**
     * A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = -217935627352096718L;

    /**
     * An instant variable representing the Score Updated board. 
     */

    private static final int RESET_SCORE = -4; 
    
    /**
     * A variable for the delay time. 
     */
    private static final int TIMER_DELAY = 1000;

    /**
     * A variable for the amount of time in millisecond
     * that the timer decreases when reaching the next level. 
     */
    private static final int DECREMENT = 50;

    /**
     * A variable that stores the directory for the background music. 
     */
    private static final String DIRECTORY = "support_files/music/tMusic.wav";
    
    /**
     * A variable that stores the directory for the game over music. 
     */
    private static final String GAMEOVER = "support_files/music/over.wav";
    
    /**
     * A variable that stores the directory for the start music.
     */
    private static final String START = "support_files/music/start.wav";
    
    /**
     * The number of blocks for the width of the board. 
     */
    private static final int BOARD_WIDTH = 10; 
    
    /**
     * The number of blocks for the height of the board.  
     */
    private static final int HEIGHT1 = 20; 
    
    /**
     * The number of blocks for the height of the board. 
     */
    private static final int HEIGHT2 = 10; 
    
    /**
     * The slider for the level/speed. 
     */
    private final JSlider mySlider; 

    /**
     * The menu for the new game. 
     */
    private JMenuItem myNewGame; 

    /**
     * The menu for the end game. 
     */
    private JMenuItem myEndGame; 

    /**
     * An instant variable that represents the TGameBoard class. 
     */
    private final TGameBoard myGameBoard; 

    /**
     * An instant variable that determines 
     * whether the grid option is selected or not. 
     */
    private boolean myIsGrid; 

    /**
     * An instant variable that represents the Board class. 
     */
    private final Board myBoard; 

    /**
     * An instant variable that determines whether the 
     * husky mode is selected or not. 
     */
    private boolean myIsHusky;

    /**
     * An instant variable that determines whether the
     * black-and-white mode is selected or not. 
     */
    private boolean myIsBlack; 

    /**
     * An instant variable for the timer. 
     */
    private final Timer myTimer; 

    /**
     * An instant variable that determines whether
     * the game has started or not. 
     */
    private boolean myIsStarted;

    /**
     * An instant variable that stores the delay time. 
     */
    private int myDelayTime; 

    /**
     * An instant variable that represents the TScoreUpdatedBoard 
     * class. 
     */
    private final TScoreUpdateBoard myScoreBoard; 

    /**
     * An instant variable that determines whether the 
     * game has been paused or not. 
     */
    private boolean  myIsPaused;
    
    /**
     * An instant variable that determines whether the music has been 
     * paused or not. 
     */
    private boolean myMusicIsNotPaused;
    
    /**
     * An instant variable for the key listener. 
     */
    private final KeyListener myKeyListener; 

    /**
     * An instant variable for the pause listener. 
     */
    private final GetPauseListener myPauseListener;
    
    /**
     * An instant variable that stores the highest score. 
     */
    private Integer myHighestScore; 

    /**
     * An instant variable that represents the 
     * SoundPlayer class. 
     */
    private final  SoundPlayer myPlayer; 
    
    /**
     * An instant variable for the 10x20 board. 
     */
    private JRadioButton mySize1;
    
    /**
     * An instant variable for the 10x10 board. 
     */
    private JRadioButton mySize2;
    
    /**
     * The constructor of the JMenuItem class.
     * @param theGameBoard is the GameBoard class. 
     * @param theBoard is the board class.
     * @param theTimer is the timer.
     * @param theScoreBoard is the ScoreBoard class. 
     * @param theKeyListener is the KeyListener class. 
     * @param theSlider is the JSlider that stores 
     * the different game level.
     * @param thePauseListener is the listener for the pause key. 
     * @param thePlayer is the player for the sound. 
     */       
    public TMenuItem(final TGameBoard theGameBoard, final Board theBoard, 
                     final Timer theTimer, final TScoreUpdateBoard theScoreBoard,
                     final KeyListener theKeyListener, final JSlider theSlider, 
                     final GetPauseListener thePauseListener, 
                      final SoundPlayer thePlayer) {

        super(); 
        myGameBoard = theGameBoard; 
        myBoard = theBoard; 
        myTimer = theTimer; 
        myScoreBoard = theScoreBoard; 
        myKeyListener = theKeyListener; 
        mySlider = theSlider; 
        myPauseListener = thePauseListener;
        myPlayer = thePlayer; 
 
        constructorHelper();
  
    }


    /**
     * A helper for the constructor. 
     */
    private void constructorHelper() {
        myDelayTime = 0; 
        myIsGrid = false;
        myIsHusky = true; 
        myIsBlack = false;
        myIsStarted = false; 
        myIsPaused = false;
        myMusicIsNotPaused = false;
        mySize1 = new JRadioButton("10 x 20");
        mySize2 = new JRadioButton("10 x 10");
        

    }

    
    /**
     * A setter method that sets the highest score. 
     * @param theHighestScore is the highest score from the text file. 
     */
    public void setHighestScore(final int theHighestScore) {
        myHighestScore = theHighestScore;
    }

   /**
    * A method that determines whether the game should be paused or not
    * based on whether the timer is running and on whether the 
    * pause key has been pressed or not. 
    * @param theTimerIsRunning determines whether the timer is running or not 
    * based on the pause event. 
    * @param theEvent is the key event for the pause key. 
    * @return a boolean on whether the game has been paused or not. 
    */
    public boolean togglePausedState(final boolean theTimerIsRunning, 
                                     final KeyEvent theEvent) {
        if (theTimerIsRunning
                        && theEvent.getKeyCode() == KeyEvent.VK_P) {
            // paused: T-> T, TvT -> F, TvF->T, ...
            myIsPaused  ^= true;
        }
        return myIsPaused;
    }


    /**
     * This method starts the game. 
     * @param theIsStarted is a boolean showing
     * whether the game has started or not. 
     */
    private void startGame(final boolean theIsStarted) {
        myIsStarted = theIsStarted;
        if (myIsStarted) {
            // Start the game
            myTimer.start();
            myBoard.newGame();
            myNewGame.setEnabled(false);
            myEndGame.setEnabled(true);
            myGameBoard.setIsGameOver(false);
            myIsPaused = false;
            myKeyListener.setMyTimerIsRunning(true);
            myPauseListener.setMyTimerIsRunning(true);
            
            if (myMusicIsNotPaused) {
                myPlayer.play(START);
                myPlayer.play(DIRECTORY);
                myKeyListener.setMyMusicIsPaused(true);
                
            }
            
            if (mySize1.isSelected()) {
                mySize2.setEnabled(false);
                
            } else {
                mySize1.setEnabled(false);
            }
            
            myGameBoard.setFocusable(true);
            myGameBoard.requestFocus();
            myGameBoard.addKeyListener(myKeyListener);

        } else { //myIsStarted == false, 
            myNewGame.setEnabled(true);
            myEndGame.setEnabled(false);
            myTimer.stop();
            myGameBoard.setIsGameOver(true);
            myGameBoard.repaint();
            myKeyListener.setMyTimerIsRunning(false);
            myScoreBoard.setMyScore(RESET_SCORE);
            myScoreBoard.setMyLevel(1);
            myScoreBoard.setMyLineSum(0);
            myScoreBoard.setMyCount(0);
            myScoreBoard.updateHighestScore();
            myPauseListener.setMyTimerIsRunning(false);
            
            if (!myMusicIsNotPaused) {
                myPlayer.stop(DIRECTORY);
                myPlayer.play(GAMEOVER);
                myKeyListener.setMyMusicIsPaused(false);
            }
            myMusicIsNotPaused ^= true; 
            mySize1.setEnabled(true);
            mySize2.setEnabled(true);
            
        }
    }
   
    /**
     * A getter method that allows access
     * to my current highest score. 
     * @return my highest score. 
     */
    public int getMyHighestScore() {
        return myHighestScore;
    }

    /**
     * This method allows user to start, end, 
     * and exit the game. 
     * @return JMenu is the menu. 
     */
    public JMenu getFileMenu() {
        final JMenu menu = new JMenu("File");

        myNewGame = new JMenuItem("New_Game");
        myNewGame.setEnabled(true);

        myNewGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsStarted ^= true;
                myTimer.start();          
                startGame(myIsStarted);
            }

        });

        menu.add(myNewGame);
        menu.addSeparator();
        myEndGame = new JMenuItem("End Game");
        myEndGame.setEnabled(false);
        myEndGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) { 
                myIsStarted ^= true;
                myMusicIsNotPaused ^= true; 

                startGame(myIsStarted);
                myTimer.stop();
                
            }
        });

        menu.add(myEndGame);
        menu.addSeparator();

        final JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final Window window = SwingUtilities.getWindowAncestor(menu);
                window.dispose();
                
                
            }
        });
        exit.setEnabled(true);
        menu.add(exit);
        return menu;
    }

    /**
     * A setter method that sets the game to start
     * or end. 
     * @param theIsStarted determines whether the game 
     * has started or not. 
     */
    public void setIsStarted(final boolean theIsStarted) {
        myIsStarted = theIsStarted; 
    }

    /**
     * A menu for selecting different game mode. 
     * @return JMenu for different game mode. 
     */
    public JMenu getGameMode() {
        final JMenu menu = new JMenu("Game_Mode");
        final ButtonGroup buttonGroup1 = new ButtonGroup();
        final JRadioButton husky = new JRadioButton("Husky_Mode");
        husky.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsHusky ^= true; 
                myIsBlack ^= true;
                myGameBoard.setIsHusky(myIsHusky);
                myGameBoard.setIsBlack(myIsBlack);

            }

        }); 
        husky.setSelected(true);

        buttonGroup1.add(husky);
        menu.add(husky);

        final JRadioButton black = new JRadioButton("Blue Mode");
        black.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsBlack ^= true;
                myIsHusky ^= true; 
                myGameBoard.setIsBlack(myIsBlack);
                myGameBoard.setIsHusky(myIsHusky);
            }
        });

        buttonGroup1.add(black);
        menu.add(black);

        menu.addSeparator();

        final ButtonGroup buttonGroup2 = new ButtonGroup();

        mySize1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.setBoardHeight(HEIGHT1);
                myBoard.setBoardWidth(BOARD_WIDTH);
                myGameBoard.setMyHeight(HEIGHT1);
                myGameBoard.setBoardData(null);
            }

        }); 
        mySize1.setSelected(true);
        buttonGroup2.add(mySize1);
        menu.add(mySize1);

        
        mySize2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myBoard.setBoardHeight(HEIGHT2);
                myBoard.setBoardWidth(BOARD_WIDTH);
                myGameBoard.setMyHeight(HEIGHT2);
                myGameBoard.setBoardData(null);
            }
        });

        buttonGroup2.add(mySize2);
        menu.add(mySize2);
        return menu;
    }


    /**
     * @return my delay time. 
     */
    public int getLevel() {
        return myDelayTime; 
    }

    /**
     * 
     * @return a JMenu that stores a 
     */
    public JMenu getEffect() {
        final JMenu menu = new JMenu("Options");

        final JCheckBoxMenuItem grid = new JCheckBoxMenuItem("Grid");
        grid.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myIsGrid ^= true; 
                myGameBoard.setIsGrid(myIsGrid);
            }

        }); 
        menu.add(grid);
        menu.addSeparator();

        mySlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent theEvent) {



                myDelayTime = mySlider.getValue();
                myDelayTime = (TIMER_DELAY + DECREMENT) 
                                - (myDelayTime * DECREMENT);

                myTimer.setDelay(myDelayTime);
            }

        });

        // Turn on labels at major tick marks. 
        final int majorTickSpacing = 3; 
        mySlider.setMajorTickSpacing(majorTickSpacing);
        mySlider.setMinorTickSpacing(1);
        mySlider.setPaintTicks(true);
        mySlider.setPaintLabels(true);
        final JMenu level = new JMenu("Speed");
        level.add(mySlider);
        menu.add(level);
        menu.addSeparator();
        
        final JCheckBoxMenuItem musicItem = new JCheckBoxMenuItem("Music");
        musicItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myMusicIsNotPaused ^= true;
                if (myMusicIsNotPaused) {
                    myPlayer.loop(DIRECTORY);
                    myKeyListener.setMyMusicIsPaused(true);
                    
                } else {
                    myPlayer.stop(DIRECTORY);
                    myKeyListener.setMyMusicIsPaused(false);
                }
                
            }

        }); 
        menu.add(musicItem);
        return menu;
    }

    
    /**
     * 
     * @return a menu that contain all score related informatio. 
     */
    public JMenu getScoreInfo() {
        final JMenu menu = new JMenu("Score Info");
        
        final JMenuItem getHighScore = new JMenuItem("Highest_Score");
        getHighScore.addActionListener(new ShowHighestScore());
        getHighScore.setEnabled(true);
        menu.add(getHighScore);
        menu.addSeparator();
        
        final JMenuItem calcultateScore =  new JMenuItem("CalculateScore");
        calcultateScore.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myGameBoard, 
                     "           1 line  2 lines   3 lines   4 lines cleared\n"
                     + "Level 1:     40      100       300       1200\n"
                     + "Level 2:     80      200       600       2400\n"
                     + "Level 3:    120      300       900       3600\n"
                     + "...\n"
                     + "Level 10:   400     1000      3000      12000\n\n"
                     + "In General\n"
                     + "Level n:    40*(n) 100*(n)    300*(n)   1200*(n)\n\n"
                     + "Add 4 points to the score when a piece freezes in place", 
                     "Score Calculation", 1);
            }

        }); 
        menu.add(calcultateScore);
        
        return menu; 
    }



    /**
     * Create a help menu bar and its menu items. 
     * @return a JMenu for the Help menu.
     */
    public JMenu getHelpMenu() {
        final JMenu menu = new JMenu("Help");
        final JMenuItem menuItem = new JMenuItem("About...");
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myGameBoard, 
                                              "TCSS 305 Tetris \n Winter 2016 "
                                                              + "\n Cindy Wang", "About", 1); 
            }
        });
        menu.add(menuItem);

        return menu; 
    }



    /**
     * A method that determines whether the game is over or not.
     * @param theObservable is the observable object.
     * @param theObject is an argument passed to the notifyObservers method.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof Boolean) {
            myIsStarted ^= true;
            myMusicIsNotPaused ^= true;  
            startGame(myIsStarted);
        }

    }
    

    
    /**
     * This method stores and shows the highest score. 
     * @author cindy Wang
     * @version 1.0
     */
    class ShowHighestScore implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            JOptionPane.showMessageDialog(myGameBoard, 
                                          String.valueOf(myScoreBoard.getMyHighestScore())); 
        }

    }
}
