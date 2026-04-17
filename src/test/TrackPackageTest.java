package test;

import track.Tile;


/**
 * TrackPackageTest.java
 * Author: August McCoy
 * Code Description: This is the test class for all classes that can be tested in the track package.
 */
public class TrackPackageTest {
    /** boolean for all tests for all classes being successful */
    boolean allSuccess;

    /** boolean for Tile class tests being successful */
    boolean tileSuccess;

    /** boolean for Track class tests being successful */
    boolean trackSuccess;

    /** boolean for CheckPointGroups class tests being successful */
    boolean checkpointSuccess;

    /**
     * Constructor for TrackPackageTest, runs tests for all testable classes in track package
     */
    public TrackPackageTest() {
        allSuccess = true;

        if (testTile() == false) {
            System.err.println("At least one test for Tile class failed");
            allSuccess = false;
        } else {
            System.out.println("All tests for Tile class passed");
        }

        if (testTrack() == false) {
            System.err.println("At least one test for Track class failed");
            allSuccess = false;
        } else {
            System.out.println("All tests for Track class passed");
        }

        if (testCheckpoint() == false) {
            System.err.println("At least one test for CheckPointGroups class failed");
            allSuccess = false;
        } else {
            System.out.println("All tests for Tile CheckPointGroups passed");
        }

        if (allSuccess) {
            System.out.println("All tests passed for all classes");
        } else {
            System.err.println("At least one test failed for either Tile, Track, or CheckPointGroups");
        }
    }

    /**
     * Main to run the class and tests
     *
     * @param args, The tests being run
     */
    public static void main(String[] args) {
        new TrackPackageTest();
    }

    /**
     * This is the method that will test all edge cases and return values for the Tile class
     *
     * @return tileSuccess, true if all tests passed, false otherwise
     */
    private boolean testTile() {
        tileSuccess = true;

        // Initialize a tile array of all tile types
        char[] tileChars = {'D', 'G', 'S', 'F', 'C', 'B', 'W'};
        Tile[] tiles = new Tile[7];
        int index = 0;
        Tile.Surface testSurface;

        for (char c: tileChars) {
            // Location doesnt matter at tile level we are testing return values
            tiles[index] = new Tile(0, 0, c);
            index++;
        }

        // Test all tiles in array for expected returns, cannot test color because it is randomly varied
        for (Tile tile: tiles) {
            if (tile.getTileType() == 'D') {
                // Dirt
                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.97) {
                    System.err.println("Dirt surface did not return expected 0.97 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .8) {
                    System.err.println("Dirt surface did not return expected 0.8 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 2.0) {
                    System.err.println("Dirt surface did not return expected 2.0 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Dirt surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'G') {
                // Grass

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.93) {
                    System.err.println("Grass surface did not return expected 0.93 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .75) {
                    System.err.println("Grass surface did not return expected 0.75 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 1.2) {
                    System.err.println("Grass surface did not return expected 1.2 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Grass surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'S') {
                // Sand

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.95) {
                    System.err.println("Sand surface did not return expected 0.95 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .78) {
                    System.err.println("Sand surface did not return expected 0.78 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 2.3) {
                    System.err.println("Sand surface did not return expected 2.3 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Sand surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'F') {
                // Finish

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.97) {
                    System.err.println("Finish surface did not return expected 0.97 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .8) {
                    System.err.println("Finish surface did not return expected 0.8 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 2.0) {
                    System.err.println("Finish surface did not return expected 2.0 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Finish surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'C') {
                // Checkpoint

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.97) {
                    System.err.println("Checkpoint surface did not return expected 0.97 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .8) {
                    System.err.println("Checkpoint surface did not return expected 0.8 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 2.0) {
                    System.err.println("Checkpoint surface did not return expected 2.0 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != true) {
                    System.err.println("Checkpoint surface did not return expected true checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'B') {
                // Barrier

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.93) {
                    System.err.println("Barrier surface did not return expected 0.93 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .75) {
                    System.err.println("Barrier surface did not return expected 0.75 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 1.2) {
                    System.err.println("Barrier surface did not return expected 1.2 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Barrier surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'W') {
                // Wall

                testSurface = tile.getSurface();

                // Enumeration value tests
                if (testSurface.grip != 0.93) {
                    System.err.println("Wall surface did not return expected 0.93 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .75) {
                    System.err.println("Wall surface did not return expected 0.75 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 1.2) {
                    System.err.println("Wall surface did not return expected 1.2 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != false) {
                    System.err.println("Wall surface did not return expected false checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else {
                tileSuccess = false;
                System.err.println("Tile class failed to return tileType in expected range of types");
            }

            // Location test for x, y pos
            if (tile.getXPos() != 0 || tile.getYPos() != 0) {
                tileSuccess = false;
                System.err.println("Tile should have returned Xpos = 0, Ypos = 0. Tile returned x, y = " + tile.getXPos() + ", " +
                        tile.getYPos());
            }
        }

        return tileSuccess;
    }

    /**
     * This is the method that will test all edge cases and return values for the Track class
     *
     * @return trackSuccess, true if all tests passed, false otherwise
     */
    private boolean testTrack() {
        trackSuccess = true;

        return trackSuccess;
    }

    /**
     * This is the method that will test all edge cases and return values for the CheckPointGroups class
     *
     * @return checkpointSuccess, true if all tests passed, false otherwise
     */
    private boolean testCheckpoint() {
        checkpointSuccess = true;

        return checkpointSuccess;
    }

}
