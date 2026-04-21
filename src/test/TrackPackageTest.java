package test;

import track.Tile;
import track.Track;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TrackPackageTest.java
 * Code Description: This is the test class for all classes that can be tested in the track package. I used claude.ai to
 * make the tests for the checkpointgroups and track.
 */
public class TrackPackageTest {
    /**
     * boolean for all tests for all classes being successful
     */
    boolean allSuccess;

    /**
     * boolean for Tile class tests being successful
     */
    boolean tileSuccess;

    /**
     * boolean for Track class tests being successful
     */
    boolean trackSuccess;

    /**
     * boolean for CheckPointGroups class tests being successful
     */
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
            System.out.println("All tests for CheckPointGroups class passed");
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

    // =========================================================
    // HELPER: writes a temp track file and returns its path.
    //
    // Track layout (6 rows x 8 cols):
    //
    //   WWWWWWWW
    //   WDDDDDDW
    //   WDCCCCGW     <- row 2, cols 2-5 are checkpoints (group 1)
    //   WDBBBBGW
    //   WSSSSFDW     <- col 6 is Finish
    //   WWWWWWWW
    //
    // numCheckpoints = 1  (one horizontal checkpoint band)
    // =========================================================
    private File createTempTrackFile(String layout) throws IOException {
        File tmp = File.createTempFile("testTrack", ".txt");
        tmp.deleteOnExit();
        try (FileWriter fw = new FileWriter(tmp)) {
            fw.write(layout);
        }
        return tmp;
    }

    // =========================================================
    // TILE TESTS  (unchanged from original)
    // =========================================================

    /**
     * This is the method that will test all edge cases and return values for the Tile class
     *
     * @return tileSuccess, true if all tests passed, false otherwise
     */
    private boolean testTile() {
        tileSuccess = true;

        char[] tileChars = {'D', 'G', 'S', 'F', 'C', 'B', 'W'};
        Tile[] tiles = new Tile[7];
        int index = 0;
        Tile.Surface testSurface;

        for (char c : tileChars) {
            tiles[index] = new Tile(0, 0, c);
            index++;
        }

        for (Tile tile : tiles) {
            if (tile.getTileType() == 'D') {
                testSurface = tile.getSurface();
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
                testSurface = tile.getSurface();
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
                testSurface = tile.getSurface();
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
                testSurface = tile.getSurface();
                if (testSurface.grip != 0.97) {
                    System.err.println("Finish surface did not return expected 0.97 grip double but instead: " + testSurface.grip);
                    tileSuccess = false;
                } else if (testSurface.accelMultiplier != .8) {
                    System.err.println("Finish surface did not return expected 0.8 accelMultiplier double but instead: " + testSurface.accelMultiplier);
                    tileSuccess = false;
                } else if (testSurface.maxSpeed != 2.0) {
                    System.err.println("Finish surface did not return expected 2.0 maxSpeed double but instead: " + testSurface.maxSpeed);
                    tileSuccess = false;
                } else if (testSurface.checkpoint != true) {
                    System.err.println("Finish surface did not return expected true checkpoint boolean but instead: " + testSurface.checkpoint);
                    tileSuccess = false;
                }
            } else if (tile.getTileType() == 'C') {
                testSurface = tile.getSurface();
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
                testSurface = tile.getSurface();
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
                testSurface = tile.getSurface();
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

            if (tile.getXPos() != 0 || tile.getYPos() != 0) {
                tileSuccess = false;
                System.err.println("Tile should have returned Xpos = 0, Ypos = 0. Tile returned x, y = "
                        + tile.getXPos() + ", " + tile.getYPos());
            }
        }

        return tileSuccess;
    }

    // =========================================================
    // TRACK TESTS
    // =========================================================

    /**
     * Tests Track construction, dimension getters, tile access, changeTile,
     * getCurrentTile (pixel-space lookup), and numCheckpoints.
     * <p>
     * Track layout used (6 rows x 8 cols, 1 checkpoint group):
     * WWWWWWWW
     * WDDDDDDW
     * WDCCCCGW
     * WDBBBBGW
     * WSSSSFDW
     * WWWWWWWW
     *
     * @return trackSuccess, true if all tests passed, false otherwise
     */
    private boolean testTrack() {
        trackSuccess = true;

        // --- Build temp file ---
        String layout =
                "6 8\n" +
                        "WWWWWWWW\n" +
                        "WDDDDDDW\n" +
                        "WDCCCCGW\n" +
                        "WDBBBBGW\n" +
                        "WSSSSFDW\n" +
                        "WWWWWWWW\n";

        Track track;
        try {
            File tmp = createTempTrackFile(layout);
            track = new Track(tmp.getAbsolutePath(), 1);
        } catch (IOException e) {
            System.err.println("Track test: failed to create temp file: " + e.getMessage());
            return false;
        }

        // --- Test 1: Dimensions ---
        if (track.getRows() != 6) {
            System.err.println("Track getRows() expected 6 but got: " + track.getRows());
            trackSuccess = false;
        }
        if (track.getCols() != 8) {
            System.err.println("Track getCols() expected 8 but got: " + track.getCols());
            trackSuccess = false;
        }

        // --- Test 2: numCheckpoints ---
        if (track.getNumCheckpoints() != 1) {
            System.err.println("Track getNumCheckpoints() expected 1 but got: " + track.getNumCheckpoints());
            trackSuccess = false;
        }

        // --- Test 3: Corner tiles are walls ---
        Tile topLeft = track.getSpecificTile(0, 0);
        if (topLeft.getTileType() != 'W') {
            System.err.println("Track corner tile (0,0) expected 'W' but got: " + topLeft.getTileType());
            trackSuccess = false;
        }

        // --- Test 4: Known interior tile types ---
        // Row 1, col 1 should be Dirt
        Tile dirtTile = track.getSpecificTile(1, 1);
        if (dirtTile.getTileType() != 'D') {
            System.err.println("Track tile (row=1,col=1) expected 'D' but got: " + dirtTile.getTileType());
            trackSuccess = false;
        }
        // Row 2, col 2 should be Checkpoint
        Tile checkTile = track.getSpecificTile(2, 2);
        if (checkTile.getTileType() != 'C') {
            System.err.println("Track tile (row=2,col=2) expected 'C' but got: " + checkTile.getTileType());
            trackSuccess = false;
        }
        // Row 4, col 5 should be Finish
        Tile finishTile = track.getSpecificTile(4, 5);
        if (finishTile.getTileType() != 'F') {
            System.err.println("Track tile (row=4,col=5) expected 'F' but got: " + finishTile.getTileType());
            trackSuccess = false;
        }

        // --- Test 5: getCurrentTile (pixel-space) ---
        // Tile at track[1][1] starts at pixel (TILE_SIZE, TILE_SIZE).
        // Any pixel inside that tile's bounding box should resolve back to row=1, col=1 (Dirt).
        int tileSize = Tile.getTileSize();
        Tile pixelTile = track.getCurrentTile(tileSize, tileSize); // xPos=8, yPos=8 -> col=1, row=1
        if (pixelTile == null) {
            System.err.println("Track getCurrentTile(8,8) returned null unexpectedly");
            trackSuccess = false;
        } else if (pixelTile.getTileType() != 'D') {
            System.err.println("Track getCurrentTile pixel lookup expected 'D' but got: " + pixelTile.getTileType());
            trackSuccess = false;
        }

        // --- Test 6: getCurrentTile out-of-bounds returns null ---
        Tile oobTile = track.getCurrentTile(-1, -1);
        if (oobTile != null) {
            System.err.println("Track getCurrentTile(-1,-1) expected null for out-of-bounds but got a tile");
            trackSuccess = false;
        }

        // --- Test 7: changeTile ---
        // Change row=1, col=1 from Dirt to Sand, verify, then restore.
        track.changeTile(1, 1, 'S');
        Tile changedTile = track.getSpecificTile(1, 1);
        if (changedTile.getTileType() != 'S') {
            System.err.println("Track changeTile to 'S' expected 'S' but got: " + changedTile.getTileType());
            trackSuccess = false;
        }
        // Restore to Dirt
        track.changeTile(1, 1, 'D');
        Tile restoredTile = track.getSpecificTile(1, 1);
        if (restoredTile.getTileType() != 'D') {
            System.err.println("Track changeTile restore to 'D' expected 'D' but got: " + restoredTile.getTileType());
            trackSuccess = false;
        }

        // --- Test 8: changeTile default (unknown char falls back to Grass) ---
        track.changeTile(1, 2, 'X'); // 'X' is not a valid case -> default Grass
        Tile defaultTile = track.getSpecificTile(1, 2);
        if (defaultTile.getTileType() != 'G') {
            System.err.println("Track changeTile with unknown char expected default 'G' but got: " + defaultTile.getTileType());
            trackSuccess = false;
        }

        // --- Test 9: drawTrackHelper returns non-null color for every tile ---
        boolean drawOk = true;
        for (int r = 0; r < track.getRows(); r++) {
            for (int c = 0; c < track.getCols(); c++) {
                if (track.drawTrackHelper(r, c) == null) {
                    System.err.println("Track drawTrackHelper returned null at row=" + r + " col=" + c);
                    trackSuccess = false;
                    drawOk = false;
                }
            }
        }
        if (drawOk) {
            // No extra print needed; overall pass message covers it
        }

        return trackSuccess;
    }

    // =========================================================
    // CHECKPOINT TESTS  (via Track's public API)
    // =========================================================

    /**
     * Tests CheckPointGroups through Track's public checkpoint API:
     * - Non-checkpoint tiles return group -1
     * - Checkpoint tiles in the same VERTICAL band share a group number
     * - hitCheckpointGroup changes the color of the entire group
     * - resetCheckpoints restores all groups
     * - resetSpecificCheckpoint restores only the targeted group
     * - A second independent checkpoint group receives a different group number
     * <p>
     * NOTE ON LAYOUT DESIGN:
     * CheckPointGroups uses V_H_CHECK=4 as its lookahead distance to detect
     * whether a band is horizontal or vertical. A horizontal band is detected
     * when (col + 4) has a checkpoint; a vertical band when (row + 4) has one.
     * We use VERTICAL bands here (a column of C's spanning 6 rows) because they
     * reliably trigger the vertical path and the zone creator correctly sweeps the
     * full column in one group. A horizontal band shorter than V_H_CHECK would fall
     * through to the diagonal path and produce unpredictable groupings.
     * <p>
     * Track layout (10 rows x 8 cols, 2 checkpoint groups):
     * WWWWWWWW
     * WDCDDDGW   <- group 1 col=2, starts row=1
     * WDCDDDGW
     * WDCDDDGW
     * WDCDDDGW
     * WDCDDDGW   <- group 1 col=2, ends   row=5 (6 tiles tall > V_H_CHECK=4)
     * WDDDCDDW   <- group 2 col=4, starts row=6
     * WDDDCDDW
     * WDDDCDDW
     * WDDDCDDW
     * WDDDCDDW   <- group 2 col=4, ends   row=10 (but we only go to row 9 for 10 rows)
     * WSSSSFDW
     * WWWWWWWW   <- 13 rows total
     * <p>
     * Simplified to 13 rows x 8 cols for clarity below.
     *
     * @return checkpointSuccess, true if all tests passed, false otherwise
     */
    private boolean testCheckpoint() {
        checkpointSuccess = true;

        // Two vertical checkpoint bands separated by non-checkpoint rows.
        // Group 1: col=2, rows 1-6  (6 tiles tall, fires V_H_CHECK vertical path)
        // Group 2: col=4, rows 7-12 (6 tiles tall, same)
        String layout =
                "13 8\n" +
                        "WWWWWWWW\n" +
                        "WDCDDDGW\n" +
                        "WDCDDDGW\n" +
                        "WDCDDDGW\n" +
                        "WDCDDDGW\n" +
                        "WDCDDDGW\n" +
                        "WDCDDDGW\n" +
                        "WDDDCDDW\n" +
                        "WDDDCDDW\n" +
                        "WDDDCDDW\n" +
                        "WDDDCDDW\n" +
                        "WDDDCDDW\n" +
                        "WWWWWWWW\n";

        Track track;
        try {
            File tmp = createTempTrackFile(layout);
            track = new Track(tmp.getAbsolutePath(), 2);
        } catch (IOException e) {
            System.err.println("Checkpoint test: failed to create temp file: " + e.getMessage());
            return false;
        }

        // --- Test 1: Non-checkpoint tiles return group -1 ---
        // getCheckpointGroupNum(xPos=col, yPos=row)
        int wallGroup = track.getCheckpointGroupNum(0, 0);
        if (wallGroup != -1) {
            System.err.println("CheckPointGroups: non-checkpoint Wall tile expected group -1 but got: " + wallGroup);
            checkpointSuccess = false;
        }
        int dirtGroup = track.getCheckpointGroupNum(1, 1); // col=1, row=1 -> Dirt
        if (dirtGroup != -1) {
            System.err.println("CheckPointGroups: non-checkpoint Dirt tile expected group -1 but got: " + dirtGroup);
            checkpointSuccess = false;
        }

        // --- Test 2: All tiles in vertical band 1 (col=2, rows 1-6) share one group number ---
        int group1_row1 = track.getCheckpointGroupNum(2, 1); // col=2, row=1
        int group1_row3 = track.getCheckpointGroupNum(2, 3); // col=2, row=3
        int group1_row6 = track.getCheckpointGroupNum(2, 6); // col=2, row=6

        if (group1_row1 == -1) {
            System.err.println("CheckPointGroups: group1 tile at (col=2, row=1) was not mapped (returned -1)");
            checkpointSuccess = false;
        }
        if (group1_row1 != group1_row3) {
            System.err.println("CheckPointGroups: group1 tiles at row=1 and row=3 have different group numbers: "
                    + group1_row1 + " vs " + group1_row3);
            checkpointSuccess = false;
        }
        if (group1_row1 != group1_row6) {
            System.err.println("CheckPointGroups: group1 tiles at row=1 and row=6 have different group numbers: "
                    + group1_row1 + " vs " + group1_row6);
            checkpointSuccess = false;
        }

        // --- Test 3: All tiles in vertical band 2 (col=4, rows 7-11) share a DIFFERENT group number ---
        int group2_row7 = track.getCheckpointGroupNum(4, 7);  // col=4, row=7
        int group2_row9 = track.getCheckpointGroupNum(4, 9);  // col=4, row=9
        int group2_row11 = track.getCheckpointGroupNum(4, 11); // col=4, row=11

        if (group2_row7 == -1) {
            System.err.println("CheckPointGroups: group2 tile at (col=4, row=7) was not mapped (returned -1)");
            checkpointSuccess = false;
        }
        if (group2_row7 != group2_row9) {
            System.err.println("CheckPointGroups: group2 tiles at row=7 and row=9 have different group numbers: "
                    + group2_row7 + " vs " + group2_row9);
            checkpointSuccess = false;
        }
        if (group2_row7 != group2_row11) {
            System.err.println("CheckPointGroups: group2 tiles at row=7 and row=11 have different group numbers: "
                    + group2_row7 + " vs " + group2_row11);
            checkpointSuccess = false;
        }
        if (group1_row1 == group2_row7) {
            System.err.println("CheckPointGroups: group1 and group2 were incorrectly assigned the same number: " + group1_row1);
            checkpointSuccess = false;
        }

        // --- Test 4: hitCheckpointGroup turns the entire group RED ---
        java.awt.Color red = java.awt.Color.RED;

        // getSpecificTile(row, col)
        Tile cp1_top = track.getSpecificTile(1, 2); // group1, row=1, col=2
        Tile cp1_bottom = track.getSpecificTile(6, 2); // group1, row=6, col=2

        if (cp1_top.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group1 tile should NOT be RED before being hit");
            checkpointSuccess = false;
        }

        // Hit via xPos=col=2, yPos=row=1
        track.hitCheckpointGroup(2, 1);

        if (!cp1_top.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: after hit, group1 tile at (row=1,col=2) should be RED");
            checkpointSuccess = false;
        }
        if (!cp1_bottom.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: after hit, group1 sibling tile at (row=6,col=2) should also be RED");
            checkpointSuccess = false;
        }

        // --- Test 5: Group 2 tiles are NOT affected by hitting group 1 ---
        Tile cp2_tile = track.getSpecificTile(7, 4); // group2, row=7, col=4
        if (cp2_tile.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group2 tile should NOT be RED after only group1 was hit");
            checkpointSuccess = false;
        }

        // --- Test 6: resetCheckpoints resets ALL groups ---
        track.resetCheckpoints();
        if (cp1_top.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: after resetCheckpoints, group1 tile at (row=1,col=2) should no longer be RED");
            checkpointSuccess = false;
        }

        // --- Test 7: resetSpecificCheckpoint resets only the targeted group ---
        // Hit both groups
        track.hitCheckpointGroup(2, 1);  // group 1
        track.hitCheckpointGroup(4, 7);  // group 2

        if (!cp1_top.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group1 tile should be RED after hitCheckpointGroup");
            checkpointSuccess = false;
        }
        if (!cp2_tile.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group2 tile should be RED after hitCheckpointGroup");
            checkpointSuccess = false;
        }

        // Reset only group 1
        track.resetSpecificCheckpoint(group1_row1);

        if (cp1_top.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group1 tile should NOT be RED after resetSpecificCheckpoint(group1)");
            checkpointSuccess = false;
        }
        if (!cp2_tile.getTileColor().equals(red)) {
            System.err.println("CheckPointGroups: group2 tile should still be RED after resetSpecificCheckpoint(group1 only)");
            checkpointSuccess = false;
        }

        // Clean up
        track.resetCheckpoints();

        return checkpointSuccess;
    }
}