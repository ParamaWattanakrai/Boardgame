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

    public void teleport(Block toBlock) {
        block.removeEntity(this);
        toBlock.addEntity(this);
    }

    public abstract void move(Direction direction);

    public EntityType getEntityType() {
        return entityType;
    }
}
