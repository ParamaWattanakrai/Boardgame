package src.entities;

import src.map.Block;
import src.utils.Direction;
import src.utils.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Dog extends Entity {
    private boolean actioned = false;
    private static List<Tuple> biteLocations = new ArrayList<>();

    public Dog(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.DOG, blockWidth, blockHeight, entitySize);
    }

    public static List<Tuple> getBiteLocations() {
        return biteLocations;
    }
    
    public static void clearBiteLocations() {
        biteLocations.clear();
    }

    public boolean algorithm(boolean canBiteBarricade) {
        if (actioned) return false;

        actioned = true;
        boolean hasBittenBarricade = false;

        if (block.getAllAlive().size() > 0) {
            recordBiteLocation();
            bite(block.getAllAlive().get(0));
            return hasBittenBarricade;
        }
        
        List<Direction> moveDirectionCandidates = new ArrayList<>();
        int x1 = block.getCoordinate().getA();
        int y1 = block.getCoordinate().getB();
        Block targetBlock = block;
        for (int i = 0; i < block.getField().getFieldWidth(); i++) {
            List<Block> manhattanBlocks = block.getManhattanBlocks(i);
            if (manhattanBlocks.size() < 1) {
                break;
            }
            for (Block curBlock : manhattanBlocks) {
                if (curBlock.getAllAlive().size() > 0) {
                    targetBlock = curBlock;
                }
            }
        }

        if (targetBlock == block) {
            return hasBittenBarricade;
        }

        int x2 = targetBlock.getCoordinate().getA();
        int y2 = targetBlock.getCoordinate().getB();
        int xDifference = x2 - x1;
        int yDifference = y2 - y1;

        if (xDifference != 0) {
            if (xDifference > 0) {
                moveDirectionCandidates.add(Direction.EAST);
            } else {
                moveDirectionCandidates.add(Direction.WEST);
            }
        }

        if (yDifference != 0) {
            if (yDifference > 0) {
                moveDirectionCandidates.add(Direction.SOUTH);
            } else {
                moveDirectionCandidates.add(Direction.NORTH);
            }
        }

        System.out.println("Dog moving " + moveDirectionCandidates);
        Direction moveDirection = moveDirectionCandidates.get((int) (Math.random() * 2) % (moveDirectionCandidates.size()));
        if (!block.getPath(moveDirection).isBarricaded()) {
            move(moveDirection);
        } else if (canBiteBarricade) {
            block.biteBarricade(moveDirection);
            hasBittenBarricade = true;
            return hasBittenBarricade;
        }

        if (block.getAllAlive().size() > 0) {
            recordBiteLocation();
            bite(block.getAllAlive().get(0));
        }
        return hasBittenBarricade;
    }

    @Override
    public void move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block) {
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.addEntity(this);
            assignPixelCoordinate();
        }
    }

    public void bite(Civilian civilian) {
        civilian.infect();
        System.out.println("I bite " + civilian);
    }

    private void recordBiteLocation() {
        int gridX = block.getCoordinate().getA();
        int gridY = block.getCoordinate().getB();
        Tuple location = new Tuple(gridX, gridY);
        
        if (!biteLocations.contains(location)) {
            biteLocations.add(location);
        }
    }

    public void unActioned() {
        actioned = false;
    }
}
