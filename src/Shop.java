import java.awt.Font;
import java.awt.Rectangle;

public class Shop {

    public static Rectangle shop = new Rectangle(Main.windowWidth - 220, 20, 200, 80);
    public static Rectangle mainPanel = new Rectangle(Main.windowWidth-250, 0, 250, Main.windowHeight);
    public static int panelX = Main.windowWidth;
    public static int panelXVelocity = 50;
    public static Rectangle exitButton = new Rectangle(Main.windowWidth - 280, Main.windowHeight/2 - 50, 30, 100);

    public static ShopItem upgrade1 = new ShopItem("upgrade1", 100, 0, 1);
    public static ShopItem upgrade2 = new ShopItem("upgrade2", 250, 1, 0);
    public static ShopItem upgrade3 = new ShopItem("upgrade3", 500, 0, 6);
    public static ShopItem upgrade4 = new ShopItem("upgrade4", 1000, 5, 0);
    public static ShopItem upgrade5 = new ShopItem("upgrade5", 2500, 0, 30);
    public static ShopItem upgrade6 = new ShopItem("upgrade6", 5000, 30, 0);
    public static ShopItem upgrade7 = new ShopItem("upgrade7", 10000, 0, 125);
    public static ShopItem upgrade8 = new ShopItem("upgrade8", 50000, 400, 0);
    public static ShopItem[] itemList = {upgrade1, upgrade2, upgrade3, upgrade4, upgrade5, upgrade6, upgrade7, upgrade8};

    static Font shopFont = new Font("Comic Sans MS", Font.PLAIN, 20);

    public static Boolean animating = false;

    public static void animateShopOpen() {
        animating = true;
        panelX -= panelXVelocity;
        panelXVelocity -= 3;
        if (panelX > Main.windowWidth - 265 && panelX < Main.windowWidth - 235) {
            panelX = Main.windowWidth - 250;
            panelXVelocity = 50;
            animating = false;
        }
    }

    public static void animateShopClose() {
        animating = true;
        panelX += panelXVelocity;
        panelXVelocity -= 3;
        if (panelX > Main.windowWidth) {
            panelX = Main.windowWidth;
            panelXVelocity = 50;
            animating = false;
        }
    }

}
