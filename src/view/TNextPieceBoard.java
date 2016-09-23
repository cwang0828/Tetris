/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Point;
import model.TetrisPiece;

/**
 * The panel that shows the next tetris piece. 
 * @author cindy Wang
 * @version 1.0
 *
 */
public class TNextPieceBoard extends JPanel implements Observer {

    /**
     *  A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -3893570013404262596L;
    
    /**
     * The title font. 
     */
    private static final float TITLE_FONT = 20.0f;
    
    /**
     * The width of the board. 
     */
    private static final int BOARD_WIDTH = 190;
    
    /**
     * The height of the board. 
     */
    private static final int BOARD_HEIGHT = 160;
    
    /**
     * The size of the block. 
     */
    private static final int BLOCK_SIZE = 40;
    
    /**
     * The size of the block. 
     */
    private static final double BLOCK_DIST = .5;

    /**
     * An instant variable that stores the next Tetris piece. 
     */
    private TetrisPiece myPiece; 
    
    /**
     * An instant variable that stores an array of points 
     * that shows the coordinates of my tetris piece. 
     */
    private Point[] myPieceCoordinate;
    
   
    /**
     * This is the constructor for the NextPieceBoard class. 
     */
    public TNextPieceBoard() {
        super();
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        final TitledBorder title = new TitledBorder("Next Piece");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        title.setTitleFont(getFont().deriveFont(TITLE_FONT));
        setBorder(title);
    }

    /**
     * This method draws the next tetris piece on to the panel.
     * @param theGraphic is the graphic. 
     */
    public void paintComponent(final Graphics theGraphic) {
        final Graphics2D g2D = (Graphics2D) theGraphic;
        g2D.setColor(Color.BLACK);
        g2D.fillRect(getX(), getY(), getWidth(), getHeight());
        
        if (myPiece != null && myPieceCoordinate != null) {
            
            for (final Point p: myPieceCoordinate) {
                // paint each of the Tetris block 
                // based on the provided coordinate.
                g2D.setColor(Color.WHITE);
                g2D.fillRect((int) ((p.x() + BLOCK_DIST) * BLOCK_SIZE), 
                             getHeight() - ((p.y() + 1) * BLOCK_SIZE), 
                             BLOCK_SIZE - 1, 
                             BLOCK_SIZE - 1);
            }
        }
    }
    
    /**
     * Update changes from the TetrisPiece.
     * @param theObservable is the observable object.
     * @param theObject is an argument passed to the notifyObservers method.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof TetrisPiece) {
            myPiece = (TetrisPiece) theObject;
            myPieceCoordinate = myPiece.getPoints();            
        }
        repaint();
    }

}
