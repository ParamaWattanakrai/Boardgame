package GUI;

import GUI.utils.ImageLoader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class GridPanel {
    private final Image civilianImage = ImageLoader.loadImage("img/civilian.jpg");
    private final Image medicImage = ImageLoader.loadImage("img/medic.jpg");
    private final Image soldierImage = ImageLoader.loadImage("img/soldier.jpg");
    private final Image engineerImage = ImageLoader.loadImage("img/engineer.jpg");

    private final ArrayList<Point> drawnPositions = new ArrayList<>();
    private final static int SIZE = 10;
    private final int num;
    private final JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawImages(g);
        }
    }; 

    public GridPanel(int num) {
        this.num = num;
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.addMouseListener(new GridMouseListener(panel, num));
    }

    private void drawImages(Graphics g) {
        drawUnits(g, Frame.gameData.get(num).getCivilian(), civilianImage);
        drawUnits(g, Frame.gameData.get(num).getMedic(), medicImage); 
        drawUnits(g, Frame.gameData.get(num).getSoldier(), soldierImage); 
        drawUnits(g, Frame.gameData.get(num).getEngineer(), engineerImage); 
    }
    
    private void drawUnits(Graphics g, int unitCount, Image unitImage) {
        for (int i = 0; i < unitCount; i++) {
            drawRandomImage(g, unitImage);
        }
    }

    private void drawRandomImage(Graphics g, Image image) {
        Random rand = new Random();
        int x, y;
        boolean positionFound = false;

        while (!positionFound) {
            x = rand.nextInt(panel.getWidth() - SIZE);
            y = rand.nextInt(panel.getHeight() - SIZE);

            boolean isOverlapping = false;
            for (Point p : drawnPositions) {
                if (Math.abs(p.x - x) < SIZE && Math.abs(p.y - y) < SIZE) {
                    isOverlapping = true;
                    break;
                }
            }

            if (!isOverlapping) {
                drawnPositions.add(new Point(x, y));
                g.drawImage(image, x, y, SIZE, SIZE, null);
                positionFound = true;
            }
        }
    }

    public JPanel getPanel() { 
        return panel;
    }
}