package gameGUI;

import car.Car;
import track.Track;
import track.TrackPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * PixelDriftGUI.java
 * Author: August McCoy
 * Code Descirption: This is the GUI class that will run the Pixel Drift Game
 */
public class PixelDriftGUI extends TimerTask implements KeyListener, MouseListener {

    /** Time between game updates in milliseconds. */
    public static final int TIME_TO_UPDATE = 30;

    /** Main game window. */
    private final JFrame gameJFrame;

    // Image for starting screen
    private Image titleImage;

    /** Container used for drawing the game. */
    private final Container contentPane;

    /** Movement control flags based on key presses. */
    private boolean up, down, left, right, drift;

    /** Timer used to repeatedly update the game. */
    private final java.util.Timer gameTimer = new Timer();

    /** The car object being controlled in the game. */
    private final Car car;


    // Practice track
    private final Track track;
    private final TrackPanel trackPanel;


    /** Indicates whether the game is currently running. */
    private boolean gameRunning = true;

    /**
     * Constructor
     */
    public PixelDriftGUI () {
        gameJFrame = new JFrame("Pixel Drift");
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = gameJFrame.getContentPane();

        // Set up trackPanel
        track = new Track("src/data/track120x100.txt");
        trackPanel = new TrackPanel(track, 1);
        gameJFrame.add(trackPanel);

        // Create a car and add it to trackPanel
        car = new Car(100, 100, "testCar.jpg");
        trackPanel.setCar(car);

        gameJFrame.pack();
        gameJFrame.setLocationRelativeTo(null);

        gameJFrame.addKeyListener(this);
        gameJFrame.setVisible(true);

        // Schedule repeated updates
        gameTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);
    }

    public static void main(String[] args) {
        new PixelDriftGUI();
    }

    private void singlePlayer() {

    }
    /* This method when implemented will load the starting screen, focusing on getting car + track first
    private void startingScreen() {
        titleImage = new ImageIcon("src/data/PixelDrift_Startup.png").getImage();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameRunning = true;
                repaint();
            }
        });

    }

     */

    /**
     * Called repeatedly by the Timer to update the game state.
     * Handles movement controls and redraws the screen.
     */
    @Override
    public void run() {
        if (!gameRunning) return;

        car.setDrift(drift);

        if (up) car.accelerate(0.2);
        if (down) car.accelerate(-0.2);
        if (left) car.turn(-0.05);
        if (right) car.turn(0.05);

        car.move();
        trackPanel.repaint();
    }

    // --------------------
    // Key Controls
    // --------------------

    /**
     * Handles key press events and activates movement flags.
     *
     * @param e key event triggered by the user
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> drift = true;
        }
    }

    /**
     * Handles key release events and deactivates movement flags.
     *
     * @param e key event triggered by the user
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
            case KeyEvent.VK_SPACE -> drift = false;
        }
    }

    /**
     * Required method from KeyListener.
     * Not used in this implementation yet but will be used in starting screen
     *
     * @param e key event
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
