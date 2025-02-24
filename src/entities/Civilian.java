package src.entities;

import src.map.*;
import src.utils.Direction;

public class Civilian extends Entity {
    protected double hitRate = 0.75;
    protected Vitality vitality = Vitality.ALIVE;
    protected boolean contacted = false;
    protected boolean armed = false;

    public Civilian(Block block) {
        super(block, EntityType.CIVILIAN);
    }

    public Civilian(Block block, EntityType entityType) {
        super(block, entityType);
    }

    @Override
    public boolean move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block &&
        block.getPath(direction).doesExist() && neighborBlock.getPath(direction).doesExist()) {
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.removeEntity(this);
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

    public boolean arm() {
        if (!armed && block.removeGun()) {
            armed = true;
            return true;
        }
        return false;
    }
    public void disarm() {
        if (armed) {
            block.addGun();
        }
    }

    public double getHitRate() {
        return hitRate;
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