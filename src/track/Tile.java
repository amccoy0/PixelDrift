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
    private static final Random rand = new Random();
    private static final int TILE_SIZE = 8;

    private final int xPos;
    private final int yPos;
    private final Surface surface;

    private Color tileColor;

    /**
     * Enumeration to store surface data
     */
    public enum Surface {
        DIRT('D', 0.97, 0.8, 6.0, false, new Color(139, 69, 19)),
        GRASS('G', 0.93, 0.75, 3.5, false, new Color(34, 139, 34)),
        FINISH('F',0.97, 0.8, 6.0, true, Color.WHITE),
        CHECKPOINT('C', 0.97, 0.8, 6.0, true, Color.BLUE);

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
     * @param xPos the xPos of the tile in the array
     * @param yPos the yPos of the tile in the array
     * @param tileType the char of the tileyupe
     */
    public Tile(int xPos, int yPos, char tileType) {
        this.xPos = xPos;
        this.yPos = yPos;

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
        return tileColor;
    }

    public int getXPos() {
        return xPos;
    }

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

    public boolean isCheckpoint() {
        return surface.checkpoint;
    }

    /**
     * This method is static so I can access it without initializing a tile for GUI purposes
     *
     * @return TILE_SIZE the size of the tile # x #
     */
    static int getTileSize() {
        return TILE_SIZE;
    }
}
