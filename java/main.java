import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class main extends JPanel {
    private String currentScene;
    private ArrayList<String> previousScenes;
    private int treasureChoice;

    public main() {
        currentScene = "start"; // Initial scene
        previousScenes = new ArrayList<>();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.LIGHT_GRAY);
        
        // Mouse listener for clicking
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void handleMouseClick(int x, int y) {
        // Simple interaction based on click coordinates
        switch (currentScene) {
            case "start":
                if (x >= 300 && x <= 500 && y >= 250 && y <= 350) {
                    previousScenes.add(currentScene);
                    currentScene = "forest"; 
                }
                break;
            case "forest":
                if (x >= 100 && x <= 300 && y >= 100 && y <= 200) {
                    previousScenes.add(currentScene);
                    currentScene = "cave"; 
                } else if (x >= 900 && x <= 700 && y >= 400 && y <= 500) {
                    JOptionPane.showMessageDialog(this, "You turn back to safety.");
                    currentScene = "start"; 
                } else if (x >= 400 && x <= 600 && y >= 350 && y <= 450) {
                    JOptionPane.showMessageDialog(this, "The forest whispers eerie secrets...");
                }
                break;
            case "cave":
                if (x >= 350 && x <= 450 && y >= 250 && y <= 350) {
                    treasureChoice = new Random().nextInt(7); 
                    showTreasureOptions();
                } else if (x >= 600 && x <= 700 && y >= 400 && y <= 500) {
                    if (!previousScenes.isEmpty()) {
                        currentScene = previousScenes.remove(previousScenes.size() - 1); 
                    } else {
                        currentScene = "forest"; 
                    }
                }
                break;
            case "treasure":
                int choice = JOptionPane.showOptionDialog(this,
                        "Choose your treasure:\n1. Golden Chalice\n2. Silver Ring\n3. Ruby Necklace\n4. Ancient Coin\n5. Cursed Amulet\n6. Diamond",
                        "Select Treasure",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"1", "2", "3", "4", "5", "6"},
                        null);

                if (choice == treasureChoice) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You found the right treasure!");
                    currentScene = "start"; 
                } else {
                    JOptionPane.showMessageDialog(this, "Wrong choice! You are trapped in the cave!");
                    currentScene = previousScenes.remove(previousScenes.size() - 1); 
                }
                break;
        }

        repaint(); 
    }

    private void showTreasureOptions() {
        currentScene = "treasure"; 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(new Font("Arial", Font.BOLD, 24));
        
        if (currentScene.equals("start")) {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawString("Welcome to the Adventure Game!", 250, 100);
            g.drawString("Click here to enter the haunted forest.", 250, 300);
        } else if (currentScene.equals("forest")) {
            g.setColor(new Color(34, 139, 34)); 
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawString("You are in a dark forest filled with whispers.", 200, 100);
            g.drawString("Click here to enter the cave.", 150, 150);
            g.drawString("Click here to go back.", 650, 450);
            g.drawString("Click here for eerie secrets...", 400, 400);
        } else if (currentScene.equals("cave")) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.drawString("You are in a cave filled with shadows.", 250, 100);
            g.drawString("Click here to find treasure.", 350, 300);
            g.drawString("Click here to go back to the forest.", 600, 450);
        } else if (currentScene.equals("treasure")) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawString("Choose your treasure wisely...", getWidth() / 3, getHeight() / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Point-and-Click Adventure Game");
        main gamePanel = new main();

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.requestFocusInWindow(); 
    }
}
