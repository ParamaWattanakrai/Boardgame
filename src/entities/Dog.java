package src.entities;
import src.map.Block;
import src.utils.Direction;

public class Dog extends Entity {

    public Dog(Block block) {
        super(block, EntityType.DOG);
    }

    public void algorithm() {
        if (block.getAllCivilians().size() > 0) {

        }
    }

    public boolean pathBarricaded(Direction direction) {
        return block.getPath(direction).isBarricaded();
    }

    @Override
    public void move(Direction direction) {
        Block neighborBlock = block.getNeighborBlock(direction);
        if (neighborBlock != block) {
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.removeEntity(this);
        }
    }

    public void bite(Civilian civilian) {
        civilian.infect();
    }
}
