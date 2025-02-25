package src.entities;
import src.map.Block;

public class Medic extends Civilian {
    protected EntityType entityType = EntityType.MEDIC;

    public Medic(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.MEDIC, blockWidth, blockHeight, entitySize);
        contact();
    }

    public boolean validateCure() {
        if (!isContacted() && vitality == Vitality.ALIVE) {
            return false;
        }
        return (block.getField().getOccupiedHospitals().size() > 0 &&
                block.getAllComa().size() > 0);
    }

    public void cure() {
        if (validateCure()) {
            block.getAllComa().get(0).disinfect();
        }
    }
}
