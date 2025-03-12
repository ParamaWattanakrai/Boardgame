package src.entities;
import src.map.Block;
import src.map.BlockType;

public class Medic extends Civilian {
    public Medic(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.MEDIC, blockWidth, blockHeight, entitySize);
        contact();
    }

    public boolean validateCure() {
        if (vitality != Vitality.ALIVE) {
            return false;
        }
        return (block.getField().getOccupationMap().get(BlockType.HOSPITAL) &&
                block.getAllComa().size() > 0);
    }

    public void cure() {
        if (validateCure()) {
            for (Civilian civilian : block.getAllComa()) {
                civilian.disinfect();
            }
        }
    }
}
