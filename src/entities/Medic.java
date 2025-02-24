package src.entities;
import src.map.*;

public class Medic extends Civilian {
    protected EntityType entityType = EntityType.MEDIC;

    public Medic(Block block) {
        super(block, EntityType.MEDIC);
        contacted = true;
    }

    public boolean cure() {
        for (Civilian person : block.getAllCivilian()) {
            if (person.getVitality() == Vitality.COMA) {
                person.disinfect();
                return true;
            }
        }
        return false;
    }
}
