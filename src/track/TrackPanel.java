package track;

import car.Car;

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
    // Array of cars for later game use
    private final int numCars;
    private Car[] cars;

    public TrackPanel(Track track, int numCars) {
        this.track = track;
        this.numCars = numCars;
        this.cars = new Car[numCars];
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

        // Draw car on top of track
        if (cars.length != 0) {
            for (Car c: cars) {
                c.draw(g);
            }

        }
    }

    // This is so the track will show fully without having to use magic numbers due to window sizing
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                track.getCols() * Tile.getTileSize(),
                track.getRows() * Tile.getTileSize()
        );
    }

    /**
     * This is used to assign a car to the track panel so
     * @param car
     */
    public void setCar(Car car) {
        for (int i = 0; i < numCars; i++) {
            if (cars[i] == null) {
                this.cars[i] = car;
            }
        }
    }

}
