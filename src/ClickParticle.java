
import java.awt.Color;
import java.util.Random;

public class ClickParticle {
    static Random random = new Random();
    int x = Main.windowWidth/2;
    int y = Main.windowHeight/2 - 120;
    int size = random.nextInt(20, 40);
    int xVelocity = random.nextInt(-15, 16);
    int yVelocity = random.nextInt(25, 35);

    int randomGrayNumber = random.nextInt(0, 75);
    Color randomGray = new Color(randomGrayNumber, randomGrayNumber, randomGrayNumber);

    public void animateParticle() {
        x += xVelocity;
        y -= yVelocity;
        yVelocity -= 3;
    }
}
