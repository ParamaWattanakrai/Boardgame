package src.entities;

import src.enums.EntityType;
import src.map.*;

public class Soldier extends Civilian {
    private double hitRate = 0.9;

    public Soldier(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.SOLDIER, blockWidth, blockHeight, entitySize);
        contact();
        arm();
    }

    @Override
    public double getHitRate() {
        return hitRate;
    }

    @Override
    public void infect() {
        vitality = Vitality.COMA;
    }
}
