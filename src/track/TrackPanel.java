package track;

import car.Car;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * TrackPanel.java
 * Code Description: This class represents the visual Track. It extends JPanel to allow the use of the paint component
 * method which allows us to draw the track. Each Tile in the track is represented by a 8x8 pixel square.
 *
 * Double buffering, implemented by claude.ai: all drawing is done onto an off-screen BufferedImage first, then the finished
 * frame is stamped onto the panel in a single drawImage() call. This eliminates the flickering /
 * tearing that happens when the track and cars are painted directly to the screen tile-by-tile.
 * The buffer is only recreated when the panel is first used or its size changes.
 */
public class TrackPanel extends JPanel {
    /**
     * Track that will be assigned to track panel for redrawing
     */
    private Track track;

    /**
     * Number of cars in the array of cars, not constant for future gamemodes + ability to be changed between gamemodes
     */
    private final int numCars;

    /**
     * Array of cars for redrawing
     */
    private Car[] cars;

    /**
     * Off-screen buffer - we draw everything here first, then blit it to the panel
     */
    private BufferedImage buffer;

    /**
     * Graphics context for the off-screen buffer
     */
    private Graphics2D bufferGraphics;

    /**
     * Constructor for TrackPanel
     *
     * @param track   the track that the TrackPanel will store and redraw
     * @param numCars the number of cars on the TrackPanel
     */
    public TrackPanel(Track track, int numCars) {
        this.track = track;
        this.numCars = numCars;
        this.cars = new Car[numCars];

        // Tell Swing we are handling our own double buffering so it does not do
        // its own (lighter-weight) version on top of ours.
        setDoubleBuffered(false);
    }

    /**
     * Creates (or recreates) the off-screen buffer to match the current panel size.
     * Called lazily from paintComponent so we always have valid dimensions.
     */
    private void createBuffer() {
        int w = getWidth();
        int h = getHeight();

        // Nothing to do if the panel hasn't been sized yet
        if (w <= 0 || h <= 0) {
            return;
        }

        // Dispose of the old context to avoid a graphics resource leak
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
        }

        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.createGraphics();
    }

    /**
     * Paints everything onto the off-screen buffer, then draws the completed buffer
     * onto the panel in a single call — no partially-drawn frames ever reach the screen.
     *
     * @param g the Graphics context provided by Swing for the panel surface
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Recreate the buffer if it doesn't exist yet or if the panel was resized
        if (buffer == null || buffer.getWidth() != getWidth() || buffer.getHeight() != getHeight()) {
            createBuffer();
        }

        // Guard: if the panel still has no size (e.g. before it is displayed), bail out
        if (bufferGraphics == null) {
            return;
        }

        // --- Draw everything onto the off-screen buffer ---

        // 1. Draw the track tiles
        for (int r = 0; r < track.getRows(); r++) {
            for (int c = 0; c < track.getCols(); c++) {
                Color tileColor = track.drawTrackHelper(r, c);
                bufferGraphics.setColor(tileColor);
                bufferGraphics.fillRect(
                        c * Tile.getTileSize(),
                        r * Tile.getTileSize(),
                        Tile.getTileSize(),
                        Tile.getTileSize()
                );
            }
        }

        // 2. Draw cars on top of the track
        if (cars.length != 0) {
            for (Car car : cars) {
                if (car != null) {
                    car.draw(bufferGraphics);
                }
            }
        }

        // --- Blit the completed buffer to the screen in one atomic operation ---
        g.drawImage(buffer, 0, 0, null);
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
     * This is used to assign a car to the track panel
     *
     * @param car the car being assigned to the track
     */
    public void setCar(Car car) {
        for (int i = 0; i < numCars; i++) {
            if (cars[i] == null) {
                this.cars[i] = car;
                break;
            }
        }
    }
}