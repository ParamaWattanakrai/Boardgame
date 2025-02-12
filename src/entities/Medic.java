package entities;
import map.*;

public class Medic extends Civilian {
    protected EntityType entityType = EntityType.MEDIC;

    public Medic(Block block) {
        super(block);
        contacted = true;
    }

    public boolean cure() {
        for (Civilian person : block.getEveryone()) {
            if (person.getVitality() == Vitality.COMA) {
                person.disinfect();
                return true;
            }
        }
        return false;
    }
}
