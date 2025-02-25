package src.entities;
import src.map.Block;
import src.utils.Direction;

public class Mechanic extends Civilian {
    protected EntityType entityType = EntityType.MECHANIC;
    public Mechanic(Block block) {
        super(block, EntityType.MECHANIC);
    }

    public boolean validateBuildBarricade(Direction direction) {
        if (!isContacted() && vitality == Vitality.ALIVE) {
            return false;
        }
        Block neighborBlock = block.getNeighborBlock(direction);
        return (neighborBlock != block &&
                !block.getPath(direction).isBarricaded());
    }

    public void buildBarricade(Direction direction) {
        if (validateBuildBarricade(direction)) {
            block.getPath(direction).buildBarricade();
        }
    }
}
