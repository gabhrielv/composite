package padroesestruturais.composite.floorplan;

public class Furniture implements RoomElement {

    private final FurnitureType type;
    private char column; // 'a'..'j'
    private int row;     // 1..10

    public Furniture(FurnitureType type, String square) {
        if (square == null || !square.matches("[a-j](10|[1-9])")) {
            throw new IllegalArgumentException("Invalid square: " + square);
        }
        this.type = type;
        this.column = square.charAt(0);
        this.row = Integer.parseInt(square.substring(1));
    }

    @Override
    public boolean canMove(Direction direction) {
        return isInsideFloorPlan(nextColumn(direction), nextRow(direction));
    }

    @Override
    public void move(Direction direction) {
        if (!canMove(direction)) {
            throw new IllegalStateException("Move leaves the floor plan: " + getPosition() + " " + direction);
        }
        column = nextColumn(direction);
        row = nextRow(direction);
    }

    @Override
    public String getPosition() {
        return "" + column + row;
    }

    @Override
    public int getValue() {
        return type.getValue();
    }

    @Override
    public String draw() {
        return String.valueOf(type.getSymbol());
    }

    public FurnitureType getType() {
        return type;
    }

    private char nextColumn(Direction direction) {
        switch (direction) {
            case EAST: return (char) (column + 1);
            case WEST: return (char) (column - 1);
            default:   return column;
        }
    }

    private int nextRow(Direction direction) {
        switch (direction) {
            case NORTH: return row + 1;
            case SOUTH: return row - 1;
            default:    return row;
        }
    }

    private static boolean isInsideFloorPlan(char column, int row) {
        return column >= 'a' && column <= 'j' && row >= 1 && row <= 10;
    }
}
