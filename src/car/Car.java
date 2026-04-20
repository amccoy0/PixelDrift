package car;

import track.Tile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
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

    /** The car's personal lapcount, starts at 0 */
    private int lap;
    /** How many checkpoints the car has crossed in the lap, used to determine whether or not the car can incrememnt lap */
    private int checkPointCount;
    /** Used in a timer, true when the car can cross a checkpoint and have it count */
    private boolean checkpointCooldown;
    // Used to make sure we don't increment Finish, might need to be a data structure where we just look at the last amount
    // of Tiles
    private Tile lastTile;

    private double startTime;
    private double endTime;
    private double raceTime;

    private double prevX;
    private double prevY;

    private boolean canAccelerate = true;


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
        if (canAccelerate) {
            force *= accelerationMultiplier;
            double xForce = Math.cos(angle) * force;
            double yForce = Math.sin(angle) * force;
            velocity.add(xForce, yForce);
        }
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
        prevX = xPos;
        prevY = yPos;
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

        AffineTransform original = g2.getTransform();  // save

        g2.rotate(angle, centerX, centerY);

        if (img == null)
            g2.fillRect((int)xPos, (int)yPos, width, height);
        else
            g2.drawImage(img, (int)xPos, (int)yPos, null);

        // Restore the original transform so subsequent cars are not drawn with this car's rotation applied
        g2.setTransform(original);
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
     * Applies drag to the car's velocity.
     * This simulates friction and gradually slows the car.
     */
    private void drag(){
        velocity.scale(dragConstant);
    }


    /**
     * Sets the current lap count.
     *
     * @param lap lap number
     */
    public void setLap(int lap) {
        this.lap = lap;
    }

    /**
     * Returns the current lap count.
     *
     * @return lap number
     */
    public int getLap() {
        return lap;
    }

    /**
     * Increments the lap counter by one.
     */
    public void incrementLap() {
        lap += 1;
    }

    /**
     * Resets the lap counter to zero.
     */
    public void resetLap() {
        lap = 0;
    }

    /**
     * Returns number of checkpoints crossed.
     *
     * @return checkpoint count
     */
    public int getCheckPointCount() {
        return checkPointCount;
    }

    /**
     * Increments checkpoint counter.
     */
    public void incrementCheckpointCount() {
        checkPointCount += 1;
    }

    /**
     * Resets checkpoint counter.
     */
    public void resetCheckpointCount() {
        checkPointCount = 0;
    }

    /**
     * Sets the last tile the car touched.
     *
     * @param tile tile object
     */
    public void setLastTile(Tile tile) {
        lastTile = tile;
    }

    /**
     * Returns the last tile the car touched.
     *
     * @return tile object
     */
    public Tile getLastTile() {
        return lastTile;
    }

    /**
     * Returns current position of car.
     *
     * @return array containing x and y position
     */
    public int[] getPos(){
        return new int[]{(int)xPos,(int)yPos};
    }

    /**
     * Sets the grip value of the car.
     *
     * @param amount grip value
     */
    public void setGrip(double amount){ grip = amount;}

    /**
     * Sets acceleration multiplier.
     *
     * @param amount acceleration multiplier
     */
    public void setAccelerationMultiplier(double amount){ accelerationMultiplier = amount;}

    /**
     * Sets maximum speed of the car.
     *
     * @param amount max speed
     */
    public void setMaxSpeed(double amount){ maxSpeed = amount;}

    /**
     * Returns checkpoint cooldown state.
     *
     * @return true if cooldown active
     */
    public boolean getCheckpointCooldown() {
        return checkpointCooldown;
    }

    /**
     * Sets checkpoint cooldown.
     *
     * @param a cooldown state
     */
    public void setCheckpointCooldown(boolean a) {
        checkpointCooldown = a;
    }

    /**
     * Starts race timer.
     */
    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Stops race timer and records race time.
     */
    public void stopTimer(){
        endTime = System.currentTimeMillis();
        raceTime = endTime-startTime;
        raceTime/=1000;
    }

    /**
     * Returns current elapsed race time.
     *
     * @return time in seconds
     */
    public double getCurrentTime(){
        return (System.currentTimeMillis() - startTime)/1000;
    }

    /**
     * Returns total race time.
     *
     * @return race time in seconds
     */
    public double getRaceTime(){
        return raceTime;
    }

    /**
     * Handles collision with a wall using automatic normal detection.
     */
    public void hitWall() {
        canAccelerate = false;
        // move back outside wall
        xPos = prevX;
        yPos = prevY;

        // get current velocity
        double vx = velocity.getXComp();
        double vy = velocity.getYComp();

        // approximate normal along the biggest component
        double absVx = Math.abs(vx);
        double absVy = Math.abs(vy);
        double normalX = 0;
        double normalY = 0;

        if (absVx > absVy) {
            normalX = (vx > 0) ? -1 : 1;  // vertical wall
        } else {
            normalY = (vy > 0) ? -1 : 1;  // horizontal wall
        }

        hitWall(normalX, normalY);

        // optional tiny push to prevent sticking
        velocity.add(normalX * 0.05, normalY * 0.05);
        canAccelerate = true;
    }

    /**
     * Reflects the car velocity using the provided wall normal.
     *
     * @param normalX x component of wall normal
     * @param normalY y component of wall normal
     */
    public void hitWall(double normalX, double normalY) {
        double length = Math.sqrt(normalX * normalX + normalY * normalY);
        normalX /= length;
        normalY /= length;

        double dot = velocity.getXComp() * normalX + velocity.getYComp() * normalY;

        // reflect: remove 2x the wall-facing component (not 1x)
        velocity.add(
                -2 * dot * normalX,
                -2 * dot * normalY
        );

        // dampen slightly so it doesn't feel rubbery
        velocity.scale(0.8);
    }

    /**
     * Resets the car to a clean state for a new race.
     * Zeroes velocity, angle, lap count, checkpoint count,
     * and all physics state so no data bleeds in from a previous game.
     *
     * @param xPos new starting x position
     * @param yPos new starting y position
     */
    public void reset(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.angle = 0;
        this.velocity = new VelocityVector();   // zero velocity
        this.drift = false;
        this.lap = 0;
        this.checkPointCount = 0;
        this.checkpointCooldown = true;
        this.lastTile = null;
        this.canAccelerate = true;
        this.prevX = xPos;
        this.prevY = yPos;
        this.startTime = 0;
        this.endTime = 0;
        this.raceTime = 0;
    }
}