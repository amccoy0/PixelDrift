package track;

import javax.swing.*;
import java.awt.*;

/**
 * TrackPanel.java
 * Author: August McCoy
 * Date: 3/4/26
 * Code Description: This class represents the visual Track. It extends JPanel to allow the use of the paint component
 * method which allows us to draw the track. Each Tile in the track is represented by a 5x5 pixel square.
 */
public class TrackPanel extends JPanel {
    private Track track;
    public TrackPanel(Track track) {
        this.track = track;
    }

    /**
     * This method will draw every Tile in the track
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < track.getRows(); r++) {
            for (int c = 0; c < track.getCols(); c++) {

                Color tileColor = track.drawTrackHelper(r, c);

                g.setColor(tileColor);

                g.fillRect(c * Tile.getTileSize(), r * Tile.getTileSize(), Tile.getTileSize(), Tile.getTileSize());
            }
        }
    }
}
