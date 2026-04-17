package track;

import java.awt.Color;
import java.util.Random;

/**
 * Tile.java
 * Author: August McCoy
 * Code Description: This class represents a single Tile/Pixel of the track. It generates a variance of a base color for its specific tile type when
 * generated.
 */
public class Tile {
    /** Random used for applying random variation to the tile base color */
    private static final Random rand = new Random();

    /** The size of the tile, # x # */
    private static final int TILE_SIZE = 8;

    /** The xPos of the tile in the double array */
    private final int xPos;

    /** The yPos of the tile in the double array */
    private final int yPos;

    /** I had to add this back in so the TrackDrawerGUI can write it to a file */
    private final char tileType;

    /** Surface of the tile, used to store information */
    private final Surface surface;

    /** Color of the tile */
    private Color tileColor;

    /** This is the Color of the checkPoint when it has been hit by a car */
    private Color checkPointHitColor;

    /** This is a boolean for if the checkpoint is hit or not, changes color based on boolean value */
    private boolean hitCheckpoint;

    /**
     * Enumeration to store surface data
     */
    public enum Surface {
        DIRT('D', 0.97, 0.8, 6.0, false, new Color(139, 69, 19)),
        GRASS('G', 0.93, 0.75, 3.5, false, new Color(34, 139, 34)),
        SAND('S', 0.95, 0.78, 5.0, false, new Color(180, 160, 100)),
        FINISH('F',0.97, 0.8, 6.0, false, Color.WHITE),
        CHECKPOINT('C', 0.97, 0.8, 6.0, true, Color.BLUE),
        BARRIER('B', 0.93, 0.75, 3.5, false, new Color(34, 139, 34)),
        WALL('W', 0.93, 0.75, 3.5, false, Color.BLACK);



        public final char tileType;
        public final double grip;
        public final double accelMultiplier;
        public final double maxSpeed;
        public final boolean checkpoint;
        public final Color baseColor;

        Surface(char tileType, double grip, double accelMultiplier, double maxSpeed, boolean checkpoint, Color baseColor) {
            this.tileType = tileType;
            this.grip = grip;
            this.accelMultiplier = accelMultiplier;
            this.maxSpeed = maxSpeed;
            this.checkpoint = checkpoint;
            this.baseColor = baseColor;
        }
    }


    /**
     * Constructor, Track will read a double array and pass XPos, YPos, and tileType
     *
     * @param xPos the xPos of the tile in the array
     * @param yPos the yPos of the tile in the array
     * @param tileType the char of the tileyupe
     */
    public Tile(int xPos, int yPos, char tileType) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.tileType = tileType;
        this.hitCheckpoint = false;

        // Switch statement for tile type to assign surface enumeration
        switch (tileType) {
            case 'D':
                surface = Surface.DIRT;
                break;
            case 'F':
                surface = Surface.FINISH;
                break;
            case 'C':
                surface = Surface.CHECKPOINT;
                checkPointHitColor = Color.RED;
                break;
            case 'S':
                surface = Surface.SAND;
                break;
            case 'W':
                surface = Surface.WALL;
                break;
            case 'B':
                surface = Surface.BARRIER;
                break;
            default:
                surface = Surface.GRASS;
        }

        // Get baseColor from enum
        Color baseColor = surface.baseColor;

        // I did this so I could have random variation in the tiles so they weren't one flat color
        // Convert Base Color to HSB
        float[] hsb = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);

        float hue = hsb[0]; // Keep same hue
        float saturation = hsb[1];
        float brightness = hsb[2];

        // Use Random to vary saturation and brightness
        saturation += (rand.nextFloat() - 0.5f) * 0.2f; // +- 0.1
        brightness += (rand.nextFloat() - 0.5f) * 0.2f; // +- 0.1

        // Clamp the values between 0 and 1
        saturation = Math.max(0, Math.min(1, saturation));
        brightness = Math.max(0, Math.min(1, brightness));

        this.tileColor = Color.getHSBColor(hue, saturation, brightness);
    }

    /**
     * Getter for tile color
     *
     * @return the Color of the tile
     */
    public Color getTileColor() {
        if (surface == Surface.CHECKPOINT && hitCheckpoint) {
            return checkPointHitColor;
        }
        return tileColor;
    }

    /**
     * Setter for if the checkpoint is hit or not yet, used to change color based on this boolean value
     *
     * @param b boolean true if hit, false otherwise
     */
    public void setHitCheckpoint(boolean b) {
        hitCheckpoint = b;
    }

    /**
     * Get the X position of the Tile in the track
     *
     * @return
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Get the Y position of the Tile in the track
     *
     * @return int yPos, Y position of the Tile in the track
     */
    public int getYPos() {
        return yPos;
    }


    /**
     * Getter for Surface enumeration of tile
     *
     * @return the Surface of the tile
     */
    public Surface getSurface() {
        return surface;
    }

    /**
     * Getter for the Tile's tileType
     * @return char tileType, char representing the tile in file
     */
    public char getTileType() {
        return tileType;
    }

    /**
     * Getter for is this Tile a checkpoint, used in checkpointGroups
     *
     * @return boolean true if checkpoint, false otherwise
     */
    public boolean isCheckpoint() {
        return surface.checkpoint;
    }

    /**
     * This method is static so I can access it without initializing a tile for GUI purposes
     *
     * @return TILE_SIZE the size of the tile # x #
     */
    public static int getTileSize() {
        return TILE_SIZE;
    }
}
