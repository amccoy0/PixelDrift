package track;

import java.awt.*;
import java.io.*;

/**
 * Track.java
 * Code Description: This class represents a Track in Java. It is a double array of Tile objects that represent the track. Has functions to help
 * game logic and the drawing of the track.
 */
public class Track {
    /**
     * Double tile array to store the track tiles for drawing/gamelogic
     */
    private Tile[][] track;

    /**
     * The number of rows in the track
     */
    private int rows;

    /**
     * The number of columns in the track
     */
    private int cols;

    /**
     * The number of checkpoints on the track, not individual tiles but the number of checkpoint lines on the track
     */
    private final int numCheckpoints;

    /**
     * The grouped checkpoints used to recolor a group when a single one is hit
     */
    private final CheckPointGroups checkPointGroups;

    /**
     * Constructor for Track class
     *
     * @param filename takes file path to the file
     */
    public Track(String filename, int numCheckpoints) {
        // I will just pass number of checkpoints, will be used in game logic
        this.numCheckpoints = numCheckpoints;
        int lineCount = 0;

        try (final FileReader fileReader = new FileReader(new File(filename));
             final BufferedReader buffReader = new BufferedReader(fileReader)) {
            // Read first line to create track
            try {
                // Split by spaces
                String[] firstLine = buffReader.readLine().split("\\s+");
                // Rows and cols
                this.rows = Integer.parseInt(firstLine[0]);
                this.cols = Integer.parseInt(firstLine[1]);
                // Create track
                this.track = new Tile[rows][cols];
            } catch (NumberFormatException e) {
                System.err.println("Illegal first line of file cannot pass data to create track");
            }

            // Now we read through the rest of the file, thankfully tile self creates so we just need to pass row + col
            String line;
            while ((line = buffReader.readLine()) != null && lineCount < rows) {
                try {
                    // I need to decide whether spaces split these or commas
                    char[] charArray = line.toCharArray();

                    if (charArray.length != cols) {
                        System.err.println("Warning: line " + (lineCount + 1) + " length mismatch. Expected " + cols + ", got " + charArray.length);
                        System.exit(1);
                    }

                    for (int i = 0; i < charArray.length; i++) {
                        track[lineCount][i] = new Tile(i, lineCount, charArray[i]);
                    }
                    // Increment lineCount at end
                    lineCount++;
                    // UPDATE THIS
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            // Catch error in opening file
            System.out.println("Cannot open '" + filename + "' file");
            System.exit(1);
        } catch (IOException e) {
            // Catch error in reading lines in file
            System.err.println("Data file error at line " + lineCount);
            System.err.println("Ignoring rest of file");
        }

        this.checkPointGroups = new CheckPointGroups(rows, cols, numCheckpoints, this);
    }


    /**
     * This function helps draw the track
     *
     * @param row the x position of the tile
     * @param col the y position of the tile
     * @return Color of the tile
     */
    public Color drawTrackHelper(int row, int col) {
        Tile tile = track[row][col];
        return tile.getTileColor();
    }

    /**
     * This function may not be used but might later, returns a specific tile
     *
     * @param xPos x position of the tile
     * @param yPos y position of the tile
     * @return a Tile in the track
     */
    public Tile getCurrentTile(int xPos, int yPos) {
        if (xPos < 0 || yPos < 0 || xPos > cols * Tile.getTileSize() || yPos > rows * Tile.getTileSize()) {
            System.err.println("X or Y Position out of bounds: " + xPos + ", " + yPos);
            return null;
        }
        return track[yPos / Tile.getTileSize()][xPos / Tile.getTileSize()];
    }

    /**
     * This fucntion is used in TrackDrawerGUI to write the tile's char to the file
     *
     * @param row the row of the tile
     * @param col the column of the tile
     * @return the Tile at the specific row + col
     */
    public Tile getSpecificTile(int row, int col) {
        return track[row][col];
    }

    /**
     * Getter for track rows
     *
     * @return rows the number of rows in the track
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter for track columns
     *
     * @return cols the number of columns in the track
     */
    public int getCols() {
        return cols;
    }

    /**
     * Getter for the number of track checkpoints
     *
     * @return numCheckpoints the number of checkpoints on the track
     */
    public int getNumCheckpoints() {
        return numCheckpoints;
    }

    /**
     * This function is only used by TrackDrawerGUI to edit track tiles to allow us to customize tracks and make them
     * more enjoyable for the user.
     *
     * @param row the row of the Tile in the track data structure
     * @param col the column of the Tile in the track data structure
     * @param c   the character we are changing the Tile to, Make a new tile and inserting it where we want
     */
    public void changeTile(int row, int col, char c) {
        switch (c) {
            case ('D'):
                track[row][col] = new Tile(row, col, 'D');
                break;
            case ('F'):
                track[row][col] = new Tile(row, col, 'F');
                break;
            case ('C'):
                track[row][col] = new Tile(row, col, 'C');
                break;
            case ('S'):
                track[row][col] = new Tile(row, col, 'S');
                break;
            case ('W'):
                track[row][col] = new Tile(row, col, 'W');
                break;
            case ('B'):
                track[row][col] = new Tile(row, col, 'B');
                break;
            default:
                track[row][col] = new Tile(row, col, 'G');

        }
    }

    /**
     * References CheckPointGroups method of changing all of the connected checkPoint tiles when a checkPoint is hit
     *
     * @param xPos column of hit checkpoint
     * @param yPos row of hit checkpoint
     */
    public void hitCheckpointGroup(int xPos, int yPos) {
        checkPointGroups.changeGroupColor(xPos, yPos);
    }

    /**
     * References CheckPointGroups method of resetting all of the checkpoints colors to the non-hit color when the car
     * crosses the finish.
     */
    public void resetCheckpoints() {
        checkPointGroups.resetAllGroups();
    }

    /**
     * References CheckPointGroups method of resetting a specific checkpointGroup colors to the non-hit color when the car
     * crosses a checkpoint.
     *
     * @param groupNum the groupNum of the checkpoint hit
     */
    public void resetSpecificCheckpoint(int groupNum) {
        checkPointGroups.resetSpecificGroup(groupNum);
    }

    /**
     * References CheckPointGroups method of returning a checkpoints group number
     *
     * @param xPos the xPos of the tile
     * @param yPos the yPos of the tile
     * @return the checkpointGroup number of the specific track tile in the track
     */
    public int getCheckpointGroupNum(int xPos, int yPos) {
        return checkPointGroups.getGroupNumber(xPos, yPos);
    }

}
