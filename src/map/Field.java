package src.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import src.entities.*;
import src.enums.*;

public class Field {
    Random rand = new Random();
    MetaSettings metaSettings;

    private int fieldHeight;
    private int fieldWidth;

    private Block[][] field;

    private List<Tuple> nextRoundDogCoordinates = new ArrayList<>();

    private ArrayList<Tuple> spawnCoords = new ArrayList<>();

    private Map<BlockType, List<Block>> landmarkMap = new HashMap<>();

    private Map<ActionType, Map<Civilian, Runnable>> actionMap = new HashMap<>();

    private Map<BlockType, Boolean> occupationMap = new HashMap<>();

    public Field(MetaSettings metaSettings) {
        this.metaSettings = metaSettings;

        fieldHeight = metaSettings.getFieldHeight();
        fieldWidth = metaSettings.getFieldWidth();
        field = new Block[fieldHeight][fieldWidth];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = new Block(this, j, i, BlockType.DEFAULT);
            }
        }

        spawnCoords.add(new Tuple(rand.nextInt(fieldWidth - 3) + 1,
                                  rand.nextInt(fieldHeight - 3) + 1));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA() + 1, spawnCoords.get(0).getB()));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA(), spawnCoords.get(0).getB() + 1));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA() + 1, spawnCoords.get(0).getB() + 1));
        for (Tuple coordinate : spawnCoords) {
            field[coordinate.getB()][coordinate.getA()].setBlockType(BlockType.SPAWN);
            addLandmark(BlockType.SPAWN, field[coordinate.getB()][coordinate.getA()]);
        }

        generateLandmark(BlockType.HOSPITAL, metaSettings.getHospitalNum());
        generateLandmark(BlockType.STORE, metaSettings.getStoreNum());
        generateLandmark(BlockType.POLICESTATION, metaSettings.getPoliceStationNum());
        generateLandmark(BlockType.POWERPLANT, metaSettings.getPowerPlantNum());

        for (Block block : landmarkMap.get(BlockType.POLICESTATION)) {
            for (int i = 0; i < 5; i++) {
                block.addGunAmount();
            }
        }

        int soldierNum = metaSettings.getSoldierNum();
        int medicNum = metaSettings.getMedicNum();
        int mechanicNum = metaSettings.getMechanicNum();
        int civilianNum = metaSettings.getCivilianNum();

        int index = rand.nextInt(4);
        while (soldierNum > 0) {
            Block block = field[spawnCoords.get(index).getB()][spawnCoords.get(index).getA()];
            new Soldier(block, metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
            index = (index + 1) % spawnCoords.size();
            soldierNum--;
        }
        
        index = rand.nextInt(4);
        while (medicNum > 0) {
            Block block = field[spawnCoords.get(index).getB()][spawnCoords.get(index).getA()];
            new Medic(block, metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
            index = (index + 1) % spawnCoords.size();
            medicNum--;
        }

        while (mechanicNum > 0) {
            Block block = field[rand.nextInt(fieldHeight)][rand.nextInt(fieldWidth)];
            new Mechanic(block, metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
            mechanicNum--;
        }
        while (civilianNum > 0) {
            Block block = field[rand.nextInt(fieldHeight)][rand.nextInt(fieldWidth)];
            new Civilian(block, metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
            civilianNum--;
        }

        for (BlockType blockType : BlockType.values()) {
            occupationMap.put(blockType, false);
        }

        for (int i = 0; i < 3; i++) {
            updateField();
        }

        generatePath();

        nextRoundDogCoordinates.add(getRandomEdgeCoordinate());
    }

    private void generateLandmark(BlockType blockType, int count) {
        while (count > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (field[randY][randX].getBlockType() == BlockType.DEFAULT) {
                field[randY][randX].setBlockType(blockType);
                addLandmark(blockType, field[randY][randX]);
                count--;
            }
        }
    }

    private void generatePath() {
        List<Block> blockCandidates = new ArrayList<>();
        blockCandidates.add(field[0][0]);
        generatePath(blockCandidates);
    }

     private void generatePath(List<Block> blockCandidates) {
        if (blockCandidates.isEmpty()) return;
    
        Set<Block> nextBlockCandidateSet = new HashSet<>(blockCandidates);
        Random random = new Random();
    
        Block block = blockCandidates.get(random.nextInt(blockCandidates.size()));
        nextBlockCandidateSet.remove(block);

        List<Direction> requirement = new ArrayList<>();
        List<Direction> unexplored = new ArrayList<>();
        List<Direction> notPast = new ArrayList<>();
    
        for (Direction direction : Direction.values()) {
            Block neighbor = block.getNeighborBlock(direction);
            if (neighbor.getPath(direction.getOpposite()).doesExist()) {
                requirement.add(direction);
                continue;
            }
            if (neighbor != block && neighbor.getPathString() == null) {
                unexplored.add(direction);
            }
            if (neighbor != block) {
                notPast.add(direction);
            }
        }
    
        Collections.shuffle(requirement);
        Collections.shuffle(unexplored);
        Collections.shuffle(notPast);
    
        int notPastCount = notPast.isEmpty() ? 0 : random.nextInt(notPast.size()) + 1;
        int requirementCount = requirement.isEmpty() ? 0 : random.nextInt(requirement.size()) + 1;
    
        List<Direction> connectPast = requirement.subList(0, requirementCount);

        Set<Direction> connectFutureSet = new HashSet<>();
        if (!unexplored.isEmpty()) connectFutureSet.add(unexplored.get(0));
        for (Direction direction : notPast.subList(0, notPastCount)) {
            connectFutureSet.add(direction);
        }
        List<Direction> connectFuture = new ArrayList<>(connectFutureSet);

        if (connectFuture.size() == 1 && connectPast.size() == 0) {
            connectFuture = notPast.subList(0, 2);
        }
        if (connectFuture.size() == 0 && connectPast.size() == 1) {;
            connectPast = requirement.subList(0, 2);
        }

        Set<Direction> nextDirections = new HashSet<>(connectFuture);
        nextDirections.retainAll(unexplored);

        Set<Direction> connected = new HashSet<>();
        connected.addAll(connectFuture);
        connected.addAll(connectPast);

        PathCombination pathCombination = new PathCombination(connected);
        block.setPath(pathCombination.getPathType(), pathCombination.getOrientation());
    
        for (Direction direction : nextDirections) {
            Block neighbor = block.getNeighborBlock(direction);
            if (neighbor != block) {
                nextBlockCandidateSet.add(neighbor);
            }
        }
    
        if (!nextBlockCandidateSet.isEmpty()) {
            List<Block> nextBlockCandidates = new ArrayList<>(nextBlockCandidateSet);
            generatePath(nextBlockCandidates);
        }
    }
    

    public void endTurn(int dogIncoming) {
        shootEveryBlock();

        doCivilianActions();

        doDogActions();

        death();

        updateField();
        spawnDogs(dogIncoming);
    }

    private void shootEveryBlock() {
        for (Block[] row : field) {
            for (Block block : row) {
                block.shootDog();
            }
        }
    }
    
    private void doCivilianActions() {
        for (ActionType actionType : ActionType.values()) {
            if (actionType == ActionType.SHOOT) {
                continue;
            }
            Map<Civilian, Runnable> actorMap = actionMap.get(actionType);
            if (actorMap != null) {
                for (Runnable action : actorMap.values()) {
                    action.run();
                }
            }
        }
        
        actionMap.clear();
    
        for (Civilian civilian : getAllCivilians()) {
            if (civilian != null) {
                civilian.nullAction();
            }
        }
    }
    

    private void doDogActions() {
        for (Block[] row : field) {
            for (Block block : row) {
                List<Dog> dogs = block.getAllDogs();
                if (dogs == null) {
                    continue;
                }
                boolean canBiteBarricade = true;
                for (Dog dog : dogs) {
                    canBiteBarricade = !dog.algorithm(canBiteBarricade);
                }
            }
        }
    }

    private int death() {
        int death = 0;
        List<Civilian> comaCivilian = getAllComa();
        if (comaCivilian == null) {return death;}
        for (Civilian civilian : comaCivilian) {
            if (civilian.getComaTime() >= 2) {
                civilian.kill();
            } else {
                civilian.comaTime();
            }
        }
        return death;
    }

    private void updateField() {
        for (Block[] row : field) {
            for (Block block : row) {
                List<Dog> dogs = block.getAllDogs();
                for (Dog dog : dogs) {
                    dog.unActioned();
                }
                block.resetGunToBeLooted();
                occupyAlgorithm(block);
                block.contact();
            }
        }

        for (BlockType blockType : occupationMap.keySet()) {
            if (landmarkMap.get(blockType) == null) {
                occupationMap.put(blockType, false);
                continue;
            }
            for (Block block : landmarkMap.get(blockType)) {
                if (block.getOccupationLevel() > 1) {
                    occupationMap.put(blockType, true);
                    break;
                }
                occupationMap.put(blockType, false);
            }
        }
    }

    private void spawnDogs(int dogIncoming) {
        for (Tuple coordinate : nextRoundDogCoordinates) {
            new Dog(field[coordinate.getB()][coordinate.getA()], metaSettings.getBlockWidth(), metaSettings.getBlockHeight(), metaSettings.getEntitySize());
        }
        nextRoundDogCoordinates.clear();

        while (dogIncoming > 0) {
            nextRoundDogCoordinates.add(getRandomEdgeCoordinate());
            dogIncoming--;
        }
    }


    private Tuple getRandomEdgeCoordinate() {
        Random rand = new Random();
        int rows = field.length;
        int cols = field[0].length;

        int edge = rand.nextInt(4);
        int i = 0, j = 0;

        if (edge == 0) {
            i = 0;
            j = rand.nextInt(cols);
        } else if (edge == 1) {
            i = rows - 1;
            j = rand.nextInt(cols);
        } else if (edge == 2) {
            i = rand.nextInt(rows);
            j = 0;
        } else {
            i = rand.nextInt(rows);
            j = cols - 1;
        }

        return new Tuple(j, i);
    }

    public void occupyAlgorithm(Block block) {
        if (block.getBlockType() == BlockType.POWERPLANT) {
            if (block.getAllEntityOfType(EntityType.MECHANIC).size() > 0) {
                block.occupy();
            } else {
                block.unOccupy();
            }
            return;
        } else if (block.getBlockType() == BlockType.HOSPITAL) {
            if (block.getAllEntityOfType(EntityType.MEDIC).size() > 0) {
                block.occupy();
            } else {
                block.unOccupy();
            }
            return;
        } else if (block.getAllContactedAlive().size() > 0) {
            block.occupy();
        } else {
            block.unOccupy();
        }
    }

    public void addAction(ActionType actionType, Civilian civilian, Runnable actionRunnable) {
        civilian.setAction(actionType, actionRunnable);
        actionMap.computeIfAbsent(actionType, _ -> new HashMap<>()).put(civilian, actionRunnable);
    }

    public void removeAction(ActionType actionType, Civilian civilian, Runnable actionRunnable) {
        civilian.nullAction();
        actionMap.get(actionType).remove(civilian);
        if (actionType == ActionType.SHOOT) {
            civilian.nullShootAt();
        }
    }

    public void printAction() {
        for (ActionType actionType : actionMap.keySet()) {
            System.out.println(actionType);
            
            Map<Civilian, Runnable> actorMap = actionMap.get(actionType);
            for (Map.Entry<Civilian, Runnable> entry : actorMap.entrySet()) {
                Civilian civilian = entry.getKey();
                Runnable action = entry.getValue();
                System.out.println(civilian + " is doing " + action);
            }
            System.out.println();
        }
    }

    private void addLandmark(BlockType blockType, Block block) {
        landmarkMap.computeIfAbsent(blockType, _ -> new ArrayList<>()).add(block);
    }

    public Block getNextBlock(Block block, Direction direction) {
        int blockX = block.getCoordinate().getA();
        int blockY = block.getCoordinate().getB();
        try {
            return field[blockY + direction.getOffset().getB()][blockX + direction.getOffset().getA()];
        } catch (Exception e) {
            // System.out.println(String.format("There is no further block %sward", direction.toString().toLowerCase()));
        }
        return block;
    }

    public Block getBlock(Tuple coordinate) {
        return field[coordinate.getB()][coordinate.getA()];
    }

    public List<Block> getManhattanBlocks(Tuple coordinate, int distance) {
        int x1 = coordinate.getA();
        int y1 = coordinate.getB();
        List<Block> manhattanBlocks = new ArrayList<>();
        for (int y2 = 0; y2 < field.length; y2++) {
            for (int x2 = 0; x2 < field[0].length; x2++) {
                if (Math.abs(x1 - x2) + Math.abs(y1 - y2) == distance) {
                    manhattanBlocks.add(field[y2][x2]);
                }
            }
        }
        return manhattanBlocks;
    }

    public ArrayList<Tuple> getSpawnCoords() {
        return spawnCoords;
    }

    public List<Block> getOccupiedHospitals() {
        List<Block> occupiedHospitals = new ArrayList<>();
        for (Block block : landmarkMap.get(BlockType.HOSPITAL)) {
            if (block.getOccupationLevel() >= 2) {
                occupiedHospitals.add(block);
            }
        }
        return occupiedHospitals;
    }

    public List<Block> getOccupiedLandmarks() {
        List<Block> occupiedLandmarks = new ArrayList<>();
        for (BlockType blockType : landmarkMap.keySet()) {
            for (Block block : landmarkMap.get(blockType)) {
                if (block.getOccupationLevel() >= 2) {
                    occupiedLandmarks.add(block);
                }
            }
        }
        return occupiedLandmarks;
    }

    public List<Entity> getAllEntityOfType(EntityType entityType) {
        List<Entity> entities = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                entities.addAll(block.getAllEntityOfType(entityType));
            }
        }
        return entities;
    }

    public List<Entity> getAllEntityOfType(EntityType entityType, Vitality vitality) {
        List<Entity> entities = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                List<Entity> getAll = block.getAllEntityOfType(entityType);
                for (Entity entity : getAll) {
                    Civilian civilian = (Civilian) entity;
                    if (civilian.getVitality() == vitality) {
                        entities.add(entity);
                    }
                }
            }
        }
        return entities;
    }

    public List<Civilian> getAllComa() {
        List<Civilian> civilians = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                civilians.addAll(block.getAllComa());
            }
        }
        return civilians;
    }

    public List<Civilian> getAllCivilians() {
        List<Civilian> civilians = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                civilians.addAll(block.getAllCivilians());
            }
        }
        return civilians;
    }

    public List<Dog> getAllDog() {
        List<Dog> dogs = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                dogs.addAll(block.getAllDogs());
            }
        }
        return dogs;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public Map<BlockType, Boolean> getOccupationMap() {
        return occupationMap;
    }

    public List<Tuple> getNextRoundDogCoordinates() {
        return nextRoundDogCoordinates;
    }

    public void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
        for (int j = 0; j < field[0].length * 2; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
