package src.entities;

import src.map.*;

public class Soldier extends Civilian {

    public Soldier(Block block) {
        super(block, EntityType.SOLDIER);
        contacted = true;
        armed = true;
    }
}
