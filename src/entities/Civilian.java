package src.entities;

import src.map.*;
import src.utils.Direction;

public class Civilian extends Entity {
    protected double hitRate = 0.75;
    protected Vitality vitality = Vitality.ALIVE;
    protected boolean inAction = false;
    protected boolean contacted = false;
    protected boolean armed = false;

    public Civilian(Block block) {
        super(block, EntityType.CIVILIAN);
    }

    public Civilian(Block block, EntityType entityType) {
        super(block, entityType);
    }

    public boolean validateMove(Direction direction) {
        if (!isContacted() && vitality == Vitality.ALIVE) {
            return false;
        }
        Block neighborBlock = block.getNeighborBlock(direction);
        return (neighborBlock != block &&
                block.getPath(direction).doesExist() &&
                neighborBlock.getPath(direction).doesExist());
    }

    @Override
    public void move(Direction direction) {
        if (validateMove(direction)) {
            Block neighborBlock = block.getNeighborBlock(direction);
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