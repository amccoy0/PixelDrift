package car;

/**
 * CarGameDriver
 * Code Description: A simple test driver for the Car class.
 * Runs a series of manual tests and reports success/failure.
 *
 */
public class CarGameDriver {
    // THIS CLASS WAS WRITTEN BY CHATGPT TO TEST THE CAR CLASS

    public static void main(String[] args) {
        boolean allSuccess = true;

        // Create tester instance
        CarGameDriver tester = new CarGameDriver(new Car(0, 0, null));

        if (!tester.testMove()) allSuccess = false;
        if (!tester.testTurn()) allSuccess = false;
        if (!tester.testLap()) allSuccess = false;
        if (!tester.testCheckpoint()) allSuccess = false;
        if (!tester.testReset()) allSuccess = false;
        if (!tester.testHitWall()) allSuccess = false;

        if (!allSuccess) {
            System.err.println("Error: At least one Car test failed");
        } else {
            System.out.println("Success: All Car tests passed");
        }
    }

    /** Car instance being tested */
    private final Car car;

    /**
     * Constructor for CarGameDriver
     *
     * @param car Car object to test
     */
    public CarGameDriver(Car car) {
        this.car = car;
        car.setAccelerationMultiplier(1.0);
        car.setGrip(1.0);
    }

    /**
     * Tests that the car moves when accelerated.
     *
     * @return true if test passes
     */
    private boolean testMove() {
        boolean success = true;

        car.reset(0, 0);
        car.accelerate(2.0);
        car.move();

        int[] pos = car.getPos();
        if (pos[0] == 0 && pos[1] == 0) {
            System.err.println("Car did not move after acceleration");
            success = false;
        }

        return success;
    }

    /**
     * Tests that turning the car changes its angle.
     *
     * @return true if test passes
     */
    private boolean testTurn() {
        boolean success = true;

        double before = car.getAngle();
        car.turn(Math.PI / 2);

        if (car.getAngle() == before) {
            System.err.println("Car angle did not change after turning");
            success = false;
        }

        return success;
    }

    /**
     * Tests lap increment functionality.
     *
     * @return true if test passes
     */
    private boolean testLap() {
        boolean success = true;

        car.reset(0, 0);
        car.incrementLap();

        if (car.getLap() != 1) {
            System.err.println("Lap did not increment correctly");
            success = false;
        }

        return success;
    }

    /**
     * Tests checkpoint counting and reset.
     *
     * @return true if test passes
     */
    private boolean testCheckpoint() {
        boolean success = true;

        car.reset(0, 0);
        car.incrementCheckpointCount();
        car.incrementCheckpointCount();

        if (car.getCheckPointCount() != 2) {
            System.err.println("Checkpoint count incorrect");
            success = false;
        }

        car.resetCheckpointCount();

        if (car.getCheckPointCount() != 0) {
            System.err.println("Checkpoint count did not reset");
            success = false;
        }

        return success;
    }

    /**
     * Tests that reset clears the car state properly.
     *
     * @return true if test passes
     */
    private boolean testReset() {
        boolean success = true;

        car.incrementLap();
        car.incrementCheckpointCount();

        car.reset(10, 20);

        int[] pos = car.getPos();

        if (pos[0] != 10 || pos[1] != 20) {
            System.err.println("Reset did not update position correctly");
            success = false;
        }

        if (car.getLap() != 0 || car.getCheckPointCount() != 0) {
            System.err.println("Reset did not clear game state");
            success = false;
        }

        return success;
    }

    /**
     * Tests that hitting a wall does not crash and changes movement.
     *
     * @return true if test passes
     */
    private boolean testHitWall() {
        boolean success = true;

        car.reset(0, 0);
        car.accelerate(5.0);
        car.move();

        int beforeX = car.getPos()[0];

        car.hitWall();
        car.move();

        int afterX = car.getPos()[0];

        if (beforeX == afterX) {
            System.err.println("Car did not react to wall collision");
            success = false;
        }

        return success;
    }
}