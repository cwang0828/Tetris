/**
* TCSS 305 Winter 2016.
* Assignment 6 Tetris.
*/

package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 * This class provides a visual indication of the keys that control the game.
 * @author Cindy Wang
 * @version 1.0
 */
public class TKeyBoard extends JPanel {

    /**
     * A generated serial version UID for object Serialization. 
     */
    private static final long serialVersionUID = 4852534607107563075L;

    /**
     * The number of key commands. 
     */
    private static final int GRID_HEIGHT = 8; 
    
    /**
     * The size of the title font. 
     */
    private static final float TITLE_SIZE = 20.0f; 
    
    /**
     * A list of JLabels that stores the visual indication of the keys
     * control the game. 
     */
    private final List<JLabel> myLabels; 
    
    /**
     * The constructor for the TKeyBoard class. 
     */
    public TKeyBoard() {
        super();
        myLabels = new ArrayList<>();
        setLayout(new GridLayout(GRID_HEIGHT, 1));
        final TitledBorder title = new TitledBorder("Commands");
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        title.setTitleFont(getFont().deriveFont(TITLE_SIZE));
        setBorder(title);
        this.setBackground(Color.BLACK);
        createKeyCommands();
    }
    
    
    /**
     * This method allows the key commands to be shown on the 
     * Tetris board. 
     */
    private void createKeyCommands() {
        
        myLabels.add(new JLabel("              Move Left: Left"));
        myLabels.add(new JLabel("              Move Right: Right"));
        myLabels.add(new JLabel("              Move Down: Up"));
        myLabels.add(new JLabel("              Drop: Down"));
        
        myLabels.add(new JLabel("              Rotate CCW: 1"));
        myLabels.add(new JLabel("              Rotate CW: 3"));
        myLabels.add(new JLabel("              Pause: P"));
        
        for (int i = 0; i < myLabels.size(); i++) {
            myLabels.get(i).setForeground(Color.WHITE);
            this.add(myLabels.get(i));
        }

    }
    

}
