package src.entities;

import src.map.Block;
import src.utils.Direction;

public class Civilian extends Entity {
    protected double hitRate = 0.75;
    protected Vitality vitality = Vitality.ALIVE;
    protected boolean inAction = false;
    protected boolean contacted = false;
    protected boolean armed = false;

    public Civilian(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.CIVILIAN, blockWidth, blockHeight, entitySize);
    }

    public Civilian(Block block, EntityType entityType, int blockWidth, int blockHeight, int entitySize) {
        super(block, entityType, blockWidth, blockHeight, entitySize);
    }

    public boolean validateMove(Direction direction) {
        if (!isContacted() && vitality == Vitality.ALIVE) {
            System.out.println("not contact or not alive bro");
            return false;
        }
        Block neighborBlock = block.getNeighborBlock(direction);
        System.out.println("trying to move from " + block.getCoordinate() + " to " + neighborBlock.getCoordinate());
        return (neighborBlock != block &&
                block.getPath(direction).doesExist() &&
                neighborBlock.getPath(direction.getOpposite()).doesExist());
    }

    @Override
    public void move(Direction direction) {
        if (validateMove(direction)) {
            Block neighborBlock = block.getNeighborBlock(direction);
            System.out.println("moved from " + block.getCoordinate() + " to " + neighborBlock.getCoordinate());
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.addEntity(this);
        }
    }

    public boolean arm() {
        if (!armed && block.removeGun()) {
            armed = true;
            return true;
        }
        return false;
    }

    public boolean disarm() {
        if (armed) {
            block.addGun();
            return true;
        }
        return false;
    }

    public void infect() {
        vitality = Vitality.COMA;
        disarm();
    }

    public void disinfect() {
        teleport(block.getField().getOccupiedHospitals().get(0));
        vitality = Vitality.ALIVE;
    }

    public void contact() {
        contacted = true;
    }

    public double getHitRate() {
        return hitRate;
    }

    public void trueAction() {
        inAction = true;
    }

    public void falseAction() {
        inAction = false;
    }

    public boolean isInAction() {
        return inAction;
    }

    public Vitality getVitality() {
        return vitality;
    }
    public boolean isContacted() {
        return contacted;
    }
    public boolean isArmed() {
        return armed;
    }
}