public class Field {
    private Block[][] Field = new Block[5][5];

    public Field() {

    }

    public Block getNextBlock(Block block, Direction direction) {
        int blockX = block.getCoordinate().getA();
        int blockY = block.getCoordinate().getB();
        try {
            return Field[blockX + direction.getOffset().getA()][blockY + direction.getOffset().getB()];
        } catch (Exception e) {
            System.out.println(String.format("There is no further block %sward", direction.toString().toLowerCase()));
        }
        return block;
    }

    public Block getBlock(Tuple coordinate) {
        return Field[coordinate.getA()][coordinate.getB()];
    }
}
