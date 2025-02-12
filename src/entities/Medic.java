package entities;

import utils.*;
import map.*;

public class Medic extends Civilian {
    protected EntityType entityType = EntityType.MEDIC;

    public Medic(Block block) {
        super(block);
        contacted = true;
    }

    public boolean cure() {
        for (Civilian person : block.getPopulation()) {
            if (person.getVitality() == Vitality.COMA) {
                person.disinfect();
                return true;
            }
        }
        return false;
    }
}
