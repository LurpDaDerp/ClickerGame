import javax.swing.*;

public class Main {

    public static int windowWidth = 1600;
    public static int windowHeight = 900;

    public static void main(String[] args) {
        
        JFrame gameFrame = new JFrame("Clicker Game Template");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(windowWidth, windowHeight); 
        gameFrame.setResizable(false); 

        Graphics panel = new Graphics();
        gameFrame.add(panel);

        gameFrame.setLocationRelativeTo(null);

        gameFrame.setVisible(true);
    }
}
