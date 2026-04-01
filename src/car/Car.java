package car;

import track.Tile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * File name: Car.java
 *
 * Description:
 * Represents a car object in the game.
 * The car has a position, velocity vector, orientation angle,
 * and can optionally display a sprite image.
 */
public class Car {

    private double xPos;
    private double yPos;


    /** Direction the car is facing (in radians). */
    private double angle = 0;
    private double grip;
    private double accelerationMultiplier;
    private double dragConstant = 0.98;
    private double maxSpeed = 6.0;

    /** Vector representing the car's velocity. */
    private VelocityVector velocity;

    /** File name of the car sprite image. */
    private String imgFileName;

    /** Loaded image used to draw the car. */
    private BufferedImage img;

    /** Indicates whether the car is drifting. */
    private boolean drift = false;

    // Added to keep track of car positioning on track, used by later game logic will
    private int lap;
    private int checkPointCount;
    private boolean checkpointCooldown;
    // Used to make sure we don't increment Finish, might need to be a data structure where we just look at the last amount
    // of Tiles
    private Tile lastTile;

    private double startTime;
    private double endTime;
    private double raceTime;


    /**
     * Constructs a Car object at the specified position.
     *
     * @param xPos starting x-coordinate
     * @param yPos starting y-coordinate
     * @param imgFileName name of the image file used for the car sprite
     */
    public Car(double xPos, double yPos, String imgFileName){
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = new VelocityVector();
        this.imgFileName = imgFileName;
        // Set to true initially, so it can count checkpoints it crosses
        this.checkpointCooldown = true;
        this.lap = 0;
        loadImage();
    }

    // --------------------
    // Movement Controls
    // --------------------

    /**
     * Accelerates the car in the direction it is currently facing.
     *
     * @param force amount of acceleration applied to the velocity vector
     */
    public void accelerate(double force) {
        force *= accelerationMultiplier;
        double xForce = Math.cos(angle) * force;
        double yForce = Math.sin(angle) * force;
        velocity.add(xForce, yForce);
    }

    /**
     * Rotates the car by the specified angle.
     *
     * @param amount change in angle (radians)
     */
    public void turn(double amount) {
        angle += amount;

        // here, if the car is not drifting we set the velocity vector to the angle,
        // instead of letting it slowly change
        if (!drift){
            velocity.rotateTo(angle*grip);
        }
    }

    /**
     * Updates the car's position based on its velocity.
     * Also applies friction and limits the maximum speed.
     */
    public void move() {

        // move based on the velocity vector
        xPos += velocity.getXComp();
        yPos += velocity.getYComp();

        // drag
        drag();

        // max speed
        velocity.limit(maxSpeed);

    }

    // --------------------
    // Drawing
    // --------------------

    /**
     * Loads the car sprite image from the /data resource directory.
     *
     * @throws IllegalArgumentException if the image file cannot be found
     */
    public void loadImage() {
        if (imgFileName != null) {
            final String resource = "/data/" + imgFileName;
            try {
                img = ImageIO.read(this.getClass().getResource(resource));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Image not found at '" + resource + "'");
                throw new IllegalArgumentException("Sprite imgFileName not found at '" + resource + "'");
            }
        }
    }

    /**
     * Draws the car onto the screen using the provided Graphics object.
     * The car is rotated according to its current angle.
     *
     * @param g graphics context used for rendering
     */
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        int width = 15;
        int height = 25;

        int centerX = (int)xPos + width / 2;
        int centerY = (int)yPos + height / 2;

        g2.rotate(angle, centerX, centerY);

        if (img == null)
            g2.fillRect((int)xPos, (int)yPos, width, height);
        else
            g2.drawImage(img, (int)xPos, (int)yPos, null);
    }

    // --------------------
    // Getters
    // --------------------

    /**
     * Returns the car's current orientation angle.
     *
     * @return angle in radians
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Sets whether the car is drifting.
     *
     * @param isDrift true if the car should be drifting
     */
    public void setDrift(boolean isDrift) {
        drift = isDrift;
    }

    /**
     * Returns whether the car is currently drifting.
     *
     * @return true if drifting
     */
    public boolean isDrift() {
        return drift;
    }


    /**
     * Scales friction
     */
    private void drag(){
        velocity.scale(dragConstant);
    }


    // Getters/Setters for lap + lastTile + checkPointCount
    public void setLap(int lap) {
        this.lap = lap;
    }

    public int getLap() {
        return lap;
    }

    public void incrementLap() {
        lap += 1;
    }

    public void resetLap() {
        lap = 0;
    }

    public int getCheckPointCount() {
        return checkPointCount;
    }

    public void incrementCheckpointCount() {
        checkPointCount += 1;
    }

    public void resetCheckpointCount() {
        checkPointCount = 0;
    }

    public void setLastTile(Tile tile) {
        lastTile = tile;
    }

    public Tile getLastTile() {
        return lastTile;
    }

    public int[] getPos(){
        return new int[]{(int)xPos,(int)yPos};
    }

    public void setGrip(double amount){ grip = amount;}

    public void setAccelerationMultiplier(double amount){ accelerationMultiplier = amount;}

    public void setMaxSpeed(double amount){ maxSpeed = amount;}

    public boolean getCheckpointCooldown() {
        return checkpointCooldown;
    }

    public void setCheckpointCooldown(boolean a) {
        checkpointCooldown = a;
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public void stopTimer(){
        endTime = System.currentTimeMillis();
        raceTime = endTime-startTime;
        raceTime/=1000;
    }

    public double getCurrentTime(){
        return (System.currentTimeMillis() - startTime)/1000;
    }

    public double getRaceTime(){
        return raceTime;
    }


}