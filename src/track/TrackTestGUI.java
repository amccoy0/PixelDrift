package track;

import javax.swing.*;
import java.awt.*;

/**
 * TrackTextGUI.java
 * Author: August McCoy
 * Code Description: This purpose of this class was to test loading a Track visually and So I can play around with the
 * track design.
 */
public class TrackTestGUI {

    public static void main(String[] args) {
        Track track = new Track("src/data/track120x100.txt");
        JFrame frame = new JFrame("Track draw test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(track.getCols() * Tile.getTileSize(), track.getRows() * Tile.getTileSize());


        TrackPanel trackPanel = new TrackPanel(track);

        frame.add(trackPanel);

        frame.setVisible(true);
    }
}
