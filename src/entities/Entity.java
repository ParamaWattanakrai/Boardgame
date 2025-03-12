package src.entities;

import java.util.Random;
import src.map.Block;
import src.utils.Direction;
import src.utils.Tuple;

public abstract class Entity {
    protected EntityType entityType;
    protected Block block;

    protected Tuple previousCoordinate;
    protected Tuple pixelCoordinate;
    protected float alpha;

    protected int blockWidth;
    protected int blockHeight;
    protected int entitySize;

    public Entity(Block block, EntityType entityType, int blockWidth, int blockHeight, int entitySize) {
        this.entityType = entityType;
        this.block = block;
        this.previousCoordinate = null;
        this.alpha = 0f;
        block.addEntity(this);

        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.entitySize = entitySize;
        assignPixelCoordinate();
    }

    protected void assignPixelCoordinate() {
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
        block = toBlock;
        block.removeEntity(this);
        toBlock.addEntity(this);
        assignPixelCoordinate();
    }

    public abstract void move(Direction direction);

    public Block getBlock() {
        return block;
    }

    public Tuple getPixelCoordinate() {
        return pixelCoordinate;
    }

    public Tuple getPreviousCoordinate() {
        return previousCoordinate;
    }

    public void setPreviousCoordinate(Tuple previousCoordinate) {
        this.previousCoordinate = previousCoordinate;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
