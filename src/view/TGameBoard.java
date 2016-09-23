/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Block;


/**
 * The main game board that displays the tetris game. 
 * @author Cindy Wang
 * @version 1.0
 */
public class TGameBoard extends JPanel implements Observer {

    /**
     * A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = 2599064897905168420L;

    /**
     * A default UW purple. 
     */
    private static final Color PURPLE = new Color(51, 0, 111);

    /**
     * A default UW gold. 
     */
    private static final Color GOLD = new Color(232, 211, 162);

    /**
     * The number of blocks per height. 
     */
    private static final int NUM_BLOCKS_PER_HEIGHT = 20; 

    /**
     * The number of blocks per width. 
     */
    private static final int NUM_BLOCKS_PER_WIDTH = 10; 

    /**
     * The width of the east panel. 
     */
    private static final int EAST_BOARD_WIDTH = 195;

    /**
     * The height of the east panel. 
     */
    private static final int EAST_BOARD_HEIGHT = 160;
    /**
     * The size of the block. 
     */
    private static final int BLOCK_SIZE = 30; 

    /**
     * The number of east side panel. 
     */
    private static final int NUM_EAST_PANEL = 3; 
    
    /**
     * This variable allows the game over sign 
     * to be positioned in the center. 
     */
    private static final int SET_CENTER = 4; 
    
    /**
     * The font for the words.
     */
    private static final Font FONT = new Font("Arial", Font.BOLD, BLOCK_SIZE);
    
    /**
     * This variable offsets the position of the blocks when 
     * changing the panel size. 
     */
    private static final int OFFSET = -10; 
    
    
    /**
     * The size of the block. 
     */
    private float myMultiplier;

    /**
     * A list of block array. 
     */
    private List<Block[]> myBoardData;

    /**
     * An instant variable storing the new height
     * when the frame is changed. 
     */
    private float myNewHeight;

    /**
     * An instant variable that determines whether
     * it is a husky mode or not. 
     */
    private boolean myIsHusky; 

    /**
     * An instant variable that determines whether
     * it is a black and white mode or not. 
     */
    private boolean myIsBlack; 
    
    /**
     * An instant variable that determines whether 
     * the grid is shown or not. 
     */
    private boolean myIsDrawGrid; 
    
    
    /**
     * An instant variable that determines whether
     * the game is over or not. 
     */
    private boolean myIsGameOver; 
    
    /**
     * An instant variable that determines
     * the height of the panel. 
     */
    private int myNumHeightBlock; 

    /**
     * An instant variable that offset 
     * the location of the block when it 
     * first appears. 
     */
    private int myLocation; 
    
    

    /**
     * This is the constructor for the TGameBoard class.
     * @param theFrame is the tetris frame. 
     */
    public TGameBoard(final Frame theFrame) {
        super();
        final Frame frame = theFrame; 
        final JPanel temp = this;
        constructorHelper();

        setPreferredSize(new Dimension((int) (myMultiplier * NUM_BLOCKS_PER_WIDTH), 
                                       (int) (myMultiplier * NUM_BLOCKS_PER_HEIGHT)));

        addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent theEvent) {
                myNewHeight = temp.getHeight();
                reSize();

                temp.setPreferredSize(new Dimension(
                    (int) Math.ceil(myMultiplier * NUM_BLOCKS_PER_WIDTH), 
                    (int) Math.ceil(myMultiplier * NUM_BLOCKS_PER_HEIGHT) + 1));

                frame.setMinimumSize(new Dimension(
                    (int) Math.ceil(myMultiplier * NUM_BLOCKS_PER_WIDTH + myMultiplier) 
                     + EAST_BOARD_WIDTH, (int) EAST_BOARD_HEIGHT * (NUM_EAST_PANEL + 1) + 1));
            }
        });
        
    }

    /**
     * A private helper method for the constructor. 
     */
    private void constructorHelper() {
        myMultiplier = BLOCK_SIZE;
        myIsDrawGrid = false; 
        myNumHeightBlock = NUM_BLOCKS_PER_HEIGHT;
        myIsHusky = true;
        myIsBlack = false;         
    }

    /**
     * This method switches the height of the panel. 
     * @param theHeight is the height of the panel. 
     */
    public void setMyHeight(final int theHeight) {
        myNumHeightBlock = theHeight;
        this.setLocation(0, 0);
        if (myNumHeightBlock == (-1 * OFFSET)) {
            myLocation = OFFSET; 
            
        } else {
            myLocation = 0; 
           
        }
        
        setPreferredSize(new Dimension((int) (myMultiplier * NUM_BLOCKS_PER_WIDTH), 
                                       (int) (myMultiplier * myNumHeightBlock)));
    }

    


    /**
     * This method resizes the board and pieces in proportion. 
     */
    public void reSize() {
        myMultiplier = myNewHeight / NUM_BLOCKS_PER_HEIGHT;
        repaint(); 
    }

    /**
     * The getter that returns the size of the block. 
     * @return the size of the block. 
     */
    public float getMultiplier() {
        return myMultiplier;
    }

    /**
     * @param theIsGrid determines whether the grid
     * should be drawn. 
     */
    public void setIsGrid(final boolean theIsGrid) {
        myIsDrawGrid = theIsGrid; 
        repaint();
    }
    
    /**
     * 
     * @param theIsHusky determines whether the husky
     * mode is selected or not. 
     */
    public void setIsHusky(final boolean theIsHusky) {
        System.out.println(theIsHusky);
        myIsHusky = theIsHusky; 
        repaint();
    }

    /**
     * 
     * @param theIsBlack determines whether the black
     * and white mode is selected or not. 
     */
    public void setIsBlack(final boolean theIsBlack) {
        myIsBlack = theIsBlack; 
        repaint();
    }
    
    
    /**
     * @param theGraphics draws the graphics.
     */
    public void paintComponent(final Graphics theGraphics) {
        final Graphics2D g2D = (Graphics2D) theGraphics;  
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());

        
        g2D.setColor(Color.WHITE);
        g2D.drawRect(0, 0, (int) (myMultiplier * NUM_BLOCKS_PER_WIDTH + 1), 
                     (int) (myMultiplier * myNumHeightBlock));
        
        if (myIsHusky) {
            drawHusky(theGraphics);
        } 
        
        if (myIsBlack) {
            drawBlackAndWhite(theGraphics);
        }

        if (myIsDrawGrid) {
            drawGrid(theGraphics);
        }
        
        
        if (myIsGameOver) {
            drawGameOver(theGraphics);
        }
    }


    /**
     * This method draws the game over display board. 
     * @param theGraphics draws the graphics. 
     */
    public void drawGameOver(final Graphics theGraphics) {
        final Graphics2D g2D = (Graphics2D) theGraphics; 
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0, 0, getWidth(), getHeight());
        g2D.setFont(FONT);
        g2D.setColor(Color.WHITE);
        g2D.drawString("Game Over",  (int) (myMultiplier * NUM_BLOCKS_PER_WIDTH / SET_CENTER), 
                       getHeight() / 2);

    }



    /**
     * This method draws the grid. 
     * @param theGraphics draws the graphics. 
     */
    private void drawGrid(final Graphics theGraphics) {
        final Graphics2D g2D = (Graphics2D) theGraphics; 
        g2D.setStroke(new BasicStroke(1));
        g2D.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < myNumHeightBlock; i++) {
            for (int j = 0; j <= NUM_BLOCKS_PER_WIDTH; j++) {
                g2D.drawLine(0, (int) (i * myMultiplier), 
                             (int) (NUM_BLOCKS_PER_WIDTH * myMultiplier), 
                             (int) (i * myMultiplier));
                g2D.drawLine((int) (j * myMultiplier), 
                             0, 
                             (int) (j * myMultiplier), 
                             (int) (myNumHeightBlock * myMultiplier));

            }
        }

    }

    /**
     * This method draws the tetris pieces for the 
     * husky mode. 
     * @param theGraphics draws the graphics. 
     */
    private void drawHusky(final Graphics theGraphics) {
        final Graphics2D g2D = (Graphics2D) theGraphics; 
        if (myBoardData != null) {
            for (int i = 1; i <= myNumHeightBlock; i++) {
                final Block[] blocks = myBoardData.get(i - 1);
                for (int j = 0; j < NUM_BLOCKS_PER_WIDTH; j++) {
                    if (blocks[j] != null) {
                        g2D.setColor(GOLD);
                        g2D.drawRect((int) (j * myMultiplier), 
                                     (int) (myNewHeight - (i * myMultiplier) 
                                                     + (myLocation * myMultiplier)),
                                     (int) myMultiplier,
                                     (int) myMultiplier);

                        g2D.setColor(PURPLE);
                        g2D.fillRect((int) (j * myMultiplier) + 1, 
                                     (int) (myNewHeight - (i * myMultiplier) + 1 
                                                     + (myLocation * myMultiplier)),
                                     ((int) myMultiplier) - 1,
                                     ((int) myMultiplier) - 1);
                    }
                }
            }
        }
    }

    /**
     * This method draws the tetris pieces
     * for the black and white mode. 
     * @param theGraphics draws the graphics.
     */
    private void drawBlackAndWhite(final Graphics theGraphics) {
        final Graphics2D g2D = (Graphics2D) theGraphics; 
        if (myBoardData != null) {
            for (int i = 1; i <= myNumHeightBlock; i++) {
                final Block[] blocks = myBoardData.get(i - 1);
                for (int j = 0; j < NUM_BLOCKS_PER_WIDTH; j++) {
                    if (blocks[j] != null) {
                        g2D.setColor(Color.BLUE);
                        g2D.fillRect((int) (j * myMultiplier) + 1, 
                                     (int) (myNewHeight - (i * myMultiplier) + 1 
                                                     + (myLocation * myMultiplier)),
                                     ((int) myMultiplier) - 1,
                                     ((int) myMultiplier) - 1);
                    }
                }
            }
        }

    }
    
    /**
     * A setter method that sets whether the game 
     * is over or not. 
     * @param theIsGameOver sets the game to be over
     * or not. 
     */
    public void setIsGameOver(final boolean theIsGameOver) {
        myIsGameOver = theIsGameOver;
    }
    
    /**
     * A getter method that shows whether the game 
     * is over or not. 
     * @return myIsGameOver shows whether the game 
     * is over or not. 
     */
    public boolean isGameOver() {
        return myIsGameOver; 
    }
    
    
    /**
     * A setter method that sets the board data. 
     * @param theBoardData sets the board data. 
     */
    public void setBoardData(final List<Block[]> theBoardData) {
        myBoardData = theBoardData; 
    }
    
    /**
     * A method that passed in block data when the game is over.
     * @param theObservable is the observable object.
     * @param theObject is an argument passed to the notifyObservers method.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof List<?>) {
            myBoardData = (List<Block[]>) theObject;
            
            
        }
        
        if (theObject instanceof Boolean) {
            myIsGameOver = (boolean) theObject;
        }

        repaint();
    }




}
