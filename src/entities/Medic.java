package src.entities;
import src.map.Block;

public class Medic extends Civilian {
    protected EntityType entityType = EntityType.MEDIC;

    public Medic(Block block) {
        super(block, EntityType.MEDIC);
        contacted = true;
    }

    public boolean validateCure() {
        return (block.getField().getOccupiedHospitals().size() > 0 &&
                block.getAllComa().size() > 0);
    }

    public void cure() {
        if (validateCure()) {
            block.getAllComa().get(0).disinfect();
        }
    }
}
