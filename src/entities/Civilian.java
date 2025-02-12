package entities;

import utils.*;
import map.*;

public class Civilian {
    protected Block block;
    protected Vitality vitality = Vitality.ALIVE;
    protected boolean contacted = false;
    protected boolean armed = false;

    Civilian(Block block) {
        this.block = block;
        block.addPerson(this);
    }

    public boolean move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block) {
            block = neighborBlock;
            neighborBlock.addPerson(this);
            return true;
        }
        return false;
    }

    public void infect() {
        vitality = Vitality.COMA;
        disarm();
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