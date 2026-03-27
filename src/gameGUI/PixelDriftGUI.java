package gameGUI;

import car.Car;
import track.Tile;
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
public class PixelDriftGUI extends TimerTask implements KeyListener, MouseListener, ActionListener, ItemListener {

    /** Time between game updates in milliseconds. */
    public static final int TIME_TO_UPDATE = 30;

    /** Countdown time for the race to start */
    private static final int COUNTDOWN = 3;

    /** Number of laps required to finish */
    private static final int MAX_LAPS = 1;

    /** Main game window. */
    private final JFrame gameJFrame;

    /** JRadio Buttons for track difficulty and gamemode */
    private JRadioButton[] trackDifficulty;
    private JRadioButton[] gamemodeSelection;
    private JRadioButton timeTrialButton;
    private JRadioButton twoPlayerButton;
    private JRadioButton currentTrackButton;
    private JRadioButton currentGamemodeButton;
    private JRadioButton easyTrackButton;
    private JRadioButton mediumTrackButton;
    private JRadioButton hardTrackButton;

    /** Button Groups for JRadioButtons */
    private ButtonGroup trackButtonGroup;
    private ButtonGroup gamemodeButtonGroup;



    /** Panels for JRadioButtons */
    private JPanel gamemodeButtonPanel;
    private JPanel trackButtonPanel;

    /** Play Button */
    private JButton playButton;

    /** Image for starting screen */
    private ImageIcon titleImage;
    private JLabel imageLabel;

    /** Container used for drawing the game. */
    private final Container contentPane;

    /** Movement control flags based on key presses. */
    private boolean up, down, left, right, drift;

    /** Timer used to repeatedly update the game. */
    private final java.util.Timer gameTimer = new Timer();

    /** The car objects being controlled in the game. */
    private Car[] cars;

    /** Track the car's location */
    private int[] carPos;
    private Tile carTile;
    private Tile.Surface carSurface;


    /** Used to store track data */
    private Track track;
    private TrackPanel trackPanel;

    /** Indicates whether the game is currently running. */
    private boolean gameRunning;

    /** Used for countdown timer */
    private boolean startGame;






    /**
     * Constructor
     */
    public PixelDriftGUI () {
        gameJFrame = new JFrame("Pixel Drift");
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = gameJFrame.getContentPane();

        // Set contentPane layout
        contentPane.setLayout(new BorderLayout());

        // I don't know where this will go
        //car.setLastTile(carPosToTile(car.getPos()));
        gameRunning = false;
        startGame = false;

        gameJFrame.addKeyListener(this);
        gameJFrame.setVisible(true);
        startingScreen();
    }

    public static void main(String[] args) {
        new PixelDriftGUI();
    }




    /**
     * This method loads an image to represent the starting screen of the game. It has a mouse listener for a mouse click
     * to see if the user is ready to play and then the loads the menu
     */
    private void startingScreen() {
        // Get starting image and add to contentPane
        titleImage = new ImageIcon("src/data/PixelDrift_Startup.png");
        imageLabel = new JLabel(titleImage);
        contentPane.add(imageLabel);

        gameJFrame.pack();
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Remove all components, revalidate and repaint to update the display
                contentPane.removeAll();
                contentPane.revalidate();
                contentPane.repaint();
                // call method to load the menu screen
                menuScreen();
            }
        });

    }

    /**
     * This method will load a menu in the GUI that allows the user to select gamemode, and track difficulty.
     */
    private void menuScreen() {
        // Set up JRadioButtons for gamemode selection
        gamemodeSelection = new JRadioButton[2]; // Change this if more gamemodes
        timeTrialButton = new JRadioButton("Time Trial");
        twoPlayerButton = new JRadioButton("Two Players");

        gamemodeSelection[0] = timeTrialButton;
        gamemodeSelection[1] = twoPlayerButton;

        timeTrialButton.setSelected(true);

        timeTrialButton.addItemListener(this);
        twoPlayerButton.addItemListener(this);


        // Add track buttons to respective button group, set easy track to selected
        gamemodeButtonGroup = new ButtonGroup();
        currentGamemodeButton = gamemodeSelection[0];

        // Initialize gamemode panel
        gamemodeButtonPanel = new JPanel(new GridLayout(2, 1, 0, 5));

        for (JRadioButton button: gamemodeSelection) {
            gamemodeButtonGroup.add(button);
        }

        // Add track buttons to respected panel
        for (JRadioButton button: gamemodeSelection) {
            gamemodeButtonPanel.add(button);
        }

        // Set up JRadioButtons for track selection
        trackDifficulty = new JRadioButton[3]; // Change this if more tracks
        easyTrackButton = new JRadioButton("Beginner Track");
        hardTrackButton = new JRadioButton("Professional Track");
        mediumTrackButton = new JRadioButton("Intermediate Track");

        trackDifficulty[0] = easyTrackButton;
        trackDifficulty[1] = mediumTrackButton;
        trackDifficulty[2] = hardTrackButton;

        easyTrackButton.setSelected(true);

        easyTrackButton.addItemListener(this);
        mediumTrackButton.addItemListener(this);
        hardTrackButton.addItemListener(this);

        // Add gamemode buttons to respective button group, set timetrial to selected
        trackButtonGroup = new ButtonGroup();
        currentTrackButton = trackDifficulty[0];

        // Initialize track panel
        trackButtonPanel = new JPanel(new GridLayout(3, 1, 0, 5));

        for (JRadioButton button: trackDifficulty) {
            trackButtonGroup.add(button);
        }

        // Add gamemode buttons to respected panel
        for (JRadioButton button: trackDifficulty) {
            trackButtonPanel.add(button);
        }

        // Play button
        playButton = new JButton("Play");
        playButton.addActionListener(this);

        // Resize JFrame
        gameJFrame.setTitle("Pixel Drift Menu");
        gameJFrame.setSize(400, 300);

        // Set background to break-up panels
        gamemodeButtonPanel.setBackground(Color.LIGHT_GRAY);
        trackButtonPanel.setBackground(Color.LIGHT_GRAY);

        // Add panels to contentPane
        contentPane.add(gamemodeButtonPanel, BorderLayout.NORTH);
        contentPane.add(trackButtonPanel, BorderLayout.CENTER);
        contentPane.add(playButton, BorderLayout.SOUTH);


        gameJFrame.setVisible(true);

        // load track here and set cars to track panel and then load


    }

    /**
     * This method will handle single player time trial logic, called by gameTimer
     *
     * @param surface
     */
    private void timeTrial(Tile.Surface surface) {
        // Update title with timer here

        // Check if current surface is checkpoint and the tick for checkpoint cooldown is over
        if (surface == Tile.Surface.CHECKPOINT && cars[0].getCheckpointCooldown()) {
            // Incremement checkpoint count and start checkpoint cooldown
            cars[0].incrementCheckpointCount();
            //cars[0].setCheckpointCooldown(false);
        // Check if current tile is finish
        }  else if (surface == Tile.Surface.FINISH && cars[0].getCheckPointCount() >= track.getNumCheckpoints()) {
            cars[0].incrementLap();
            // Check if car has reached max lap count and stop game
            if (cars[0].getLap() >= MAX_LAPS) {
                // Stop timer and game

                gameRunning = false;
                cars[0].stopTimer();
                // Display in message and ask if they want to go to the menu or play again
                JOptionPane.showMessageDialog(null, "You finished the track in: " +cars[0].getRaceTime() + " seconds!" );
                int selection = JOptionPane.showConfirmDialog(null,"Choose one",
                        "Would you like to play again?", JOptionPane.YES_NO_OPTION);
                // Return to menu or call play again
                if (selection == JOptionPane.YES_OPTION) {
                    // Restart game
                    restartTimeTrial();
                } else {
                    // Go to menu
                    gameRunning = false;
                    gameTimer.cancel();
                    // Erase contents
                    contentPane.removeAll();
                    contentPane.revalidate();
                    contentPane.repaint();
                    menuScreen();
                }
            }
            cars[0].resetCheckpointCount();

        }

    }

    /**
     * Sets up time trial, called whenever menu chooses time trial or when the user wants to play agin after finishing
     * a time trial race.
     */
    private void restartTimeTrial() {
        // Create trackPanel and add cars
        trackPanel = new TrackPanel(track, 1);
        trackPanel.setFocusable(true);
        cars = new Car[1];
        cars[0] = new Car(100, 100, "testCar.jpg");
        trackPanel.setCar(cars[0]);

        // Add trackPanel to gameJFrame
        gameJFrame.add(trackPanel);
        gameJFrame.pack();
        gameJFrame.revalidate();
        trackPanel.repaint();
        trackPanel.requestFocusInWindow();

        // Set title in here
        gameJFrame.setTitle("Time Trial Gamemode: Press W to Start Countdown");

        gameJFrame.setVisible(true);
        trackPanel.addKeyListener(this);
        startGame = true;
    }

    /**
     * This method will handle 2 player game logic
     */
    private void twoPlayer() {
        gameRunning = true;
        gameJFrame.requestFocusInWindow();
        // Schedule repeated updates
        gameTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);
    }

    /**
     * Creates a swing timer that lasts 3 seconds that then allows key inputs, used at start of race
     */
    private void startCountdown() {
        startGame = false;
        int[] countdown = {COUNTDOWN};
        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            if (countdown[0] > 0) {
                gameJFrame.setTitle(String.valueOf(countdown[0]));
                // Decrease countdown
                countdown[0]--;
            } else {
                // Stop timer and start game
                ((javax.swing.Timer)e.getSource()).stop();
                gameRunning = true;
                for (Car car : cars){
                    car.startTimer();
                }
                gameTimer.scheduleAtFixedRate(this, 0, TIME_TO_UPDATE);

            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * This method is called when a car crosses a checkpoint, creates a half second timer for
     */
    private void checkPointCooldownTimer(Car car) {}


    /**
     * Called repeatedly by the Timer to update the game state.
     * Handles movement controls and redraws the screen.
     */
    @Override
    public void run() {

        gameJFrame.setTitle("Time: "+cars[0].getCurrentTime());
        if (!gameRunning) return;
        for (Car car: cars) {
            car.setDrift(drift);
            if (up) car.accelerate(0.2);
            if (down) car.accelerate(-0.2);
            if (left) car.turn(-0.05);
            if (right) car.turn(0.05);

            carPos = car.getPos();
            carTile = carPosToTile(carPos);
            carSurface = carTile.getSurface();
            // Call Time trial for tile checking
            timeTrial(carSurface);

            car.setGrip(carSurface.grip);
            car.setAccelerationMultiplier(carSurface.accelMultiplier);
            car.setMaxSpeed(carSurface.maxSpeed);
            car.setDrift(drift);


            car.move();
        }
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
            case KeyEvent.VK_W -> {
                // Only start countdown if the game is not running
                if (!gameRunning && startGame) {
                    startCountdown();
                } else if (gameRunning){
                    up = true;
                }
            }
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SPACE -> drift = true;
        }
    }

    private Tile carPosToTile(int[] carPos){
        return track.getCurrentTile(carPos[0],carPos[1]);
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

    /**
     * This method is only called when a JRadioButton is selected. It reassigns the respective selected button for each
     * group of buttons.
     *
     * @param e the event to be processed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        // Check if one of the buttons is selected
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Check if source is track or gamemode
            for (JRadioButton gamemodeButton: gamemodeSelection) {
                if (e.getSource() == gamemodeButton) {
                    currentGamemodeButton = gamemodeButton;
                }
            }
            for (JRadioButton trackButton: trackDifficulty) {
                if (e.getSource() == trackButton) {
                    currentTrackButton =  trackButton;
                }
            }
        }
    }

    /**
     * This method is used to load the gamemode and the track selection, only used when playButton is clicked
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {

            // Remove all components, revalidate and repaint to update the display
            contentPane.removeAll();
            contentPane.revalidate();
            contentPane.repaint();
            startGame = true;
            // Initialize tracks
            if (currentTrackButton == easyTrackButton) {
                // Easy track
                track = new Track("src/data/track120x100.txt", 2);
            } else if (currentTrackButton == mediumTrackButton) {
                // Add medium track when I get it
            } else {
                // Hard track add when I draw it
            }

            // game mode
            if (currentGamemodeButton == timeTrialButton) {
                restartTimeTrial();
            } else if (currentGamemodeButton == twoPlayerButton) {
                // Create trackPanel and add cars
                trackPanel = new TrackPanel(track, 2);
                trackPanel.setFocusable(true);
                cars = new Car[2];
                cars[0] = new Car(100, 100, "testCar.jpg");
                cars[1] = new Car(50, 100, "testCar.jpg");
                trackPanel.setCar(cars[0]);
                trackPanel.setCar(cars[1]);

                // Add trackPanel to gameJFrame
                gameJFrame.add(trackPanel);
                gameJFrame.pack();
                gameJFrame.revalidate();
                trackPanel.repaint();

                gameJFrame.setVisible(true);

            }
        }
    }
}
