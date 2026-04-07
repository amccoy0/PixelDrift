package track;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

import static track.Tile.getTileSize;

/**
 * TrackDrawerGUI.java
 * Code description: This program allows me to visually edit a track so I can generate new tracks.
 */
public class TrackDrawerGUI extends TimerTask implements ActionListener, ItemListener, MouseListener, MouseMotionListener {

    /** Time to redraw the track */
    private static final int TIME_TO_UPDATE = 10;

    /** Timer to update the track drawing */
    private final java.util.Timer drawingTimer = new java.util.Timer();

    /** The trackPanel we are using to visually see the track */
    private TrackPanel trackPanel;

    /** The track data structure we are editing, not overwriting */
    private Track track;

    /** The JFrame to draw the Track and add the buttons to */
    private JFrame drawingJFrame;

    /** Container for contentPane, trackPanel and buttonPanel will be displayed here */
    private final Container contentPane;

    /** This is the array of JRadioButtons that each tile type will be added to */
    private JRadioButton[] tileTypes;

    /** This is the buttonGroup for the tileType JRadioButtons so only one can be selected at a time */
    private ButtonGroup tileButtonGroup;

    /** This is the dirt Tile JRadioButton */
    private JRadioButton dirtButton;

    /** This is the sand Tile JRadioButton */
    private JRadioButton sandButton;

    /** This is the finish Tile JRadioButton */
    private JRadioButton finishButton;

    /** This is the checkpoint Tile JRadioButton */
    private JRadioButton checkpointButton;

    /** This is the grass Tile JRadioButton */
    private JRadioButton grassButton;

    /** This is the currently selected Tile JRadioButton */
    private JRadioButton currentTileButton;

    /** This is the currently selected Tile char so we can pass it to track and change a tile */
    private char currentTileChar;

    /** This is the panel that the JRadioButtons and the file button will be added to */
    private JPanel buttonPanel;

    /** This is the JTextField of the filename that we will write */
    private JTextField fileNameField;

    /** Button to write the file */
    private JButton fileButton;

    /**
     * Constructor for TrackDrawerGUI, sets up GUI
     */
    public TrackDrawerGUI() {
        // Set up JFrame and contentPane
        drawingJFrame = new JFrame("Track Drawer");
        drawingJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = drawingJFrame.getContentPane();

        // Set up JRadioButtons
        tileTypes = new JRadioButton[5];
        grassButton = new JRadioButton("Grass");
        dirtButton = new JRadioButton("Dirt");
        sandButton = new JRadioButton("Sand");
        checkpointButton = new JRadioButton("Checkpoint");
        finishButton = new JRadioButton("Finish");

        grassButton.setSelected(true);

        // Add buttons to array
        tileTypes[0] = grassButton;
        tileTypes[1] = dirtButton;
        tileTypes[2] = sandButton;
        tileTypes[3] = checkpointButton;
        tileTypes[4] = finishButton;

        // Add Item Listeners
        grassButton.addItemListener(this);
        dirtButton.addItemListener(this);
        sandButton.addItemListener(this);
        checkpointButton.addItemListener(this);
        finishButton.addItemListener(this);

        // Initialize button group and set currenTileButton + currentTileChar to grass button
        tileButtonGroup = new ButtonGroup();
        currentTileButton = tileTypes[0];
        currentTileChar = 'G';

        // Add buttons to the button group
        for (JRadioButton button: tileTypes) {
            tileButtonGroup.add(button);
        }

        // Create and add buttons to the panel
        buttonPanel = new JPanel(new GridLayout(2, 5, 0, 0));
        for (JRadioButton button: tileTypes) {
            // This should fill the first row with the buttons
            buttonPanel.add(button);
        }
        // Add text field and write button to the second row
        fileNameField = new JTextField("Filename");
        fileButton = new JButton("Save to File");
        fileButton.addActionListener(this);

        buttonPanel.add(fileNameField);
        buttonPanel.add(fileButton);

        contentPane.add(buttonPanel, BorderLayout.NORTH);

        // numCheckpoints doesn't really matter in this context because it is assigned in PixelDrift, we are just drawing.
        track = new Track("src/data/mediumTrack.txt", 2);
        trackPanel = new TrackPanel(track, 0);
        contentPane.add(trackPanel, BorderLayout.SOUTH);
        drawingJFrame.pack();

        // Add mouse listener
        trackPanel.addMouseListener(this);
        trackPanel.addMouseMotionListener(this);
        drawingJFrame.setVisible(true);

        // Start drawing
        drawingTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);
    }

    /**
     * Used to redraw the Track, called by drawinTimer every TIME_TO_UPDATE ms
     */
    @Override
    public void run() {
        trackPanel.repaint();
    }

    /**
     * Main to create a new TrackDrawerGUI and start the drawer
     *
     * @param args, the code being processed in main
     */
    public static void main(String[] args) {
        new TrackDrawerGUI();
    }

    /**
     * This method will write the track when we are finished editing it to a new file so we can use it later.
     *
     * @param filename the name of the file we are writing too
     */
    private void writeToFile(String filename) {
        // Write to file
        try (FileWriter writer = new FileWriter("src/data/" + fileNameField.getText())) {
            writer.write(track.getRows() + " " + track.getCols() + "\n"); // first line: rows cols
            for (int r = 0; r < track.getRows(); r++) {
                for (int c = 0; c < track.getCols(); c++) {
                    writer.write(track.getSpecificTile(r, c).getTileType());
                }
                writer.write("\n");
            }
            System.out.println("Track generated successfully!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This is only called when the file button is pressed which writes the track to a new file
     *
     * @param e the event to be processed, fileButton being clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileButton) {
            writeToFile(fileNameField.getText());
        }
    }

    /**
     * This is used to change what JRadioButton for tile choice is currently being selected
     *
     * @param e the event to be processed, JRadioButton selection
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        // Check if one of the buttons is selected
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Check what the source is
            for (JRadioButton tileType: tileTypes) {
                if (e.getSource() == tileType) {
                    currentTileButton = tileType;
                }
            }

            // Set currentTileChar so we can use it for drawing to pass to track
            if (currentTileButton == dirtButton) {
                currentTileChar = 'D';
            } else if (currentTileButton == sandButton) {
                currentTileChar = 'S';
            } else if (currentTileButton == finishButton) {
                currentTileChar = 'F';
            } else if (currentTileButton == checkpointButton) {
                currentTileChar = 'C';
            } else {
                currentTileChar = 'G';
            }
        }
    }

    /**
     * Repaints the tile when mouse clicked
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Get the X and Y position of the click
        int x = e.getX();
        int y = e.getY();

        // Change the tile clicked, need to change X + Y to incorporate which tile is actually being clicked
        track.changeTile(y / getTileSize(), x / getTileSize(), currentTileChar);
    }

    /**
     * Not used
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Not used
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Not used
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Not used
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Used when the mouse is dragged to repaint the track
     *
     * @param e the event to be processed, mouse drag click
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // Get the X and Y position of the click
        int x = e.getX();
        int y = e.getY();

        // Change the tile clicked
        track.changeTile(y / getTileSize(), x / getTileSize(), currentTileChar);
    }

    /**
     * Not used
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
