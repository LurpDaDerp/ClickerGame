import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Images {

    public static BufferedImage square;
    public static BufferedImage button;

    public static void loadImages() {
        try {
            Images.button = ImageIO.read(new File("resources/button.png")); 
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
}
