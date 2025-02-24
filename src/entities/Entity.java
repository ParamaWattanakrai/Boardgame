package src.entities;

import src.map.Block;
import src.utils.Direction;

public abstract class Entity {
    protected EntityType entityType;
    protected Block block;

    public Entity(Block block, EntityType entityType) {
        this.entityType = entityType;
        this.block = block;
        block.addEntity(this);
    }

    public void kill() {
        block.removeEntity(this);
    }

    public abstract void move(Direction direction);
    // {
    //     Block neighborBlock = block.getNeighborBlock(direction);
    //     if (neighborBlock != block) {
    //         block.removeEntity(this);
    //         block = neighborBlock;
    //         neighborBlock.removeEntity(this);
    //         return true;
    //     }
    //     return false;
    // }

    public EntityType getEntityType() {
        return entityType;
    }
}
