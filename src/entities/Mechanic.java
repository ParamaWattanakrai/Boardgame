package src.entities;
import src.map.Block;
import src.utils.Direction;

public class Mechanic extends Civilian {
    public Mechanic(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.MECHANIC, blockWidth, blockHeight, entitySize);
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
            block.getNeighborBlock(direction).getPath(direction.getOpposite()).buildBarricade();
        }
    }
}
