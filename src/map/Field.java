package map;

import utils.*;
import entities.*;

public class Field {
    PathType[] pathTypes = PathType.values();
    private Block[][] Field = new Block[5][5];

    public Field() {
        for (int i = 0; i < Field.length; i++) {
            for (int j = 0; j < Field[0].length; j++) {
                Field[i][j] = new Block(j, i, pathTypes[(int) (Math.random() * pathTypes.length)], (int) (Math.random() * 4));
            }
        }
    }

    public Block getNextBlock(Block block, Direction direction) {
        int blockX = block.getCoordinate().getA();
        int blockY = block.getCoordinate().getB();
        try {
            return Field[blockX + direction.getOffset().getB()][blockY + direction.getOffset().getA()];
        } catch (Exception e) {
            System.out.println(String.format("There is no further block %sward", direction.toString().toLowerCase()));
        }
        return block;
    }

    public Block getBlock(Tuple coordinate) {
        return Field[coordinate.getA()][coordinate.getB()];
    }

    public void printField() {
        for (int i = 0; i < Field.length; i++) {
            for (int j = 0; j < Field[0].length; j++) {
                System.out.print(Field[j][i].getPathString());
            }
            System.out.println();
        }
    }
}
