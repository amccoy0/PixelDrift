package track;

import car.Car;

import javax.swing.*;
import java.awt.*;

/**
 * TrackPanel.java
 * Author: August McCoy
 * Code Description: This class represents the visual Track. It extends JPanel to allow the use of the paint component
 * method which allows us to draw the track. Each Tile in the track is represented by a 5x5 pixel square.
 */
public class TrackPanel extends JPanel {
    /** Track that will be assigned to track panel for redrawing */
    private Track track;

    /** Number of cars in the array of cars, not constant for future gamemodes + ability to be changed between gamemodes */
    private final int numCars;

    /** Array of cars for redrawing */
    private Car[] cars;

    /**
     * Constructor for TrackPanel
     *
     * @param track the track that the TrackPanel will store and redraw
     * @param numCars the number of cars on the TrackPanel
     */
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

        // Redraw the track
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



    /**
     * This is used so the track will show fully without having to use magic numbers due to window sizing
     *
     * @return Dimension of the window that will show everything
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                track.getCols() * Tile.getTileSize(),
                track.getRows() * Tile.getTileSize()
        );
    }

    /**
     * This is used to assign a car to the track panel so
     * @param car the car being assigned to the track
     */
    public void setCar(Car car) {
        // Set the car to a null spot in array
        for (int i = 0; i < numCars; i++) {
            if (cars[i] == null) {
                this.cars[i] = car;
            }
        }
    }

}
