package src.entities;
import src.map.Block;
import src.utils.Direction;

public class Dog extends Entity {

    public Dog(Block block) {
        super(block, EntityType.DOG);
    }

    @Override
    public boolean move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block) {
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.removeEntity(this);
            return true;
        }
        return false;
    }
}
