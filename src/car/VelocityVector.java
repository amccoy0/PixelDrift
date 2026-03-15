/**
 * File name: VelocityVector.java
 *
 * Description:
 * Represents a 2D vector used for movement and physics calculations.
 * Stores x and y components and provides basic vector operations
 * such as addition, scaling, magnitude, and limiting speed.
 */

package car;


public class VelocityVector {

    /** X component of the vector. */
    private double xComp;

    /** Y component of the vector. */
    private double yComp;

    /**
     * Creates a vector with zero magnitude.
     */
    public VelocityVector() {
        this(0, 0);
    }

    /**
     * Creates a vector with specified x and y components.
     *
     * @param x x-component of the vector
     * @param y y-component of the vector
     */
    public VelocityVector(double x, double y) {
        this.xComp = x;
        this.yComp = y;
    }

    // ----- Getters -----

    /**
     * Returns the x-component of the vector.
     *
     * @return x-component value
     */
    public double getXComp() {
        return xComp;
    }

    /**
     * Returns the y-component of the vector.
     *
     * @return y-component value
     */
    public double getYComp() {
        return yComp;
    }

    // ----- Basic Operations -----

    /**
     * Adds values to the vector components.
     * Often used to apply acceleration or force.
     *
     * @param x amount added to the x-component
     * @param y amount added to the y-component
     */
    public void add(double x, double y) {
        this.xComp += x;
        this.yComp += y;
    }

    /**
     * Scales the vector by a constant factor.
     * Commonly used to simulate friction or drag.
     *
     * @param factor value to multiply both components by
     */
    public void scale(double factor) {
        this.xComp *= factor;
        this.yComp *= factor;
    }

    /**
     * Calculates the magnitude (length) of the vector.
     *
     * @return magnitude of the vector
     */
    public double getMagnitude() {
        return Math.sqrt(xComp * xComp + yComp * yComp);
    }

    /**
     * Returns the direction of the vector in radians.
     *
     * @return angle of the vector
     */
    public double getAngle() {
        return Math.atan2(yComp, xComp);
    }

    /**
     * Limits the magnitude of the vector to a maximum value.
     * If the vector exceeds the maximum speed, it is scaled down.
     *
     * @param max maximum allowed magnitude
     */
    public void limit(double max) {
        double mag = getMagnitude();
        if (mag > max) {
            scale(max / mag);
        }
    }
}