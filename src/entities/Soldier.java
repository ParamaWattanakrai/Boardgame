package src.entities;

import src.map.*;

public class Soldier extends Civilian {
    private double hitRate = 0.9;
    public Soldier(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.SOLDIER, blockWidth, blockHeight, entitySize);
        contacted = true;
        armed = true;
    }

    @Override
    public double getHitRate() {
        return hitRate;
    }
}
