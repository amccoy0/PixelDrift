

package car;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CarController.java
 * Code Description: Controls the main game loop and user input for the car simulation.
 * This class manages keyboard controls, updates the car's movement,
 * and handles drawing the car on the screen.
 */
public class CarController extends TimerTask implements KeyListener {

    /** Time between game updates in milliseconds. */
    public static final int TIME_TO_UPDATE = 10;

    /** Main game window. */
    private final JFrame gameJFrame;

    /** Container used for drawing the game. */
    private final Container contentPane;

    /** Movement control flags based on key presses. */
    private boolean up, down, left, right, drift;

    /** Timer used to repeatedly update the game. */
    private final Timer gameTimer = new Timer();

    /** The car object being controlled in the game. */
    private final Car car;

    /** Indicates whether the game is currently running. */
    private boolean gameRunning = false;


    /**
     * Entry point of the program. Creates the game controller
     * and starts the game window.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        new CarController("Pixel Drift", 50, 50, 800, 600);
    }

    /**
     * Constructs the game window and initializes the car and game loop.
     *
     * @param title window title
     * @param x window x-position on screen
     * @param y window y-position on screen
     * @param width window width
     * @param height window height
     */
    public CarController(String title, int x, int y, int width, int height) {
        gameJFrame = new JFrame(title);
        gameJFrame.setSize(width, height);
        gameJFrame.setLocation(x, y);
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = gameJFrame.getContentPane();
        contentPane.setLayout(null);

        // Create a car
        car = new Car(100, 100, "src/data/testCar.png");

        gameJFrame.addKeyListener(this);
        gameJFrame.setVisible(true);

        // Schedule repeated updates
        gameTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);
    }

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
        repaint();
    }

    /**
     * Clears the screen and redraws the car.
     */
    private void repaint() {
        Graphics g = contentPane.getGraphics();
        if (g != null) {
            g.clearRect(0, 0, contentPane.getWidth(), contentPane.getHeight());
            car.draw(g);
        }
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
     * Not used in this implementation.
     *
     * @param e key event
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}