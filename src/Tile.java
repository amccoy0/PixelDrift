import java.awt.Color;
import java.util.Random;

public class Tile {
    private final int XPos;
    private final int YPos;
    private final char tileType;
    private boolean slowTile;
    private boolean checkpoint;
    private Color tileColor;
    private static final Random rand = new Random();

    /**
     * Constructor, Track will read a double array and pass XPos, YPos, and tileType
     */
    public Tile(int XPos, int YPos, char tileType) {
        this.XPos = XPos;
        this.YPos = YPos;
        this.tileType = tileType;
        this.slowTile = false;

        // Placeholder for basic color
        Color baseColor;
        // Switch statement to set color and determine whether or not it is a slow tile
        switch (tileType) {
            // Dirt
            case 'D':
                // Saddle brown
                baseColor = new Color(139, 69, 19);
            // Checkpoint, Dirt Color
            case 'C':
                // Saddle brown
                baseColor = new Color(139, 69, 19);
                this.checkpoint = true;
            // Finish Line
            case 'F':
                this.checkpoint = true;
                baseColor = Color.WHITE;
            // Default is grass
            default:
                this.slowTile = true;
                // Forest green
                baseColor = new Color(34, 139, 34);
        }

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

    // Getters may need to update getters based on later game logic
    public Color getTileColor() {
        return tileColor;
    }

    public int getXPos() {
        return XPos;
    }

    public int getYPos() {
        return YPos;
    }

    public char getTileType() {
        return tileType;
    }

    public boolean isSlowTile() {
        return slowTile;
    }

    public boolean isCheckpoint() {
        return checkpoint;
    }
}
