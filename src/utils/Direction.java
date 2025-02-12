package utils;

public enum Direction {
    NORTH {
        @Override
        public Tuple getOffset() {
            return new Tuple(0, 1);
        }
    },
    EAST {
        @Override
        public Tuple getOffset() {
            return new Tuple(1, 0);
        }
    },
    SOUTH {
        @Override
        public Tuple getOffset() {
            return new Tuple(0, -1);
        }
    },
    WEST {
        @Override
        public Tuple getOffset() {
            return new Tuple(-1, 0);
        }
    };
    
    public abstract Tuple getOffset();
}