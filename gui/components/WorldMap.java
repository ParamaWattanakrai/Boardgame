package gui.components;

import gui.MainFrame;
import gui.enums.GameMode;
import gui.enums.ImageResource;
import gui.screens.Game;
import gui.utils.ImageDrawer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import src.entities.ActionType;
import src.entities.Civilian;
import src.entities.Mechanic;
import src.map.Block;
import src.utils.Direction;
import src.utils.Tuple;

public class WorldMap extends JPanel {
    private Road[][] road = new Road[5][5];

    private final MainFrame mainFrame;
    private final Game game;
    private Road select = null;
    private ActionType action;
    private Civilian alive;

    public WorldMap(Game game, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.game = game;
        createBlock();
        setLayout(new GridLayout(5, 5));
        setOpaque(false);
    }

    private void createBlock() {
        for (int gridY = 0; gridY < 5; gridY++) {
            for (int gridX = 0; gridX < 5; gridX++) {
                road[gridX][gridY] = new Road(gridX, gridY, this);
                add(road[gridX][gridY]);
            }
        }
    }

    public void repaintAllRoads() {
        for (int gridY = 0; gridY < 5; gridY++) {
            for (int gridX = 0; gridX < 5; gridX++) {
                road[gridX][gridY].revalidate();
                road[gridX][gridY].repaint();
            }
        }
    }

    public void resetActionRoads() {
        for (int gridY = 0; gridY < 5; gridY++) {
            for (int gridX = 0; gridX < 5; gridX++) {
                road[gridX][gridY].setCanMove(false);
                road[gridX][gridY].setCanShot(false);
            }
        }
    }

    public void resetPerRoads() {
        for (int gridY = 0; gridY < 5; gridY++) {
            for (int gridX = 0; gridX < 5; gridX++) {
                road[gridX][gridY].setPreviewDog(false);
            }
        }
    }

    public Road[][] getAllRoad() {
        return road;
    }

    public Road getRoad(int gridX, int gridY) {
        return road[gridX][gridY];
    }

    public void setRoad(Road[][] road) {
        this.road = road;
    }

    public Road getSelect() {
        return select;
    }

    public void setSelect(Road select) {
        this.select = select;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public Civilian getAlive() {
        return alive;
    }

    public void setAlive(Civilian alive) {
        this.alive = alive;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2d.drawImage(ImageResource.MAP.getImage(), 0, 0, getWidth(), getHeight(), null);
        g2d.dispose();

        ImageDrawer drawer = new ImageDrawer();
        drawer.drawRoad(g, this, mainFrame);
        drawer.drawBarricade(g, this, mainFrame);
        drawer.drawLandmark(g, this, mainFrame);
        drawer.drawPopulation(g, this, mainFrame);
        drawer.drawDog(g, this, mainFrame);        
    }

    public class Road extends JPanel {
        private final WorldMap map;
        private final int gridX;
        private final int gridY;
        private boolean canMove = false;
        private boolean canShot = false;
        private boolean previewDog = false;

        private int a = 50;
        private int num = -1;

        public Road(int gridX, int gridY, WorldMap map) {
            this.gridX = gridX;
            this.gridY = gridY;
            this.map = map;
            setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1));
            setOpaque(false);
            MouseListener();
        }

        private void MouseListener() {
            addMouseListener(new java.awt.event.MouseListener() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    Block block = mainFrame.getField().getBlock(new Tuple(gridX, gridY));
                    System.out.println(block.getCoordinate());
                    for (Direction direction : Direction.values()) {
                        System.out.println(direction + ", " + block.getPath(direction));
                    }
                    if (game.getMode() == GameMode.Action && map.getSelect() != null && map.getAction() != null) {
                        if (nearBlock(gridX, gridY)) {
                            game.resetButton();
                            int startX = map.getSelect().getGridX();
                            int startY = map.getSelect().getGridY();
                            int dx = gridX - startX;
                            int dy = gridY - startY;

                            Direction direction = Direction.offsetToDirection(new Tuple(dx, dy));
                            Civilian civilian = map.getAlive();
                            System.out.println(civilian.getEntityType());

                            switch (map.getAction()) {
                                case MOVE -> {
                                    if (civilian.validateMove(direction)) {
                                        mainFrame.getField().addAction(ActionType.MOVE, civilian,
                                                () -> civilian.move(direction));
                                    }
                                }
                                case SHOOT -> {
                                    if (civilian.validateShoot(direction)) {
                                        mainFrame.getField().addAction(ActionType.SHOOT, civilian,
                                                () -> civilian.shoot());
                                        civilian.getBlock().getNeighborBlock(direction).addShooter(civilian);
                                    }
                                }
                                case BUILD -> {
                                    if (!(civilian instanceof Mechanic)) {
                                        System.out.println("Not a mechanic!");
                                        break;
                                    }
                                    Mechanic mechanic = (Mechanic) civilian;
                                    if (mechanic.validateBuildBarricade(direction)) {
                                        mainFrame.getField().addAction(ActionType.BUILD, mechanic,
                                                () -> mechanic.buildBarricade(direction));
                                    }
                                }
                                default -> {
                                }
                            }
                            civilian.getBlock().getField().printAction();
                        }

                        resetActionRoads();
                        map.setSelect(null);
                        game.setMode(GameMode.Default);

                    } else {
                        game.repaint();
                        map.setSelect(map.getRoad(gridX, gridY));
                    }

                    game.loadEntityButton(gridX, gridY);
                    game.loadInActionButton();
                    map.repaintAllRoads();
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                }
            });
        }

        public boolean nearBlock(int x, int y) {
            for (Direction direction : Direction.values()) {
                int newX = x + direction.getOffset().getA();
                int newY = y + direction.getOffset().getB();
                if (newX >= 0 && newX < 5 && newY >= 0 && newY < 5 && map.getSelect() == map.getRoad(newX, newY)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            if (map.getSelect() == this) {
                g2d.setColor(new Color(0, 255, 0, 50));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            if (canMove) {
                g2d.setColor(new Color(255, 255, 0, a));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }

            if (canShot) {
                g2d.setColor(new Color(255, 0, 0, a));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.drawImage(ImageResource.SHOOT.getImage(), 0, 0, getHeight(), getHeight(), null);
            }

            if (previewDog) {
                g2d.setColor(new Color(255, 0, 0, a));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                repaint();
            }


            if (a >= 80) {
                num = -1;  
            } else if (a <= 10) {
                num = 1; 
            }

            a += num;
            g2d.dispose();
        }

        public int getGridX() {
            return gridX;
        }

        public int getGridY() {
            return gridY;
        }

        public boolean isCanMove() {
            return canMove;
        }

        public void setCanMove(boolean canMove) {
            this.canMove = canMove;
        }

        public boolean isCanShot() {
            return canShot;
        }

        public void setCanShot(boolean canShot) {
            this.canShot = canShot;
        }

        public boolean isPreviewDog() {
            return previewDog;
        }

        public void setPreviewDog(boolean previewDog) {
            this.previewDog = previewDog;
        }
    }
}
