package padroesestruturais.composite.floorplan;

import java.util.ArrayList;
import java.util.List;

public class FurnitureGroup implements RoomElement {

    private final String name;
    private final List<RoomElement> elements = new ArrayList<>();

    public FurnitureGroup(String name) {
        this.name = name;
    }

    public void add(RoomElement element) {
        elements.add(element);
    }

    public void remove(RoomElement element) {
        elements.remove(element);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean canMove(Direction direction) {
        for (RoomElement element : elements) {
            if (!element.canMove(direction)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void move(Direction direction) {
        if (!canMove(direction)) {
            throw new IllegalStateException("Move leaves the floor plan: " + name + " " + direction);
        }
        for (RoomElement element : elements) {
            element.move(direction);
        }
    }

    @Override
    public String getPosition() {
        List<String> positions = new ArrayList<>();
        for (RoomElement element : elements) {
            positions.add(element.getPosition());
        }
        return name + "[" + String.join(", ", positions) + "]";
    }

    @Override
    public int getValue() {
        int total = 0;
        for (RoomElement element : elements) {
            total += element.getValue();
        }
        return total;
    }

    @Override
    public String draw() {
        List<String> drawings = new ArrayList<>();
        for (RoomElement element : elements) {
            drawings.add(element.draw());
        }
        return name + ": " + String.join(" ", drawings);
    }
}
