package track;

import java.awt.*;
import java.io.*;

/**
 * Track.java
 * Author: August McCoy
 * Date: 3/2/2026
 * Code Description: This class represents a Track in Java. It is a double array of Tile objects that represent the track. Has functions to help
 * game logic and the drawing of the track.
 */
public class Track {
    private Tile[][] track;
    private int rows;
    private int cols;

    /**
     * Constructor for Track class
     *
     * @param filename takes file path to the file
     */
    public Track (String filename) {
        loadTrack(filename);
    }

    /**
     * This function reads a file and generates the track based on the contents of the tile.
     *
     * @param filename the filepath of the file
     */
    private void loadTrack(String filename) {
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
            } catch (NumberFormatException e){
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
                    throw new RuntimeException(e);
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
     * @param row x position of the tile
     * @param col y position of the tile
     * @return a Tile in the track
     */
    public Tile getCurrentTile(int row, int col) {
        return track[row][col];
    }

    /**
     * This function checks to see if a Tile in the track is a slow Time, Grass
     *
     * @param row the x position of the tile
     * @param col the y position of the tile
     * @return true if the tile is a slow tile, false otherwise
     */
    public boolean isSlowTime(int row, int col) {
        return track[row][col].isSlowTile();
    }

    // Getters, might remove later, don't know if they will be used yet
    public Tile[][] getTrack() {
        return track;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}
