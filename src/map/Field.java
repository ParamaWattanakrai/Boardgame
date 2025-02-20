package src.map;

import java.util.ArrayList;
import java.util.Random;

import src.entities.*;
import src.utils.*;

public class Field {
    Random rand = new Random();
    int fieldHeight;
    int fieldWidth;

    PathType[] allPathTypes = PathType.values();
    private Block[][] field;

    ArrayList<Tuple> spawnCoords = new ArrayList<>();
    ArrayList<Tuple> hospitalCoords = new ArrayList<>();
    ArrayList<Tuple> storeCoords = new ArrayList<>();
    ArrayList<Tuple> policeStationCoords = new ArrayList<>();
    ArrayList<Tuple> powerPlantCoords = new ArrayList<>();

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
                hospitalCoords.add(new Tuple(randX, randY));
                typeField[randY][randX] = BlockType.HOSPITAL;
                remainingHospital--;
            }
        }
        while (remainingStore > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                hospitalCoords.add(new Tuple(randX, randY));
                typeField[randY][randX] = BlockType.STORE;
                remainingStore--;
            }
        }
        while (remainingPoliceStation > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                hospitalCoords.add(new Tuple(randX, randY));
                typeField[randY][randX] = BlockType.POLICESTATION;
                remainingPoliceStation--;
            }
        }
        while (remainingPowerPlant > 0) {
            int randX = rand.nextInt(fieldWidth);
            int randY = rand.nextInt(fieldHeight);
            if (typeField[randY][randX] == null) {
                hospitalCoords.add(new Tuple(randX, randY));
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

    public Block getRandomBlock(int x, int y, BlockType blockType, PathType[] possiblePathTypes) {
        return new Block(this, x, y, blockType, possiblePathTypes[rand.nextInt(possiblePathTypes.length)], rand.nextInt(5), 0);
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

    public ArrayList<Tuple> getSpawnCoords() {
        return spawnCoords;
    }

    public void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j].getBlockTypeString() + field[i][j].getPathString() + field[i][j].getPopulation().size());
            }
            System.out.println();
        }
        for (int j = 0; j < field[0].length * 2; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
