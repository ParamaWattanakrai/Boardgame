package src.enums;

public enum Direction {
    NORTH {
        @Override
        public Direction getOpposite() { return SOUTH; }
        @Override
        public Tuple getOffset() { return new Tuple(0, -1); }
        @Override
        public char getChar() { return 'N'; }
    },
    EAST {
        @Override
        public Direction getOpposite() { return WEST; }
        @Override
        public Tuple getOffset() { return new Tuple(1, 0); }
        @Override
        public char getChar() { return 'E'; }
    },
    SOUTH {
        @Override
        public Direction getOpposite() { return NORTH; }
        @Override
        public Tuple getOffset() { return new Tuple(0, 1); }
        @Override
        public char getChar() { return 'S'; }
    },
    WEST {
        @Override
        public Direction getOpposite() { return EAST; }
        @Override
        public Tuple getOffset() { return new Tuple(-1, 0); }
        @Override
        public char getChar() { return 'W'; }
    };
    
    public abstract Direction getOpposite();
    public abstract Tuple getOffset();
    public abstract char getChar();

    public static Direction offsetToDirection(Tuple offset) {
        int x = offset.getA();
        int y = offset.getB();
        if (x == 0 && y == -1) {
            return NORTH;
        } else if (x == 1 && y == 0) {
            return EAST;
        } else if (x == 0 && y == 1) {
            return SOUTH;
        } else if (x == -1 && y == 0) {
            return WEST;
        } else {
            return null;
        }
    }
}