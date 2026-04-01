package track;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TrackGenerator.java
 * Author: ChatGPT
 * Code Description: This class generates files for track generation. It was implemented by ChatGPT to write base track
 * files that will be editted later.
 */
public class TrackGenerator {
    public static void main(String[] args) {
        int cols = 120; // width
        int rows = 100; // height
        int trackThickness = 10; // dirt
        int grassBorder = 5; // grass outside the track

        char[][] track = new char[rows][cols];

        // Fill entire track with grass first
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                track[r][c] = 'G';
            }
        }

        // Define a simple loop rectangle in the middle
        int top = grassBorder;
        int left = grassBorder;
        int bottom = rows - grassBorder - 1;
        int right = cols - grassBorder - 1;

        // Make dirt track thickness
        for (int t = 0; t < trackThickness; t++) {
            for (int c = left + t; c <= right - t; c++) {
                track[top + t][c] = 'D';      // top row
                track[bottom - t][c] = 'D';   // bottom row
            }
            for (int r = top + t; r <= bottom - t; r++) {
                track[r][left + t] = 'D';     // left col
                track[r][right - t] = 'D';    // right col
            }
        }

        // Optionally mark a finish line
        int finishRow = top + trackThickness / 2;
        int finishCol = left + trackThickness;
        track[finishRow][finishCol] = 'F';

        // Write to file
        try (FileWriter writer = new FileWriter("src/data/track120x100.txt")) {
            writer.write(cols + " " + rows + "\n"); // first line: width height
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    writer.write(track[r][c]);
                }
                writer.write("\n");
            }
            System.out.println("Track generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}