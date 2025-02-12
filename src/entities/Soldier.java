package entities;

import map.*;

public class Soldier extends Civilian {
    protected EntityType entityType = EntityType.SOLDIER;

    public Soldier(Block block) {
        super(block);
        contacted = true;
        armed = true;
    }
}
