package car;

public class Vector {

    private double xComp;
    private double yComp;

    public Vector() {
        this(0, 0);
    }

    public Vector(double x, double y) {
        this.xComp = x;
        this.yComp = y;
    }

    // ----- Getters -----

    public double getXComp() {
        return xComp;
    }

    public double getYComp() {
        return yComp;
    }

    // ----- Basic Operations -----

    // Add force / acceleration
    public void add(double x, double y) {
        this.xComp += x;
        this.yComp += y;
    }

    // Multiply by factor (friction / drag)
    public void scale(double factor) {
        this.xComp *= factor;
        this.yComp *= factor;
    }

    // Magnitude (speed)
    public double getMagnitude() {
        return Math.sqrt(xComp * xComp + yComp * yComp);
    }

    // Direction of movement
    public double getAngle() {
        return Math.atan2(yComp, xComp);
    }

    // Clamp to max speed
    public void limit(double max) {
        double mag = getMagnitude();
        if (mag > max) {
            scale(max / mag);
        }
    }


}