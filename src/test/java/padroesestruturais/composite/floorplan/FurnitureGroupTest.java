package padroesestruturais.composite.floorplan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FurnitureGroupTest {

    private FurnitureGroup createDiningSet() {
        FurnitureGroup diningSet = new FurnitureGroup("Dining set");
        diningSet.add(new Furniture(FurnitureType.TABLE, "c4"));
        diningSet.add(new Furniture(FurnitureType.CHAIR, "c3"));
        diningSet.add(new Furniture(FurnitureType.CHAIR, "c5"));
        diningSet.add(new Furniture(FurnitureType.CHAIR, "b4"));
        diningSet.add(new Furniture(FurnitureType.CHAIR, "d4"));
        return diningSet;
    }

    private FurnitureGroup createLivingRoom(String wardrobeSquare) {
        FurnitureGroup sofaSet = new FurnitureGroup("Sofa set");
        sofaSet.add(new Furniture(FurnitureType.SOFA, "f2"));
        sofaSet.add(new Furniture(FurnitureType.LAMP, "g2"));

        FurnitureGroup livingRoom = new FurnitureGroup("Living room");
        livingRoom.add(createDiningSet());
        livingRoom.add(sofaSet);
        livingRoom.add(new Furniture(FurnitureType.WARDROBE, wardrobeSquare));
        return livingRoom;
    }

    @Test
    void shouldReturnTheGroupPosition() {
        assertEquals("Dining set[c4, c3, c5, b4, d4]", createDiningSet().getPosition());
    }

    @Test
    void shouldSumTheValues() {
        assertEquals(1400, createDiningSet().getValue()); // 800 + 4 * 150
    }

    @Test
    void shouldDrawTheGroup() {
        assertEquals("Dining set: T C C C C", createDiningSet().draw());
    }

    @Test
    void shouldMoveEveryPieceOfTheGroup() {
        FurnitureGroup diningSet = createDiningSet();
        diningSet.move(Direction.NORTH);
        assertEquals("Dining set[c5, c4, c6, b5, d5]", diningSet.getPosition());
    }

    @Test
    void shouldReturnEmptyGroup() {
        FurnitureGroup empty = new FurnitureGroup("Empty");
        assertEquals("Empty[]", empty.getPosition());
        assertEquals(0, empty.getValue());
        assertEquals("Empty: ", empty.draw());
        assertTrue(empty.canMove(Direction.NORTH));
    }

    @Test
    void shouldRemoveFurnitureFromTheGroup() {
        FurnitureGroup bedroom = new FurnitureGroup("Bedroom");
        Furniture bed = new Furniture(FurnitureType.BED, "g8");
        Furniture lamp = new Furniture(FurnitureType.LAMP, "h8");
        bedroom.add(bed);
        bedroom.add(lamp);

        bedroom.remove(bed);

        assertEquals("Bedroom[h8]", bedroom.getPosition());
        assertEquals(120, bedroom.getValue());
    }

    @Test
    void shouldTreatFurnitureAndGroupTheSameWay() {
        RoomElement lamp = new Furniture(FurnitureType.LAMP, "a1");
        RoomElement diningSet = createDiningSet();

        for (RoomElement element : new RoomElement[]{lamp, diningSet}) {
            element.move(Direction.EAST);
        }

        assertEquals("b1", lamp.getPosition());
        assertEquals("Dining set[d4, d3, d5, c4, e4]", diningSet.getPosition());
    }

    @Test
    void shouldSupportNestedGroups() {
        FurnitureGroup livingRoom = createLivingRoom("i1");

        assertEquals("Living room[Dining set[c4, c3, c5, b4, d4], Sofa set[f2, g2], i1]", livingRoom.getPosition());
        assertEquals(5220, livingRoom.getValue()); // 1400 + 2500 + 120 + 1200
        assertEquals("Living room: Dining set: T C C C C Sofa set: S L W", livingRoom.draw());

        livingRoom.move(Direction.WEST);

        assertEquals("Living room[Dining set[b4, b3, b5, a4, c4], Sofa set[e2, f2], h1]", livingRoom.getPosition());
    }

    @Test
    void shouldNotMoveTheGroupWhenOnePieceWouldLeave() {
        FurnitureGroup corner = new FurnitureGroup("Corner");
        corner.add(new Furniture(FurnitureType.CHAIR, "b1"));
        corner.add(new Furniture(FurnitureType.LAMP, "a1"));

        assertFalse(corner.canMove(Direction.WEST));
        assertThrows(IllegalStateException.class, () -> corner.move(Direction.WEST));
        assertEquals("Corner[b1, a1]", corner.getPosition());
        assertTrue(corner.canMove(Direction.EAST));
    }

    @Test
    void shouldNotMoveANestedGroupWhenADeepPieceWouldLeave() {
        FurnitureGroup livingRoom = createLivingRoom("j1");

        assertThrows(IllegalStateException.class, () -> livingRoom.move(Direction.EAST));
        assertEquals("Living room[Dining set[c4, c3, c5, b4, d4], Sofa set[f2, g2], j1]", livingRoom.getPosition());
    }
}
