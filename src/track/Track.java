package track;

import java.io.*;

public class Track {
    private Tile[][] track;
    private int rows;
    private int cols;

    public Track (String filename) {
        loadTrack(filename);
    }

    private void loadTrack(String filename) {
        int lineCount = 0;
        try (final FileReader fileReader = new FileReader(new File(filename));
             final BufferedReader buffReader = new BufferedReader(fileReader)) {
            // Read first line to create track
            try {
                String[] firstLine = buffReader.readLine().split(",");
                // Rows and cols
                this.rows = Integer.parseInt(firstLine[0]);
                this.cols = Integer.parseInt(firstLine[1]);
                // Create track
                this.track = new Tile[rows][cols];
            } catch (NumberFormatException e){
                System.err.println("Illegal first line of file cannot pass data to create track");
            }

            // Now we read through the rest of the file, thankfully tile self creates so we just need to pass row + col
            while (true) {
                // Create placeholder
                int xPos, YPos;
                char tileType;
                try {
                    // I need to decide whether spaces split these or commas
                    char[] charArray = buffReader.readLine().toCharArray();
                    for (int i = 0; i < charArray.length; i++) {
                        // Need to implement a check here
                        track[i][lineCount] = new Tile(i, lineCount, charArray[i]);
                    }
                    // Increment lineCount at end
                    lineCount++;
                } catch (NullPointerException e) {
                    System.err.println("Can't convert null, something wrong with data in file: " + e.getMessage());
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
