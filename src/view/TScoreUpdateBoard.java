/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;


import java.awt.Color;
import java.awt.GridLayout;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;




import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import model.Board;
import model.TetrisPiece;


/**
 * The panel for the score. 
 * @author cindy Wang
 * @version 1.0
 */
public class TScoreUpdateBoard extends JPanel implements Observer {

    /**
     * A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = 2169600208471104304L;

    /**
     * The added score when one line is completed. 
     */
    private static final int ONE_LINE = 40; 

    /**
     * The added score when two line is completed. 
     */
    private static final int TWO_LINE = 100; 

    /**
     * The added score when three line is completed. 
     */
    private static final int THREE_LINE = 300; 

    /**
     * The added score when four line is completed. 
     */
    private static final int FOUR_LINE = 1200; 

    /**
     * The line completed before entering to the next level. 
     */
    private static final int NEXT_LEVEL = 4; 

    /**
     * The title font. 
     */
    private static final float TITLE_FONT = 20.0f;

    /**
     * The sub-title font.
     */
    private static final float SUBTITLE_FONT = 14.0f;

    /**
     * The default score and level.
     */
    private static final String DEFAULT_TEXT = "0";

    /**
     * The number of panels inside the score panel. 
     */
    private static final int NUM_PANEL = 4; 

    /**
     * The amount of time that is decremented each time
     * when the level goes up. 
     */
    private static final int TIME_DECREMENT = 50; 

    /**
     * The number of points gained every time 
     * when a piece is frozed. 
     */
    private static final int FROZEN_POINT = 4; 

    /**
     * The maximum number of lines that are needed
     * to complete before going to the next level. 
     */
    private static final int MAX_LINE = 5; 

    /**
     * An instant variable storing
     * the slider that control the speed
     * of the game. 
     */
    private final JSlider mySlider; 

    /**
     * My current score. 
     */
    private int myScore; 

    /**
     * My current level. 
     */
    private int myLevel; 

    /**
     * JLabel storing my current score. 
     */
    private final  JLabel myScoreLabel; 

    /**
     * JLabel storing my current level. 
     */
    private final JLabel myLevelLabel;

    /**
     * JLable storing the number of lines completed. 
     */
    private final JLabel myLineLabel;

    /**
     * JLabel storing the number of lines needed to 
     * go up to the next level. 
     */
    private final JLabel myLineNeededLabel;

    /**
     * An array that stores the line numbers that 
     * have been cleared each time. 
     */
    private Integer[] myLineList;

    /**
     * The total number of lines that are cleared
     * during a game. 
     */
    private int myLineSum;

    /**
     * An instant variable storing the delay time. 
     */
    private int myTime; 

    /**
     * An instant variable storing the highest score. 
     */
    private int myCurrentHighestScore;



    /**
     * An instant variable storing the number
     * of frozen piece. 
     */
    private int myCount; 

    /**
     * An instant variable that stores the timer. 
     */
    private final Timer myTimer; 


    /**
     * An instant variable storing the number of lines 
     * needed to go to the next level. 
     */
    private int myLineNeeded;

    /**
     * An instant variable that stores the previous highest
     * score read from the text file. 
     */
    private Integer myHighScore; 


    /**
     * The constructor for the TScoreUpdateBoard.
     * @param theTimer is the timer
     * @param theSlider is the slider that allows the user 
     * to manually setting the speed / level. 
     * @param theHighScore is the previous highest score
     * stores in the text file. 
     */
    public TScoreUpdateBoard(final Timer theTimer, 
                             final JSlider theSlider, final int theHighScore) {
        super();
        mySlider = theSlider; 
        myTimer = theTimer; 
        myHighScore = theHighScore; 
        myScoreLabel = new JLabel();
        myLevelLabel = new JLabel();
        myLineLabel = new JLabel();
        myLineNeededLabel = new JLabel();
        constructorHelper();
        createLayout();
    }

    /**
     * A private helper method for the constructor. 
     */
    private void constructorHelper() {
        setOpaque(true);
        this.setBackground(Color.BLACK);
        myCount = 0; 
        myCurrentHighestScore = myHighScore;
        myScore = 0; 
        myLevel = 1;
        myLineSum = 0;
    }

    /**
     * This method creates the layout of the score board. 
     */
    private void createLayout() {    
        final TitledBorder title = new TitledBorder("Update");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        title.setTitleFont(getFont().deriveFont(TITLE_FONT));
        setBorder(title);
        setLayout(new GridLayout(NUM_PANEL, 1));

        myScoreLabel.setForeground(Color.WHITE);
        myLevelLabel.setForeground(Color.WHITE);
        myLineLabel.setForeground(Color.WHITE);
        myLineNeededLabel.setForeground(Color.WHITE);

        myScoreLabel.setFont(myScoreLabel.getFont().deriveFont(SUBTITLE_FONT));
        myLevelLabel.setFont(myLevelLabel.getFont().deriveFont(SUBTITLE_FONT));
        myLineLabel.setFont(myLineLabel.getFont().deriveFont(SUBTITLE_FONT));
        myLineNeededLabel.setFont(myLineNeededLabel.getFont().deriveFont(SUBTITLE_FONT));

        myScoreLabel.setText(DEFAULT_TEXT);
        myLevelLabel.setText("1");
        myLineLabel.setText(DEFAULT_TEXT);
        myLineNeededLabel.setText(String.valueOf(MAX_LINE));

        final JPanel scorePanel = new JPanel();
        final JPanel levelPanel = new JPanel();
        final JPanel linePanel = new JPanel();
        final JPanel lineNeededPanel = new JPanel(); 

        scorePanel.setBackground(Color.BLACK);
        final TitledBorder titleScore = new TitledBorder("Score");
        titleScore.setTitleJustification(TitledBorder.CENTER);
        titleScore.setTitleColor(Color.WHITE);
        scorePanel.setBorder(titleScore);
        scorePanel.add(myScoreLabel);

        levelPanel.setBackground(Color.BLACK);
        final TitledBorder titleLevel = new TitledBorder("Level");
        titleLevel.setTitleJustification(TitledBorder.CENTER);
        titleLevel.setTitleColor(Color.WHITE);
        levelPanel.setBorder(titleLevel);
        levelPanel.add(myLevelLabel);

        linePanel.setBackground(Color.BLACK);
        final TitledBorder titleLine = new TitledBorder("Line");
        titleLine.setTitleJustification(TitledBorder.CENTER);
        titleLine.setTitleColor(Color.WHITE);
        linePanel.setBorder(titleLine);
        linePanel.add(myLineLabel);

        lineNeededPanel.setBackground(Color.BLACK);
        final TitledBorder titleLineNeeded = new TitledBorder("Line Needed");
        titleLineNeeded.setTitleJustification(TitledBorder.CENTER);
        titleLineNeeded.setTitleColor(Color.WHITE);
        lineNeededPanel.setBorder(titleLineNeeded);
        lineNeededPanel.add(myLineNeededLabel);

        add(scorePanel);
        add(levelPanel);
        add(linePanel);
        add(lineNeededPanel);
    }


    /**
     * This method calculate the score and the level. 
     */
    private void calculateScoreAndLevel() {
        final int currentLine = myLineList.length;
        myLineSum += myLineList.length;
        int multiplier = 0; 

        if (currentLine == 1) {
            multiplier = ONE_LINE;
        } else if (currentLine == 2) {
            multiplier = TWO_LINE;
        } else if (currentLine == (NEXT_LEVEL - 1)) {
            multiplier = THREE_LINE;
        } else if (currentLine == NEXT_LEVEL) {
            multiplier = FOUR_LINE; 
        }

        myLevel = (myLineSum / (NEXT_LEVEL + 1)) + 1;
        myScore += myLevel * multiplier;
        System.out.println("The score = " + myScore);
        updateScores();

        myLineNeeded = MAX_LINE - (myLineSum % MAX_LINE);
    }

    /**
     * A getter method that allows access
     * to the highest score. 
     * @return my highest score. 
     */
    public int getMyHighestScore() {
        return myHighScore;
    }

    /**
     * Compare the current high score 
     * with the previously played high score. 
     */
    private void updateScores() {
        if (myScore > myCurrentHighestScore) {
            myCurrentHighestScore = myScore;
        }
    }

    /**
     * Compare the current high score 
     * with the previous high score stores in the 
     * text file.      
     */
    public void updateHighestScore() {
        if (myCurrentHighestScore > myHighScore) {
            myHighScore = myCurrentHighestScore;
        }

    }

    /**
     * A setter method setting the total number 
     * of lines being cleared. 
     * @param theLineSum is the total number of lines 
     * that are cleared. 
     */
    public void setMyLineSum(final int theLineSum) {
        myLineSum = theLineSum;
    }

    /**
     * A setting method setting the current level. 
     * @param theLevel is the current level. 
     */
    public void setMyLevel(final int theLevel) {
        myLevel = theLevel;
    }

    /**
     * A setter method setting the current score. 
     * @param theScore is the current score. 
     */
    public void setMyScore(final int theScore) {
        myScore = theScore;
    }

    /**
     * A getter method for the current level. 
     * @return the current level. 
     */
    public int getLevel() {
        return myLevel; 
    }

    /**
     * 
     * @return the highest score. 
     */
    public int getHighestScore() {
        return myHighScore; 
    }


    /**
     * A setter method that sets the number
     * of frozen pieces. 
     * @param theCount is the number of
     * frozen pieces. 
     */
    public void setMyCount(final int theCount) {
        myCount = theCount;
    }


    /**
     * Update changes from the Board.
     * @param theObservable is the observable object.
     * @param theObject is an argument passed to the notifyObservers method.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {

        if (theObservable instanceof Board && theObject instanceof Integer[]) {
            myLineList = (Integer[]) theObject;

            int oldLineSum = 1;
            oldLineSum = myLineSum; 

            calculateScoreAndLevel();
            if ((oldLineSum / (NEXT_LEVEL + 1))  < (myLineSum / (NEXT_LEVEL + 1))) {
                myTime = myTimer.getDelay() - TIME_DECREMENT; 
                myTimer.setDelay(myTime);
                mySlider.setValue(myLevel);
            }
        }


        if (theObject instanceof TetrisPiece) {         
            myCount++; 
            if (myCount > 1) {                
                myScore += FROZEN_POINT;
                updateScores();
                myScoreLabel.setText(String.valueOf(myScore));
                myLevelLabel.setText(String.valueOf(myLevel));
                myLineLabel.setText(String.valueOf(myLineSum));
                myLineNeededLabel.setText(String.valueOf(myLineNeeded));
            }
        }


    }



}
