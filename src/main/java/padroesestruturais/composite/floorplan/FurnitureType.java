package padroesestruturais.composite.floorplan;

public enum FurnitureType {
    CHAIR('C', 150),
    TABLE('T', 800),
    SOFA('S', 2500),
    BED('B', 1800),
    LAMP('L', 120),
    WARDROBE('W', 1200);

    private final char symbol;
    private final int value;

    FurnitureType(char symbol, int value) {
        this.symbol = symbol;
        this.value = value;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }
}
