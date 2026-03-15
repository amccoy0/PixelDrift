package car;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Car {

    private double xPos;
    private double yPos;

    private double angle = 0;      // direction car is facing
    private Vector velocity;       // movement vector

    private String imgFileName;
    private BufferedImage img;

    private boolean drift = false;


    public Car(double xPos, double yPos, String imgFileName){
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = new Vector();
        this.imgFileName = imgFileName;
    }

    // --------------------
    // Movement Controls
    // --------------------

    public void accelerate(double force) {
        double xForce = Math.cos(angle) * force; // add force in the x direction
        double yForce = Math.sin(angle) * force; // add force in the y direction
        velocity.add(xForce, yForce);
    }

    public void turn(double amount) {
        angle += amount; // change the angle, only for the image
    }

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

    public Vector getVelocity() {
        return velocity;
    }

    public double getAngle() {
        return angle;
    }

    public void setDrift(boolean isDrift) { drift = isDrift;}

    public boolean isDrift() {return drift;}
}