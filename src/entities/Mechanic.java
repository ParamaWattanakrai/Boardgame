package src.entities;
import src.map.Block;
import src.utils.Direction;

public class Mechanic extends Civilian {
    protected EntityType entityType = EntityType.MECHANIC;
    public Mechanic(Block block) {
        super(block, EntityType.MECHANIC);
    }

    public boolean buildBarricade(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block && block.getPath(direction).buildBarricade()) {
            return true;
        }
        return false;
    }
}
