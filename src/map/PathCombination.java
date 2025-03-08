package src.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import src.utils.Direction;

public class PathCombination {
    private PathType pathType;
    private int orientation;

    public PathCombination(Set<Direction> directionSet) {
        List<Direction> directions = new ArrayList<>(directionSet);

        directions.sort(Comparator.comparing(Direction::ordinal));

        String directionString = "";
        for (Direction direction : directions) {
            directionString += direction.getChar();
        }

        switch (directionString) {
            case "NESW":
                pathType = PathType.FOURWAY;
                orientation = 0;
                break;
            case "NS":
                pathType = PathType.STRAIGHT;
                orientation = 0;
                break;
            case "EW":
                pathType = PathType.STRAIGHT;
                orientation = 1;
                break;
            case "NE":
                pathType = PathType.CURVED;
                orientation = 0;
                break;
            case "ES":
                pathType = PathType.CURVED;
                orientation = 1;
                break;
            case "SW":
                pathType = PathType.CURVED;
                orientation = 2;
                break;
            case "NW":
                pathType = PathType.CURVED;
                orientation = 3;
                break;
            case "NEW":
                pathType = PathType.THREEWAY;
                orientation = 0;
                break;
            case "NES":
                pathType = PathType.THREEWAY;
                orientation = 1;
                break;
            case "ESW":
                pathType = PathType.THREEWAY;
                orientation = 2;
                break;
            case "NSW":
                pathType = PathType.THREEWAY;
                orientation = 3;
                break;
            default:
                System.out.println("WHAT=============================================================");
                break;
        }
    }

    public PathType getPathType() { return pathType; }
    public int getOrientation() { return orientation; }
}
