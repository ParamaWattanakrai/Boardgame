package map;

import utils.*;

public class Field {
    PathType[] allPathTypes = PathType.values();
    private Block[][] field = new Block[5][5];

    public Field(int width, int height) {
        field = new Block[height][width];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = getRandomBlock(j, i, allPathTypes);
            }
        }
    }

    public Block getRandomBlock(int x, int y, PathType[] possiblePathTypes) {
        return new Block(this, x, y, BlockType.DEFAULT, possiblePathTypes[(int) (Math.random() * possiblePathTypes.length)], (int) (Math.random() * 4));
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
        return field[coordinate.getA()][coordinate.getB()];
    }

    public void printField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j].getBlockTypeString() + field[i][j].getPathString() + field[i][j].getPopulation());
            }
            System.out.println();
        }
        for (int j = 0; j < field[0].length * 2; j++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
