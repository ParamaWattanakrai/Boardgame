package utils;

public enum Direction {
    NORTH {
        @Override
        public Direction getOpposite() {
            return SOUTH;
        }
        @Override
        public Tuple getOffset() {
            return new Tuple(0, 1);
        }
    },
    EAST {
        @Override
        public Direction getOpposite() {
            return WEST;
        }
        @Override
        public Tuple getOffset() {
            return new Tuple(1, 0);
        }
    },
    SOUTH {
        @Override
        public Direction getOpposite() {
            return NORTH;
        }
        @Override
        public Tuple getOffset() {
            return new Tuple(0, -1);
        }
    },
    WEST {
        @Override
        public Direction getOpposite() {
            return EAST;
        }
        @Override
        public Tuple getOffset() {
            return new Tuple(-1, 0);
        }
    };
    
    public abstract Direction getOpposite();
    public abstract Tuple getOffset();
}