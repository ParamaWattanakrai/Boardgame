package src.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import src.entities.*;
import src.utils.*;

public class Field {
    Random rand = new Random();
    int fieldHeight;
    int fieldWidth;

    private PathType[] allPathTypes = PathType.values();
    private Block[][] field;

    private ArrayList<Tuple> spawnCoords = new ArrayList<>();

    private HashMap<BlockType, List<Block>> landmarkMap = new HashMap<>();

    private HashMap<CivilianAction, List<Runnable>> actionMap = new HashMap<>();

    public Field(MetaSettings metaSettings) {
        fieldHeight = metaSettings.getFieldHeight();
        fieldWidth = metaSettings.getFieldWidth();
        field = new Block[fieldHeight][fieldWidth];
        BlockType[][] typeField = new BlockType[fieldHeight][fieldWidth];

        spawnCoords.add(new Tuple(rand.nextInt(fieldWidth - 2) + 1,
                                  rand.nextInt(fieldHeight - 2) + 1));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA() + 1, spawnCoords.get(0).getB()));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA(), spawnCoords.get(0).getB() + 1));
        spawnCoords.add(new Tuple(spawnCoords.get(0).getA() + 1, spawnCoords.get(0).getB() + 1));
        for (Tuple coordinate : spawnCoords) {
            addLandmark(BlockType.SPAWN, field[coordinate.getB()][coordinate.getA()]);
            typeField[coordinate.getB()][coordinate.getA()] = BlockType.SPAWN;
        }

        int remainingHospital = metaSettings.getHospitalNum();
        int remainingStore = metaSettings.getStoreNum();
        int remainingPoliceStation = metaSettings.getPoliceStationNum();
        int remainingPowerPlant = metaSettings.getPowerPlantNum();

        while (remainingHospital > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                addLandmark(BlockType.HOSPITAL, field[randY][randX]);
                typeField[randY][randX] = BlockType.HOSPITAL;
                remainingHospital--;
            }
        }
        while (remainingStore > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                addLandmark(BlockType.STORE, field[randY][randX]);
                typeField[randY][randX] = BlockType.STORE;
                remainingStore--;
            }
        }
        while (remainingPoliceStation > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                addLandmark(BlockType.POLICESTATION, field[randY][randX]);
                typeField[randY][randX] = BlockType.POLICESTATION;
                remainingPoliceStation--;
            }
        }
        while (remainingPowerPlant > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                addLandmark(BlockType.POWERPLANT, field[randY][randX]);
                typeField[randY][randX] = BlockType.POWERPLANT;
                remainingPowerPlant--;
            }
        }

        int soldierNum = metaSettings.getSoldierNum();
        int medicNum = metaSettings.getMedicNum();
        int mechanicNum = metaSettings.getMechanicNum();
        int civilianNum = metaSettings.getCivilianNum();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                BlockType blockType = typeField[i][j] == null ? BlockType.DEFAULT : typeField[i][j];
                field[i][j] = getRandomBlock(j, i, blockType, allPathTypes);
            }
        }

        int index = rand.nextInt(4);
        while (soldierNum > 0) {
            Block block = field[spawnCoords.get(index).getB()][spawnCoords.get(index).getA()];
            new Soldier(block);
            index = (index + 1) % spawnCoords.size();
            soldierNum--;
        }
        
        index = rand.nextInt(4);
        while (medicNum > 0) {
            Block block = field[spawnCoords.get(index).getB()][spawnCoords.get(index).getA()];
            new Medic(block);
            index = (index + 1) % spawnCoords.size();
            medicNum--;
        }

        while (mechanicNum > 0) {
            Block block = field[rand.nextInt(fieldHeight)][rand.nextInt(fieldWidth)];
            new Mechanic(block);
            mechanicNum--;
        }
        while (civilianNum > 0) {
            Block block = field[rand.nextInt(fieldHeight)][rand.nextInt(fieldWidth)];
            new Civilian(block);
            civilianNum--;
        }
    }

    public void endTurn() {
        for (Block[] row : field) {
            for (Block block : row) {
                System.out.println("Dog Shot: " + block.shootDog());
            }
        }

        for (CivilianAction civilianAction : CivilianAction.values()) {
            List<Runnable> actionRunnables = actionMap.get(civilianAction);
            if (actionRunnables == null) {
                continue;
            }
            for (Runnable runnable : actionRunnables) {
                runnable.run();
            }
        }
    }

    public void occupyAlgorithm(Block block) {
        if (block.getBlockType() == BlockType.POWERPLANT) {
            if (block.getAllEntity(EntityType.MECHANIC).size() > 0) {
                block.occupy();
                return;
            }
        } else if (block.getBlockType() == BlockType.HOSPITAL) {
            if (block.getAllEntity(EntityType.MEDIC).size() > 0) {
                block.occupy();
                return;
            }
        } else if (block.getAllAlive().size() > 0) {
            block.occupy();
        } else {
            block.unOccupy();
        }
    }

    public void addAction(CivilianAction action, Runnable actionRunnable) {
        actionMap.computeIfAbsent(action, _ -> new ArrayList<>()).add(actionRunnable);
    }

    public void removeAction(CivilianAction action, Runnable actionRunnable) {
        actionMap.get(action).remove(actionRunnable);
    }

    public void addLandmark(BlockType blockType, Block block) {
        landmarkMap.computeIfAbsent(blockType, _ -> new ArrayList<>()).add(block);
    }

    public void removeLandmark(BlockType blockType, Block block) {
        landmarkMap.get(blockType).remove(block);
    }

    public Block getRandomBlock(int x, int y, BlockType blockType, PathType[] possiblePathTypes) {
        return new Block(this, x, y, blockType, possiblePathTypes[rand.nextInt(possiblePathTypes.length)], rand.nextInt(4), 0);
    }

    public Block getNextBlock(Block block, Direction direction) {
        int blockX = block.getCoordinate().getA();
        int blockY = block.getCoordinate().getB();
        try {
            return field[blockX + direction.getOffset().getB()][blockY + direction.getOffset().getA()];
        } catch (Exception e) {
            System.out.println(String.format("There is no further block %sward", direction.toString().toLowerCase()));
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

    public List<Entity> getAllEntity(EntityType entityType) {
        List<Entity> entities = new ArrayList<>();
        for (Block[] row : field) {
            for (Block block : row) {
                entities.addAll(block.getAllEntity(entityType));
            }
        }
        return entities;
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

    public void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print("(" + field[i][j].getBlockTypeString() + field[i][j].getPathString() + field[i][j].getAllCivilians().size() + ")");
            }
            System.out.println();
        }
        for (int j = 0; j < field[0].length * 2; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
