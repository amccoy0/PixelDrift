/**
 * File name: Car.java
 *
 * Description:
 * Represents a car object in the game.
 * The car has a position, velocity vector, orientation angle,
 * and can optionally display a sprite image.
 */

package car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Car {

    private double xPos;
    private double yPos;

    /** Direction the car is facing (in radians). */
    private double angle = 0;

    /** Vector representing the car's velocity. */
    private VelocityVector velocity;

    /** File name of the car sprite image. */
    private String imgFileName;

    /** Loaded image used to draw the car. */
    private BufferedImage img;

    /** Indicates whether the car is drifting. */
    private boolean drift = false;

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
    }

    /**
     * Updates the car's position based on its velocity.
     * Also applies friction and limits the maximum speed.
     */
    public void move() {

        // move based on the velocity vector
        xPos += velocity.getXComp();
        yPos += velocity.getYComp();

        // friction
        velocity.scale(0.97);

        // max speed
        velocity.limit(6);
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

        g2.rotate(-angle, centerX, centerY);
    }

    // --------------------
    // Getters
    // --------------------

    /**
     * Returns the car's velocity vector.
     *
     * @return velocity vector
     */
    public VelocityVector getVelocity() {
        return velocity;
    }

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
}