package padroesestruturais.composite.floorplan;

public interface RoomElement {

    boolean canMove(Direction direction);

    void move(Direction direction);

    String getPosition();

    int getValue();

    String draw();
}
