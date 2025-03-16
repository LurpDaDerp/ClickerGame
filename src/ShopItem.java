import java.awt.geom.*;

public class ShopItem {

    String buttonLabel;
    long price;
    String displayedPrice;
    int clickMultiplier;
    int perSecondMutiplier;
    public static Rectangle2D button;
    public static int y = 30 + Graphics.gap;

    public ShopItem(String label, long price, int clickMultiplier, int perSecondMultiplier) {
        this.buttonLabel = label;
        this.price = price;
        this.clickMultiplier = clickMultiplier;
        this.perSecondMutiplier = perSecondMultiplier;
        
        displayedPrice = formatPrice(price);
    }
    
    private String formatPrice(long price) {
        final String[] suffixes = {"", "K", "M", "B", "T", "Q"};
        long divisor = 1;
    
        for (int i = 0; i < suffixes.length; i++) {
            if (price < divisor * 1000) {
                return i == 0 
                    ? String.valueOf(price) 
                    : String.format("%.1f%s", (double) price / divisor, suffixes[i]);
            }
            divisor *= 1000;
        }
    
        return String.format("%.1fQ+", (double) price / divisor);  //if price more than 1Q
    }

}