package src.entities;

import src.map.*;

public class Soldier extends Civilian {
    private double hitRate = 0.9;
    public Soldier(Block block) {
        super(block, EntityType.SOLDIER);
        contacted = true;
        armed = true;
    }

    @Override
    public double getHitRate() {
        return hitRate;
    }
}
