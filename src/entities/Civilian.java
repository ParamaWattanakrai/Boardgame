package src.entities;

import src.map.Block;
import src.utils.Direction;

public class Civilian extends Entity {
    private double hitRate = 0.75;
    protected Vitality vitality = Vitality.ALIVE;
    
    private int comaTime = 0;
    private boolean contacted = false;
    private boolean armed = false;

    private ActionType ActionType;
    private Runnable actionRunnable;
    private Block shootAt;

    public Civilian(Block block, int blockWidth, int blockHeight, int entitySize) {
        super(block, EntityType.CIVILIAN, blockWidth, blockHeight, entitySize);
    }

    public Civilian(Block block, EntityType entityType, int blockWidth, int blockHeight, int entitySize) {
        super(block, entityType, blockWidth, blockHeight, entitySize);
    }

    public boolean validateMove(Direction direction) {
        if (!isContacted() && vitality != Vitality.ALIVE) {
            System.out.println("not contact or not alive bro");
            return false;
        }
        Block neighborBlock = block.getNeighborBlock(direction);
        System.out.println("trying to move from " + block.getCoordinate() + " to " + neighborBlock.getCoordinate());
        return (neighborBlock != block &&
                block.getPath(direction).doesExist() &&
                neighborBlock.getPath(direction.getOpposite()).doesExist());
    }
 
    @Override
    public void move(Direction direction) {
        if (validateMove(direction)) {
            Block neighborBlock = block.getNeighborBlock(direction);
            System.out.println("moved from " + block.getCoordinate() + " to " + neighborBlock.getCoordinate());
            block.removeEntity(this);
            block = neighborBlock;
            neighborBlock.addEntity(this);
            assignPixelCoordinate();
        }
    }

    public boolean validateShoot(Direction direction) {
        if (!isArmed() && vitality != Vitality.ALIVE) {
            return false;
        }
        Block neighborBlock = block.getNeighborBlock(direction);
        return (neighborBlock != block &&
                neighborBlock.getAllDogs().size() > 0);
    }

    public void setShootAt(Block block) {
        shootAt = block;
        block.addShooter(this);
    }

    public void nullShootAt() {
        shootAt.removeShooter(this);
        shootAt = null;
    }

    public Block getShootAt() {
        return shootAt;
    }

    public void shoot() {
        System.out.println("Pew pew pew");
    }

    public boolean validateArm() {
        if (!armed && (block.getGunAmount() - block.getGunToBeLooted() > 0)) {
            return true;
        }
        System.out.println(!armed+", "+block.getGunAmount()+"-"+block.getGunToBeLooted());
        return false;
    }

    public void arm() {
        if (validateArm()) {
            armed = true;
            block.removeGunAmount();
        }
    }

    public boolean disarm() {
        if (armed) {
            block.addGunAmount();
            return true;
        }
        return false;
    }

    public void infect() {
        vitality = Vitality.COMA;
        disarm();
    }

    public void disinfect() {
        teleport(block.getField().getOccupiedHospitals().get(0));
        vitality = Vitality.ALIVE;
        comaTime = 0;
    }

    public void contact() {
        contacted = true;
    }

    public double getHitRate() {
        return hitRate;
    }

    public void setAction(ActionType ActionType, Runnable actionRunnable) {
        this.ActionType = ActionType;
        this.actionRunnable = actionRunnable;
    }

    public void nullAction() {
        ActionType = null;
        actionRunnable = null;
    }

    public ActionType getActionType() {
        return ActionType;
    }
    public Runnable getActionRunnable() {
        return actionRunnable;
    }

    public void comaTime() {
        comaTime++;
    }
    public int getComaTime() {
        return comaTime;
    }

    public Vitality getVitality() {
        return vitality;
    }
    public boolean isContacted() {
        return contacted;
    }
    public boolean isArmed() {
        return armed;
    }
}