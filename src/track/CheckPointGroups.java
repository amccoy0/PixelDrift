package track;

import java.util.HashMap;
import java.util.Map;

/**
 * CheckPointGroups.java
 * Author: August McCoy
 * Code Description: Groups connected checkpoint tiles by scanning for zones and mapping every tile in each zone to a
 * checkpoint group number.
 */
public class CheckPointGroups {

    /** Number of tiles to look ahead when checking for a vertical or horizontal checkpoint*/
    private static final int V_H_CHECK = 4;

    /** Number of rows in the track */
    private final int rows;

    /** Number of columns in the track */
    private final int cols;

    /** Total number of distinct checkpoint groups expected on this track*/
    private final int numCheckpoints;

    /** Tracks which group number we are currently assigning */
    private int checkpointNum;

    /** Maps each checkpoint Tile to its connected group number */
    private final Map<Tile, Integer> checkpoints = new HashMap<>();

    /** The track we are creating checkpoint groups for, used for tile lookups */
    private final Track track;

    /**
     * Constructor
     *
     * @param rows the number of rows in the track
     * @param cols the number of columns in the track
     * @param numCheckpoints the expected number of checkpoint groups
     * @param track the Track to scan
     */
    public CheckPointGroups(int rows, int cols, int numCheckpoints, Track track) {
        this.rows = rows;
        this.cols = cols;
        this.numCheckpoints = numCheckpoints;
        this.track = track;
        this.checkpointNum = 1;

        createGroups();
    }

    /**
     * Scans the track, the first checkpoint tile of each group is used to create a zone, and every checkpoint
     * tile in that zone gets assigned the current group number.
     */
    public void createGroups() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Tile tile = track.getSpecificTile(r, c);

                // Skip non-checkpoints and if it is already assigned to a group
                if (!tile.isCheckpoint() || checkpoints.containsKey(tile)) {
                    continue;
                }

                // First tile of a new checkpoint group so get zone
                int [] zone = checkpointZoneCreator(r, c);
                int leftCol = zone[0];
                int rightCol = zone[1];
                int bottomRow = zone[2];

                // Go through zone and assign group number to each checkpoint
                for (int zoneR = r; zoneR <= bottomRow; zoneR++) {
                    for (int zoneC = leftCol; zoneC <= rightCol; zoneC++) {
                        Tile zoneTile = track.getSpecificTile(zoneR, zoneC);
                        // Check if it is checkpoint and is checkpoints already contains the Tile
                        if (zoneTile.isCheckpoint() && !checkpoints.containsKey(zoneTile)) {
                            checkpoints.put(zoneTile,checkpointNum);
                        }
                    }
                }
                checkpointNum++;
            }
        }
    }

    /**
     * This function creates a zone of where we will find all of the connected checkpoints to the first one we hit.
     *
     * @param row the row of where the first checkpoint in this checkpoint group was found
     * @param col the column of where the first checkpoint in this checkpoint group was found
     * @return int[] zone, zone[left most column, right most column, bottom row] not top row because we know that already
     */
    private int[] checkpointZoneCreator(int row, int col) {
        int[] zone = new int[3];

        // Horizontal Check
        if (col + V_H_CHECK < cols && track.getSpecificTile(row, col + V_H_CHECK).isCheckpoint()) {
            // Left col is current col
            zone[0] = col;

            // Right col search
            int horizCol = col;
            while (horizCol + 1 < cols && track.getSpecificTile(row, horizCol + 1).isCheckpoint()) {
                horizCol++;
            }
            zone[1] = horizCol;

            // Bottom row search
            int horizRow = row;
            while (horizRow + 1 < rows && track.getSpecificTile(horizRow + 1, col).isCheckpoint()) {
                horizRow++;
            }
            zone[2] = horizRow;

            return zone;
        }

        // Vertical Check
        if (row + V_H_CHECK < rows && track.getSpecificTile(row + V_H_CHECK, col).isCheckpoint()) {
            zone[0] = col;

            // Right col search
            int vertCol = col;
            while (vertCol + 1 < cols && track.getSpecificTile(row, vertCol + 1).isCheckpoint()) {
                vertCol++;
            }
            zone[1] = vertCol;

            // Bottom row search
            int vertRow = row;
            while (vertRow + 1 < rows && track.getSpecificTile(vertRow + 1, col).isCheckpoint()) {
                vertRow++;
            }
            zone[2] = vertRow;

            return zone;
        }

        // Diagonal check
        // Left col search, walk left down
        int leftRow = row;
        int leftCol = col;
        while (leftCol - 1 >= 0 && leftRow + 1 < rows &&
                track.getSpecificTile(leftRow + 1, leftCol - 1).isCheckpoint()) {
            leftCol--;
            leftRow++;
        }
        // Extended the zone by one to the left to make sure all of the checkpoint tiles are in the zone
        zone[0] = leftCol - 1;

        // Right col search, walk right down
        int rightRow = row;
        int rightCol = col;
        while (rightCol + 1 < cols && rightRow + 1 < rows &&
                track.getSpecificTile(rightRow + 1,rightCol + 1).isCheckpoint()) {
            rightCol++;
            rightRow++;

        }
        // Added one here too because it was missing the farthest right one.
        zone[1] = rightCol + 1;

        // Bottom row is whichever went further down, I added one to this because it is missing one on diagonals
        zone[2] = Math.max(leftRow, rightRow) + 1;

        return zone;
    }


    /**
     * Changes the color of all Tiles in the same checkpoint group as xPos yPos by setting hitCheckpoint to true
     *
     * @param xPos column of hit checkpoint
     * @param yPos row of hit checkpoint
     */
    public void changeGroupColor(int xPos, int yPos) {
        int group = getGroupNumber(xPos, yPos);

        // Check if the tile is a mapped checkpoint
        if (group == -1) {
            return;
        }

        // Go through all entrys in map and see if they are in the group and set hitCheckpoint to true which changes color
        for (Map.Entry<Tile, Integer> entry: checkpoints.entrySet()) {
            if (entry.getValue() == group) {
                entry.getKey().setHitCheckpoint(true);
            }
        }
    }

    /**
     * Returns the group number of the checkpoint tile at xPos yPos, or -1 if the Tile is not a mapped checkpoint
     *
     * @param xPos column of the tile
     * @param yPos row of the tile
     * @return group number of the tile, or -1 if not mapped
     */
    public int getGroupNumber(int xPos, int yPos) {
        Tile tile = track.getSpecificTile(yPos, xPos);
        return checkpoints.getOrDefault(tile, -1);
    }

    /**
     * Resets all checkpoint tiles in all groups back to unhit. Called when Finish line is hit.
     */
    public void resetAllGroups() {
        for (Tile tile: checkpoints.keySet()) {
            tile.setHitCheckpoint(false);
        }
    }

    /**
     * Resets the checkpoint tiles in a specific group
     *
     * @param groupNum the corresponding group to be reset
     */
    public void resetSpecificGroup(int groupNum) {
        for (Tile tile: checkpoints.keySet()) {
            if (checkpoints.get(tile) == groupNum) {
                tile.setHitCheckpoint(false);
            }
        }
    }

}
