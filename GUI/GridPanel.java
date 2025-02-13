package GUI;

import GUI.utils.ImageLoader;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
public class GridPanel {
    private final Image civilianImage = ImageLoader.loadImage("img/civilian.jpg");
    private final Image medicImage = ImageLoader.loadImage("img/medic.jpg");
    private final Image soldierImage = ImageLoader.loadImage("img/soldier.jpg");
    private final Image engineerImage = ImageLoader.loadImage("img/engineer.jpg");

    // private final Image oneMap = ImageLoader.loadImage("img/map/1.png");
    // private final Image twoMap = ImageLoader.loadImage("img/map/2.png");
    // private final Image treeMap = ImageLoader.loadImage("img/map/3.png");

    private final static int SIZE = 12;
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
        // panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.addMouseListener(new GridMouseListener(panel, num));
    }

    private void drawImages(Graphics g) {
        // drawMap(g); 
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

    // private void drawMap(Graphics g) {
    //     switch (new Random().nextInt(3)) {
    //         case 0 -> g.drawImage(oneMap, 0, 0, 120,120, null);
    //         case 1 -> g.drawImage(twoMap, 0, 0, 120,120, null);
    //         case 2 -> g.drawImage(treeMap, 0, 0,120,120,null);
    //     }

    // }

    
    private void drawRandomImage(Graphics g, Image image) {
            Random rand = new Random();
            int x = rand.nextInt(panel.getWidth() - SIZE);
            int y = rand.nextInt(panel.getHeight() - SIZE);
            g.drawImage(image, x, y, SIZE, SIZE, null);
    }
        
    public JPanel getPanel() { 
        return panel;
    }
}