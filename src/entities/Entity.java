package src.entities;

import java.util.Random;

import src.map.Block;
import src.utils.Direction;
import src.utils.Tuple;

public abstract class Entity {
    protected EntityType entityType;
    protected Block block;

    protected Tuple pixelCoordinate;

    public Entity(Block block, EntityType entityType, int blockWidth, int blockHeight, int entitySize) {
        this.entityType = entityType;
        this.block = block;
        block.addEntity(this);
        assignPixelCoordinate(blockWidth, blockHeight, entitySize);
    }

    public void assignPixelCoordinate(int blockWidth, int blockHeight, int entitySize) {
        int loop = 0;
        int posX = new Random().nextInt(blockWidth - entitySize);
        int posY = new Random().nextInt(blockHeight - entitySize);
        while (loop < 10 && block.coordinateOccupied(posX, posY, entitySize)) {
            posX = new Random().nextInt(blockWidth - entitySize);
            posY = new Random().nextInt(blockHeight - entitySize);
        }
        pixelCoordinate = new Tuple(posX, posY);
    }

    public void kill() {
        block.removeEntity(this);
    }

    public void teleport(Block toBlock) {
        block.removeEntity(this);
        toBlock.addEntity(this);
    }

    public abstract void move(Direction direction);

    public Tuple getPixelCoordinate() {
        return pixelCoordinate;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
