package src.entities;

import src.map.Block;
import src.utils.Direction;

import java.util.ArrayList;
import java.util.List;

public class Dog extends Entity {

    public Dog(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.DOG, blockWidth, blockHeight, entitySize);
    }

    public void algorithm() {
        if (block.getAllAlive().size() > 0) {
            bite(block.getAllAlive().get(0));
            return;
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
            return;
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
        move(moveDirectionCandidates.get((int) (Math.random() * 2) % (moveDirectionCandidates.size())));

        if (block.getAllAlive().size() > 0) {
            bite(block.getAllAlive().get(0));
        }        
    }

    public boolean pathBarricaded(Direction direction) {
        return block.getPath(direction).isBarricaded();
    }

    @Override
    public void move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block) {
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.addEntity(this);
        }
    }

    public void bite(Civilian civilian) {
        civilian.infect();
        System.out.println("I bit" + civilian);
    }
}
