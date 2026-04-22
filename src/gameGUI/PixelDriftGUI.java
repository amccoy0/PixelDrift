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
 * Code Description: This is the GUI class that will run the Pixel Drift Game
 */
public class PixelDriftGUI implements KeyListener, MouseListener, ActionListener, ItemListener {

    /**
     * Time between game updates in milliseconds.
     */
    public static final int TIME_TO_UPDATE = 10;

    /**
     * Countdown time for the race to start
     */
    private static final int COUNTDOWN = 3;

    /**
     * Number of laps required to finish
     */
    private static final int MAX_LAPS = 3;

    /**
     * Main game window.
     */
    private final JFrame gameJFrame;

    /**
     * JRadioButton array for track difficulty
     */
    private JRadioButton[] trackDifficulty;

    /**
     * JRadioButton array for gamemode
     */
    private JRadioButton[] gamemodeSelection;

    /**
     * JRadioButton for time trial gamemode
     */
    private JRadioButton timeTrialButton;

    /**
     * JRadioButton for two player gamemode
     */
    private JRadioButton twoPlayerButton;

    /**
     * JRadioButton for keep track of the currently selected track button
     */
    private JRadioButton currentTrackButton;

    /**
     * JRadioButton for keep track of the currently selected gamemode button
     */
    private JRadioButton currentGamemodeButton;

    /**
     * JRadioButton for easy track
     */
    private JRadioButton easyTrackButton;

    /**
     * JRadioButton for medium track
     */
    private JRadioButton mediumTrackButton;

    /**
     * JRadioButton for hard track
     */
    private JRadioButton hardTrackButton;

    /**
     * Button Group for track JRadioButtons
     */
    private ButtonGroup trackButtonGroup;

    /**
     * Button Group for gamemode JRadioButtons
     */
    private ButtonGroup gamemodeButtonGroup;

    /**
     * Boolean for whether player1 and player2 have finished all the laps of the race
     */
    private boolean player1Finished, player2Finished;

    /**
     * JPanels for gamemode JRadioButtons
     */
    private JPanel gamemodeButtonPanel;

    /**
     * JPanels for track JRadioButtons
     */
    private JPanel trackButtonPanel;

    /**
     * Play Button for menu to start game, has action listener
     */
    private JButton playButton;

    /**
     * This button on the menu will load an information pane of how to play
     */
    private JButton howToPlayButton;

    /**
     * This is the Image Icon for the keybindings to play the game
     */
    private ImageIcon controlsImage;

    /**
     * This is the controls JLabel to put the image icon into
     */
    private JLabel controlsLabel;

    /**
     * This is the Image Icon for the objectives of the game
     */
    private ImageIcon objectiveImage;

    /**
     * This is the objective JLabel to put the image icon into
     */
    private JLabel objectiveLabel;

    /**
     * This is the JPanel to put the controls and game objective JLbabel into
     */
    private JPanel controlsPanel;

    /**
     * This is the JPanel for all of the Exit, Menu, StartGame, HowToPlay buttons
     */
    private JPanel menuButtonPanel;

    /**
     * This button will be on the menu and will quit the game
     */
    private JButton exitButton;

    /**
     * This button will be on the how to play screen and will load the user back into the menu
     */
    private JButton menuButton;

    /**
     * Image for starting screen
     */
    private ImageIcon titleImage;

    /**
     * JLabel to add image to for starting screen
     */
    private JLabel titleLabel;

    /**
     * Image that changes based on what gamemode is selected
     */
    private ImageIcon menuGamemodeImage;

    /**
     * JLabel to add gamemode image to menu screen
     */
    private JLabel gamemodeImageLabel;

    /**
     * JPanel for the gamemode preview image
     */
    private JPanel gamemodeImagePanel;

    /**
     * Image to get a preview of the track
     */
    private ImageIcon trackImage;

    /**
     * JLabel to add image of track to menu screen
     */
    private JLabel trackImageLabel;

    /**
     * JPanel for the track preview image
     */
    private JPanel trackImagePanel;

    /**
     * JPanel for the menu
     */
    private JPanel menuPanel;

    /**
     * Container used for drawing the game.
     */
    private final Container contentPane;

    /**
     * Player 1 controls
     */
    private boolean up, down, left, right, drift;

    /**
     * Player 2 controls
     */
    private boolean up2, down2, left2, right2, drift2;

    /**
     * Timer used to repeatedly update the game.
     */
    private java.util.Timer gameTimer = new Timer();

    /**
     * The car objects being controlled in the game.
     */
    private Car[] cars;

    /**
     * Track the car's location
     */
    private Tile carTile1, carTile2;

    /**
     * Track the car's current Tile's surface
     */
    private Tile.Surface surface1, surface2;

    /**
     * Used to store track data
     */
    private Track track;

    /**
     * Used to draw track and cars
     */
    private TrackPanel trackPanel;

    /**
     * Indicates whether the game is currently running.
     */
    private boolean gameRunning;

    /**
     * Used for countdown timer, determines when the user can start the game countdown or not
     */
    private boolean startGame;

    /**
     * String array for JOptionPane options when game ends
     */
    private static final String[] endGameOptions = {"Go to Menu", "Play again"};

    /**
     * JPanel for infoPanel when racing
     */
    private JPanel infoPanel;

    /**
     * JLabel for timer
     */
    private JLabel timerLabel;

    /**
     * JLabel for lapCount
     */
    private JLabel lapLabel;

    /**
     * JLabel for gamemode
     */
    private JLabel modeLabel;

    /**
     * JLabel for player 2 time
     */
    private JLabel timerLabel2;

    /**
     * JLabel for player 2 lap
     */
    private JLabel lapLabel2;

    /**
     * Constant for acceleration amount
     */
    private final double ACCELL_AMOUNT = 0.067;

    /**
     * Constant for turn amount
     */
    private final double TURN_AMOUNT = 0.0167;

    /**
     * Boolean is game paused
     */
    private boolean gamePaused = false;

    /**
     * JPanel for pause overlay
     */
    private JPanel pauseOverlay;

    private int startX;
    private int startY;


    private final int bStartX = 300;
    private final int bStartY = 90;

    private final int iStartX = 300;
    private final int iStartY = 110;

    private final int hStartX = 160;
    private final int hStartY = 110;



    /**
     * Constructor
     */
    public PixelDriftGUI() {
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

    /**
     * Main so we can run the game which calls the PixelDriftGUI constructor
     *
     * @param args, the code being run
     */
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
        titleLabel = new JLabel(titleImage);
        contentPane.add(titleLabel);

        gameJFrame.pack();
        gameJFrame.setLocationRelativeTo(null);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // call method to load the menu screen
                menuScreen();
            }
        });

    }

    /**
     * This method will load a menu in the GUI that allows the user to select gamemode, and track difficulty.
     */
    private void menuScreen() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

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

        // Initialize gamemode image panel and image as well
        gamemodeImagePanel = new JPanel();
        menuGamemodeImage = new ImageIcon("src/data/timeTrialImage.png");
        gamemodeImageLabel = new JLabel(menuGamemodeImage);
        gamemodeImagePanel.add(gamemodeImageLabel, BorderLayout.CENTER);

        for (JRadioButton button : gamemodeSelection) {
            gamemodeButtonGroup.add(button);
        }

        // Add track buttons to respected panel
        for (JRadioButton button : gamemodeSelection) {
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

        // Initialize track image panel as well
        trackImagePanel = new JPanel();
        trackImage = new ImageIcon("src/data/easyTrackImage.png");
        trackImageLabel = new JLabel(trackImage);
        trackImagePanel.add(trackImageLabel, BorderLayout.CENTER);

        for (JRadioButton button : trackDifficulty) {
            trackButtonGroup.add(button);
        }

        // Add gamemode buttons to respected panel
        for (JRadioButton button : trackDifficulty) {
            trackButtonPanel.add(button);
        }

        // Play button
        playButton = new JButton("Play Game");
        playButton.addActionListener(this);

        // How to play button
        howToPlayButton = new JButton("How to Play");
        howToPlayButton.addActionListener(this);

        // Quit button
        exitButton = new JButton("Quit Game");
        exitButton.addActionListener(this);

        // Resize JFrame
        gameJFrame.setTitle("Pixel Drift Menu");


        // Set background to break-up panels
        gamemodeButtonPanel.setBackground(Color.LIGHT_GRAY);
        trackButtonPanel.setBackground(Color.LIGHT_GRAY);

        // create a temporary box layout panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        // Set panel alignment
        gamemodeButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamemodeImagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackImagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add panels
        menuPanel.add(gamemodeButtonPanel);
        menuPanel.add(gamemodeImagePanel);
        menuPanel.add(trackButtonPanel);
        menuPanel.add(trackImagePanel);

        // Create button panel
        menuButtonPanel = new JPanel(new GridLayout(1, 3));
        menuButtonPanel.add(exitButton);
        menuButtonPanel.add(howToPlayButton);
        menuButtonPanel.add(playButton);

        contentPane.add(menuPanel, BorderLayout.CENTER);
        contentPane.add(menuButtonPanel, BorderLayout.SOUTH);

        gameJFrame.pack();
        gameJFrame.setLocationRelativeTo(null);
        gameJFrame.setVisible(true);


    }

    /**
     * This method can be called from the menu to load a how to play information screen where the user can return to the
     * menu or quit the game.
     */
    private void howToPlayScreen() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        controlsImage = new ImageIcon("src/data/controls.png");
        controlsLabel = new JLabel(controlsImage);

        objectiveImage = new ImageIcon("src/data/objective.png");
        objectiveLabel = new JLabel(objectiveImage);

        controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        controlsPanel.add(controlsLabel);
        controlsPanel.add(objectiveLabel);

        menuButton = new JButton("Return to Menu");
        menuButton.addActionListener(this);

        menuButtonPanel = new JPanel(new GridLayout(1, 2));
        menuButtonPanel.add(exitButton);
        menuButtonPanel.add(menuButton);

        contentPane.add(controlsPanel, BorderLayout.CENTER);
        contentPane.add(menuButtonPanel, BorderLayout.SOUTH);

        gameJFrame.pack();
        gameJFrame.setLocationRelativeTo(null);
        gameJFrame.setVisible(true);

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
            cars[0].setCheckpointCooldown(false);
            track.hitCheckpointGroup(carTile1.getXPos(), carTile1.getYPos());
            checkpointTick(cars[0]);
            // Check if current tile is wall
        } else if (surface == Tile.Surface.WALL || surface == Tile.Surface.BARRIER) {
            cars[0].hitWall();
            // Check if current tile is finish
        } else if (surface == Tile.Surface.FINISH && cars[0].getCheckPointCount() >= track.getNumCheckpoints()) {
            cars[0].incrementLap();
            track.resetCheckpoints();
            // Check if car has reached max lap count and stop game
            if (cars[0].getLap() - 1 >= MAX_LAPS) { // -1 because we increment right at the beginning, so getLap represent the lap the car's on, not completed laps
                // Stop timer and game
                gameRunning = false;
                gameTimer.cancel();
                cars[0].stopTimer();

                // Display in message and ask if they want to go to the menu or play again
                JOptionPane.showMessageDialog(null, "You finished the track in: " + cars[0].getRaceTime() + " seconds!");
                int selection = JOptionPane.showOptionDialog(null, "Would you like to play again or go to the menu?",
                        "End of Race", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, endGameOptions, endGameOptions[0]);
                // Return to menu or call play again
                if (selection == 1) {
                    // Restart game
                    restartTimeTrial();
                } else {
                    // Go to menu
                    gameRunning = false;

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
        gameTimer.cancel();
        // Create trackPanel and add cars
        trackPanel = new TrackPanel(track, 1);
        trackPanel.setFocusable(true);
        cars = new Car[1];

        cars[0] = new Car(startX, startY, "RedCar.png");
        cars[0].reset(startX,startY);
        
        trackPanel.setCar(cars[0]);

        // Add trackPanel to gameJFrame
        gameJFrame.add(trackPanel);
        trackPanel.repaint();
        createInfoPanel();
        gameJFrame.pack();
        gameJFrame.revalidate();
        trackPanel.requestFocusInWindow();


        // Set title in here
        gameJFrame.setTitle("Time Trial Gamemode: Press W to Start Countdown");

        gameJFrame.setLocationRelativeTo(null);
        gameJFrame.setVisible(true);
        trackPanel.addKeyListener(this);
        startGame = true;
    }

    /**
     * This method will handle 2 player game logic
     */
    private void twoPlayer(Tile.Surface surface1, Tile.Surface surface2) {
        // Player 1 checkpoint
        if (surface1 == Tile.Surface.CHECKPOINT && cars[0].getCheckpointCooldown()) {
            cars[0].incrementCheckpointCount();
            cars[0].setCheckpointCooldown(false);
            checkpointTick(cars[0]);

            // Flash the hit checkpoint
            track.hitCheckpointGroup(carTile1.getXPos(), carTile1.getYPos());
            checkpointResetTick(track.getCheckpointGroupNum(carTile1.getXPos(), carTile1.getYPos()));
        } else if (surface1 == Tile.Surface.WALL || surface1 == Tile.Surface.BARRIER) {
            cars[0].hitWall();
        } else if (surface1 == Tile.Surface.FINISH && cars[0].getCheckPointCount() >= track.getNumCheckpoints()) {
            // Flash Finish line
            track.hitCheckpointGroup(carTile1.getXPos(), carTile1.getYPos());
            checkpointResetTick(track.getCheckpointGroupNum(carTile1.getXPos(), carTile1.getYPos()));

            cars[0].incrementLap();
            cars[0].resetCheckpointCount();
            if (cars[0].getLap() - 1 >= MAX_LAPS) { // -1 because we increment right at the beginning, so getLap represent the lap the car's on, not completed laps
                player1Finished = true;
                cars[0].stopTimer();
                if (player2Finished) {
                    endTwoPlayer();
                }
                ;
            }
        }

        // Player 2 checkpoint
        if (surface2 == Tile.Surface.CHECKPOINT && cars[1].getCheckpointCooldown()) {
            cars[1].incrementCheckpointCount();
            cars[1].setCheckpointCooldown(false);
            checkpointTick(cars[1]);

            // Flash the hit checkpoint
            track.hitCheckpointGroup(carTile2.getXPos(), carTile2.getYPos());
            checkpointResetTick(track.getCheckpointGroupNum(carTile2.getXPos(), carTile2.getYPos()));
        } else if (surface2 == Tile.Surface.WALL || surface2 == Tile.Surface.BARRIER) {
            cars[1].hitWall();
        } else if (surface2 == Tile.Surface.FINISH && cars[1].getCheckPointCount() >= track.getNumCheckpoints()) {

            // Flash Finish line
            track.hitCheckpointGroup(carTile2.getXPos(), carTile2.getYPos());
            checkpointResetTick(track.getCheckpointGroupNum(carTile2.getXPos(), carTile2.getYPos()));

            cars[1].incrementLap();
            cars[1].resetCheckpointCount();
            if (cars[1].getLap() - 1 >= MAX_LAPS) { // -1 because we increment right at the beginning, so getLap represent the lap the car's on, not completed laps
                player2Finished = true;
                cars[1].stopTimer();
                if (player1Finished) {
                    endTwoPlayer();
                }
            }
        }


    }

    /**
     * End of two player method, determines pathing to menu or play again
     */
    private void endTwoPlayer() {
        gameRunning = false;
        player1Finished = false;
        player2Finished = false;
        String winner;
        if (cars[0].getRaceTime() < cars[1].getRaceTime()) {
            winner = "Player 1 wins!";
        } else if (cars[1].getRaceTime() < cars[0].getRaceTime()) {
            winner = "Player 2 wins!";
        } else {
            winner = "It's a tie!";
        }
        JOptionPane.showMessageDialog(null, winner + "\nP1: " + cars[0].getRaceTime() + "s  P2: " + cars[1].getRaceTime() + "s");
        int selection = JOptionPane.showConfirmDialog(null, "Play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        if (selection == JOptionPane.YES_OPTION) {
            contentPane.removeAll();
            contentPane.revalidate();
            contentPane.repaint();
            // re-trigger actionPerformed logic by re-calling play setup
            actionPerformed(new ActionEvent(playButton, ActionEvent.ACTION_PERFORMED, ""));
        } else {
            gameTimer.cancel();
            contentPane.removeAll();
            contentPane.revalidate();
            contentPane.repaint();
            menuScreen();
        }
    }

    /**
     * Creates a swing timer that lasts 3 seconds that then allows key inputs, used at start of race
     */
    private void startCountdown() {
        // Set start game to false so the car can't move
        startGame = false;
        // Initialize an array with countdown so we can countdown every second
        int[] countdown = {COUNTDOWN};
        // Specify swing timer because of util timer
        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            // If countdown > 0 display and decrement
            if (countdown[0] > 0) {
                gameJFrame.setTitle(String.valueOf(countdown[0]));
                // Decrease countdown
                countdown[0]--;
            } else {
                // Stop timer and start game
                ((javax.swing.Timer) e.getSource()).stop();
                gameRunning = true;
                // Start car timers
                for (Car car : cars) {
                    car.startTimer();
                    // This is so the lap increments at the start
                    for (int i = 0; i < track.getNumCheckpoints(); i++) {
                        car.incrementCheckpointCount();
                    }
                }
                gameJFrame.setTitle("Go!");
                gameTimer = new java.util.Timer();

                // Had to do this to fix timertask bugs
                gameTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        PixelDriftGUI.this.run();
                    }
                }, 0, TIME_TO_UPDATE);

            }
        });

        timer.setInitialDelay(0);
        timer.start();

    }

    /**
     * This method is used so the user can't cross a checkpoint repeatedly, adds a delay for the Car attribute of
     * checkpoint cooldown to be set to true which is used in game logic to determine if the crossing of a checkpoint
     * incrememnts the cars checkpoint count.
     *
     * @param car the Car that hit the checkpoint
     */
    private void checkpointTick(Car car) {
        // Create timer for 1 second to set car's checkpoint cooldown to true
        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            // True means its over and can cross the checkpoint
            car.setCheckpointCooldown(true);
            ((javax.swing.Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * This method is used in two player mode so when either car hits a checkpoint it stays hit color for half a second
     * so both cars can visually see if they have hit the checkpoint.
     *
     * @param groupNum, the checkpointGroup number of the checkpoint group
     */
    private void checkpointResetTick(int groupNum) {
        // Create timer for half a second to reset a checkpoint groups color
        javax.swing.Timer timer = new javax.swing.Timer(500, null);
        timer.addActionListener(e -> {
            track.resetSpecificCheckpoint(groupNum);
            ((javax.swing.Timer) e.getSource()).stop();
        });
        timer.setRepeats(false);
        timer.start();
    }


    /**
     * Called repeatedly by the Timer to update the game state.
     * Handles movement controls and redraws the screen.
     */
    public void run() {

        if (!gameRunning || gamePaused) return;
        // Update
        if (timerLabel != null) {
            timerLabel.setText("Time: " + cars[0].getCurrentTime());
            lapLabel.setText("Lap: " + cars[0].getLap() + "/" + MAX_LAPS);
        }
        if (timerLabel2 != null && cars.length > 1) {
            timerLabel2.setText("Time: " + cars[1].getCurrentTime());
            lapLabel2.setText("Lap: " + cars[1].getLap() + "/" + MAX_LAPS);
        }

        // ---------- PLAYER 1 ----------
        cars[0].setDrift(drift);
        if (up) cars[0].accelerate(ACCELL_AMOUNT);
        if (down) cars[0].accelerate(-ACCELL_AMOUNT);
        if (left) cars[0].turn(-TURN_AMOUNT);
        if (right) cars[0].turn(TURN_AMOUNT);

        int[] carPos1 = cars[0].getPos();
        carTile1 = carPosToTile(carPos1);
        surface1 = carTile1.getSurface();

        // Time trial logic (only for single player)
        if (cars.length == 1) {
            timeTrial(surface1);
        }

        cars[0].setGrip(surface1.grip);
        cars[0].setAccelerationMultiplier(surface1.accelMultiplier);
        cars[0].setMaxSpeed(surface1.maxSpeed);

        if (cars.length == 1) {
            cars[0].move();
        }

        // ---------- PLAYER 2 ----------
        if (cars.length > 1) {

            cars[1].setDrift(drift2);
            if (up2) cars[1].accelerate(ACCELL_AMOUNT);
            if (down2) cars[1].accelerate(-ACCELL_AMOUNT);
            if (left2) cars[1].turn(-TURN_AMOUNT);
            if (right2) cars[1].turn(TURN_AMOUNT);

            int[] carPos2 = cars[1].getPos();
            carTile2 = carPosToTile(carPos2);
            surface2 = carTile2.getSurface();

            cars[1].setGrip(surface2.grip);
            cars[1].setAccelerationMultiplier(surface2.accelMultiplier);
            cars[1].setMaxSpeed(surface2.maxSpeed);
            twoPlayer(surface1, surface2);

            cars[0].move();
            cars[1].move();
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
            // Player 1
            case KeyEvent.VK_W -> {
                if (!gameRunning && startGame) {
                    startCountdown();
                } else if (gameRunning) {
                    up = true;
                }
            }

            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;

            // Player 2 (if necessary)
            case KeyEvent.VK_UP -> up2 = true;
            case KeyEvent.VK_DOWN -> down2 = true;
            case KeyEvent.VK_LEFT -> left2 = true;
            case KeyEvent.VK_RIGHT -> right2 = true;

            case KeyEvent.VK_SPACE -> {
                if (cars.length > 1) drift2 = true;
            }
            case KeyEvent.VK_SHIFT -> drift = true;  // P1 drift in both modes
        }
    }

    private Tile carPosToTile(int[] carPos) {
        return track.getCurrentTile(carPos[0], carPos[1]);
    }

    /**
     * Handles key release events and deactivates movement flags.
     *
     * @param e key event triggered by the user
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {

            // Player 1
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;

            // Player 2
            case KeyEvent.VK_UP -> up2 = false;
            case KeyEvent.VK_DOWN -> down2 = false;
            case KeyEvent.VK_LEFT -> left2 = false;
            case KeyEvent.VK_RIGHT -> right2 = false;

            case KeyEvent.VK_SPACE -> {
                if (cars.length > 1) drift2 = false;
            }
            case KeyEvent.VK_SHIFT -> drift = false;  // always release, no condition
        }
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Required method, not implemented
     *
     * @param e key event
     */
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
            for (JRadioButton gamemodeButton : gamemodeSelection) {
                if (e.getSource() == gamemodeButton) {
                    currentGamemodeButton = gamemodeButton;

                    // Swap gamemode image
                    if (currentGamemodeButton == timeTrialButton) {
                        menuGamemodeImage = new ImageIcon("src/data/timeTrialImage.png");
                    } else if (currentGamemodeButton == twoPlayerButton) {
                        menuGamemodeImage = new ImageIcon("src/data/twoPlayerImage.png");
                    }
                    gamemodeImageLabel.setIcon(menuGamemodeImage);
                    gamemodeImagePanel.repaint();
                }
            }
            for (JRadioButton trackButton : trackDifficulty) {
                if (e.getSource() == trackButton) {
                    currentTrackButton = trackButton;

                    // Swap track image
                    if (currentTrackButton == easyTrackButton) {
                        trackImage = new ImageIcon("src/data/easyTrackImage.png");
                    } else if (currentTrackButton == mediumTrackButton) {
                        trackImage = new ImageIcon("src/data/mediumTrackImage.png");
                    } else if (currentTrackButton == hardTrackButton) {
                        trackImage = new ImageIcon("src/data/hardTrackImage.png");
                    }
                    trackImageLabel.setIcon(trackImage);
                    trackImagePanel.repaint();
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
                track = new Track("src/data/easyTrack.txt", 4);
                startX = bStartX;
                startY = bStartY;
            } else if (currentTrackButton == mediumTrackButton) {
                // Medium track
                track = new Track("src/data/mediumTrack.txt", 4);
                startX = iStartX;
                startY = iStartY;
            } else if (currentTrackButton == hardTrackButton) {
                // Hard Track
                track = new Track("src/data/hardTrack.txt", 4);
                startX = hStartX;
                startY = hStartY;

            }

            // Game mode
            if (currentGamemodeButton == timeTrialButton) {
                restartTimeTrial();
            } else if (currentGamemodeButton == twoPlayerButton) {
                // Create trackPanel and add cars
                trackPanel = new TrackPanel(track, 2);
                trackPanel.setFocusable(true);
                cars = new Car[2];

                cars[0] = new Car(startX, startY, "RedCar.png");
                cars[0].reset(startX, startY);
                cars[1] = new Car(startX, startY-50, "PurpleCar.png");
                cars[1].reset(startX, startY-50);

                trackPanel.setCar(cars[0]);
                trackPanel.setCar(cars[1]);

                // Add trackPanel to gameJFrame
                gameJFrame.add(trackPanel, BorderLayout.CENTER);
                createInfoPanel();
                gameJFrame.pack();
                gameJFrame.revalidate();
                trackPanel.repaint();
                trackPanel.requestFocusInWindow();

                gameJFrame.setTitle("Two Player: Press W to Start Countdown");  // prompt player
                gameJFrame.setLocationRelativeTo(null);
                gameJFrame.setVisible(true);
                trackPanel.addKeyListener(this);
                startGame = true;  //allow countdown to begin
            }
        } else if (e.getSource() == exitButton) {
            // Show message then quit
            JOptionPane.showMessageDialog(null, "Thank you for playing PixelDrift!");
            System.exit(1);
        } else if (e.getSource() == howToPlayButton) {
            howToPlayScreen();
        } else if (e.getSource() == menuButton) {
            menuScreen();
        }
    }

    /**
     * This method is used to create the info panel on the right side of the screen.
     */
    private void createInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(200, 0));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.DARK_GRAY);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        modeLabel = new JLabel("Mode: " + currentGamemodeButton.getText());
        modeLabel.setForeground(Color.WHITE);
        modeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(modeLabel);
        infoPanel.add(Box.createVerticalStrut(20));

        // Player 1 section
        JLabel p1Header = new JLabel("-- Player 1 --");
        p1Header.setForeground(Color.RED);
        p1Header.setAlignmentX(Component.LEFT_ALIGNMENT);

        timerLabel = new JLabel("Time: 0.0");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lapLabel = new JLabel("Lap: 0/" + MAX_LAPS);
        lapLabel.setForeground(Color.WHITE);
        lapLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(p1Header);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(timerLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lapLabel);

        // Player 2 section (only in two player mode)
        if (cars.length > 1) {
            infoPanel.add(Box.createVerticalStrut(20));

            JLabel p2Header = new JLabel("-- Player 2 --");
            p2Header.setForeground(new Color(150, 0, 255));
            p2Header.setAlignmentX(Component.LEFT_ALIGNMENT);

            timerLabel2 = new JLabel("Time: 0.0");
            timerLabel2.setForeground(Color.WHITE);
            timerLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);

            lapLabel2 = new JLabel("Lap: 0/" + MAX_LAPS);
            lapLabel2.setForeground(Color.WHITE);
            lapLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);

            infoPanel.add(p2Header);
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(timerLabel2);
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(lapLabel2);
        }

        infoPanel.add(Box.createVerticalGlue()); // pushes pause button to bottom

        // Pause button
        JButton pauseButton = new JButton("Pause");
        pauseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        pauseButton.setMaximumSize(new Dimension(160, 30));
        pauseButton.addActionListener(e -> togglePause());
        infoPanel.add(pauseButton);

        contentPane.add(infoPanel, BorderLayout.EAST);
    }

    /**
     * Method to toggle pause
     */
    private void togglePause() {
        if (!gamePaused) {
            gamePaused = true;
            gameRunning = false;
            showPauseMenu();
        } else {
            hidePauseMenu();
            gamePaused = false;
            gameRunning = true;
            trackPanel.requestFocusInWindow();
        }
    }

    /**
     * Method to show pause menu when game is paused
     */
    private void showPauseMenu() {
        pauseOverlay = new JPanel();
        pauseOverlay.setLayout(new BoxLayout(pauseOverlay, BoxLayout.Y_AXIS));
        pauseOverlay.setBackground(Color.DARK_GRAY);
        pauseOverlay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel pauseLabel = new JLabel("PAUSED");
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton resumeButton = new JButton("Resume");
        JButton restartButton = new JButton("Restart");
        JButton menuButton2 = new JButton("Go to Menu");
        JButton quitButton = new JButton("Quit Game");

        for (JButton btn : new JButton[]{resumeButton, restartButton, menuButton2, quitButton}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(160, 30));
        }

        resumeButton.addActionListener(e -> togglePause());

        restartButton.addActionListener(e -> {
            hidePauseMenu();
            gamePaused = false;
            gameRunning = false;
            gameTimer.cancel();
            contentPane.removeAll();
            contentPane.revalidate();
            contentPane.repaint();
            actionPerformed(new ActionEvent(playButton, ActionEvent.ACTION_PERFORMED, ""));
        });

        menuButton2.addActionListener(e -> {
            hidePauseMenu();
            gamePaused = false;
            gameRunning = false;
            gameTimer.cancel();
            contentPane.removeAll();
            contentPane.revalidate();
            contentPane.repaint();
            menuScreen();
        });

        quitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Thank you for playing PixelDrift!");
            System.exit(0);
        });

        pauseOverlay.add(pauseLabel);
        pauseOverlay.add(Box.createVerticalStrut(15));
        pauseOverlay.add(resumeButton);
        pauseOverlay.add(Box.createVerticalStrut(5));
        pauseOverlay.add(restartButton);
        pauseOverlay.add(Box.createVerticalStrut(5));
        pauseOverlay.add(menuButton2);
        pauseOverlay.add(Box.createVerticalStrut(5));
        pauseOverlay.add(quitButton);

        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(pauseOverlay);
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    /**
     * Method to hide the pause menu
     */
    private void hidePauseMenu() {
        if (pauseOverlay != null) {
            infoPanel.remove(pauseOverlay);
            pauseOverlay = null;
            infoPanel.revalidate();
            infoPanel.repaint();
        }
    }
}
