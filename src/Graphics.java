import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class Graphics extends JPanel {

    Random random = new Random();

    public static Boolean shopOpen = false;
    public static int gap = 0;
    
    Ellipse2D.Double click;
    int clickX = Main.windowWidth/2-200;
    int clickY = Main.windowHeight/2-200;
    int clickSize = 400;
    int clickRotation = 0;
    double clickSizeChange = 0;
    private Timer clickedAnimation;
    Boolean becomeBigger = true;

    Timer perSecondTimer;
    Timer secondTick;

    ArrayList<ClickParticle> particles = new ArrayList<>();
    ArrayList<ClickParticle> removedParticles = new ArrayList<>();

    static Timer shopOpenAnimation;
    static Timer shopCloseAnimation;

    public Graphics() {
        setBackground(Color.GRAY);
        Images.loadImages();

        click = new Ellipse2D.Double(Main.windowWidth/2-150, Main.windowHeight/2-150, 300, 300);

        Timer mainTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < particles.size(); i++) {
                    if (particles.get(i).y >= Main.windowHeight) {
                        removedParticles.add(particles.get(i));
                    }
                }
                
                particles.removeAll(removedParticles);
                
                removedParticles.clear();

                if (GameData.perSecondMutiplier != 0) {
                    int newDelay = 1000 / (GameData.perSecondMutiplier);
                    if (secondTick.getDelay() != newDelay) {
                        //secondTick.stop(); 
                        secondTick.setDelay(newDelay); 
                        //secondTick.start(); 
                    }
                }
                
                repaint();
            }
        });

        

        Timer buttonAnimation = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (clickSizeChange < -5) {
                    becomeBigger = true;
                }
                if (clickSizeChange > 5) {
                    becomeBigger = false;
                }
                if (becomeBigger) {
                    clickSizeChange += 0.5;
                } else {
                    clickSizeChange -= 0.5;
                }
                clickSize += (int)(clickSizeChange);
                
                clickX -= (int) clickSizeChange/2;
                clickY -= (int) clickSizeChange/2;
                repaint();
                
            }
        });

        secondTick = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GameData.perSecondMutiplier >0) {
                    GameData.score += 1;
                }
            }
        });

        clickedAnimation = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (int i = 0; i < particles.size(); i++) {
                    particles.get(i).animateParticle();
                }
                
            }
        });

        shopOpenAnimation = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop.animateShopOpen();
                if (Shop.panelX == Main.windowWidth - 250) {
                    shopOpenAnimation.stop();
                    shopOpen = true;
                }
            }
        });

        shopCloseAnimation = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shop.animateShopClose();
                if (Shop.panelX == Main.windowWidth) {
                    shopCloseAnimation.stop();
                    shopOpen = false;
                }
            }
        });

        mainTimer.start();
        secondTick.start();
        buttonAnimation.start();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (click.contains(e.getPoint())) {
                    GameData.score += 1 * GameData.clickMultiplier;
                    int i = 0;
                    while (i < GameData.clickMultiplier && i <= 15) {
                        particles.add(new ClickParticle());
                        i++;
                    }
                    clickedAnimation.start();
                }
                if (shopOpen) {
                    gap = 30;
                    for (ShopItem item : Shop.itemList) {
                        item.button = new Rectangle2D.Double(Main.windowWidth-220, gap, 190, 70);
                        gap += 100;
                        if (item.button.contains(e.getPoint())) {
                            if (GameData.score >= item.price) {
                                GameData.score -= item.price;
                                if (item.perSecondMutiplier == 0) {
                                    GameData.clickMultiplier += item.clickMultiplier;
                                } else {
                                    GameData.perSecondMutiplier += item.perSecondMutiplier;
                                }
                            } 
                        }
                    }
                }
                if (Shop.shop.contains(e.getPoint())) {
                    if (shopOpen == false && !Shop.animating) {
                        shopOpenAnimation.start();
                    }
                    
                }
                if (Shop.exitButton.contains(e.getPoint()) || Shop.mainPanel.contains(e.getPoint()) == false) {
                    if (shopOpen == true && !Shop.animating) {
                        shopCloseAnimation.start();
                    }
                }
                
                
            }
        });
    }


    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, Main.windowWidth, Main.windowHeight/6);
        g2d.fillRect(0, Main.windowHeight/3, Main.windowWidth, Main.windowHeight/6);
        g2d.fillRect(0, 2*Main.windowHeight/3, Main.windowWidth, Main.windowHeight/6);

        g2d.setColor(Color.BLACK);
        if (particles != null) {
            for (int i = 0; i < particles.size(); i++) {
                g2d.setColor(particles.get(i).randomGray);
                g2d.fillOval(particles.get(i).x, particles.get(i).y, particles.get(i).size, particles.get(i).size);
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.drawImage(Images.button, clickX, clickY, clickSize, clickSize, this);

        g2d.setColor(Color.GRAY);
        g2d.fillRect(Shop.panelX, 0, 250, Main.windowHeight);

        if (shopOpen || Shop.animating) {
            
            g2d.setColor(Color.BLACK);
            Shop.exitButton = new Rectangle(Shop.panelX - 30, Main.windowHeight/2 - 50, 30, 100);
            g2d.fill(Shop.exitButton);
            gap = 30;
            for (ShopItem item : Shop.itemList) {

                item.button = new Rectangle2D.Double(Shop.panelX + 30, gap, 190, 70);
                g2d.setColor(Color.BLACK);
                g2d.fill(item.button);

                g2d.setColor(Color.WHITE);
                g2d.setFont(Shop.shopFont);
                FontMetrics metrics = g2d.getFontMetrics(Shop.shopFont);
                int textWidth = metrics.stringWidth(item.buttonLabel + ": " + item.displayedPrice);
                g2d.drawString(item.buttonLabel + ": " + item.displayedPrice, Shop.panelX + 30 + (190 - textWidth) / 2, gap+25); 

                if (item.perSecondMutiplier == 0) {
                    metrics = g2d.getFontMetrics(Shop.shopFont);
                    textWidth = metrics.stringWidth("+" + item.clickMultiplier + " per click");
                    g2d.drawString("+" + item.clickMultiplier + " per click",Shop.panelX + 30 + (190 - textWidth) / 2, gap+55); 
                } else {
                    metrics = g2d.getFontMetrics(Shop.shopFont);
                    textWidth = metrics.stringWidth("+" + item.perSecondMutiplier + " per second");
                    g2d.drawString("+" + item.perSecondMutiplier + " per second", Shop.panelX + 30 + (190 - textWidth) / 2, gap+55); 
                }
                
                gap += 100;
            }
            
            
        } else {

            g2d.setColor(Color.BLACK);
            g2d.fill(Shop.shop);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
            FontMetrics metrics = g2d.getFontMetrics(new Font("Comic Sans MS", Font.PLAIN, 50));
            int textWidth = metrics.stringWidth("Shop");
            g2d.drawString("Shop", Main.windowWidth - 220 + (200-textWidth)/2, 75); 

        }
        
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        g2d.drawString("Points: " + GameData.score, 25, 70); 

    }
}
