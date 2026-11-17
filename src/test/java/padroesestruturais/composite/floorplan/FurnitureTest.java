package padroesestruturais.composite.floorplan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FurnitureTest {

    @Test
    void shouldReturnInitialPosition() {
        Furniture table = new Furniture(FurnitureType.TABLE, "c4");
        assertEquals("c4", table.getPosition());
    }

    @Test
    void shouldMoveNorth() {
        Furniture table = new Furniture(FurnitureType.TABLE, "c4");
        table.move(Direction.NORTH);
        assertEquals("c5", table.getPosition());
    }

    @Test
    void shouldMoveSouth() {
        Furniture table = new Furniture(FurnitureType.TABLE, "c4");
        table.move(Direction.SOUTH);
        assertEquals("c3", table.getPosition());
    }

    @Test
    void shouldMoveEastAndWest() {
        Furniture sofa = new Furniture(FurnitureType.SOFA, "d4");
        sofa.move(Direction.WEST);
        assertEquals("c4", sofa.getPosition());
        sofa.move(Direction.EAST);
        sofa.move(Direction.EAST);
        assertEquals("e4", sofa.getPosition());
    }

    @Test
    void shouldThrowWhenLeavingTheFloorPlan() {
        Furniture lamp = new Furniture(FurnitureType.LAMP, "a10");
        assertFalse(lamp.canMove(Direction.NORTH));
        assertThrows(IllegalStateException.class, () -> lamp.move(Direction.NORTH));
        assertThrows(IllegalStateException.class, () -> lamp.move(Direction.WEST));
        assertEquals("a10", lamp.getPosition());
    }

    @Test
    void shouldThrowForInvalidSquare() {
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, "k1"));
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, "a11"));
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, "a0"));
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, "C4"));
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, ""));
        assertThrows(IllegalArgumentException.class, () -> new Furniture(FurnitureType.BED, null));
    }

    @Test
    void shouldAcceptTheLastSquare() {
        Furniture bed = new Furniture(FurnitureType.BED, "j10");
        assertEquals("j10", bed.getPosition());
        assertFalse(bed.canMove(Direction.NORTH));
        assertTrue(bed.canMove(Direction.SOUTH));
    }

    @Test
    void shouldReturnTheValueOfEachType() {
        assertEquals(150, new Furniture(FurnitureType.CHAIR, "a1").getValue());
        assertEquals(800, new Furniture(FurnitureType.TABLE, "b1").getValue());
        assertEquals(2500, new Furniture(FurnitureType.SOFA, "c1").getValue());
        assertEquals(1800, new Furniture(FurnitureType.BED, "d1").getValue());
        assertEquals(120, new Furniture(FurnitureType.LAMP, "e1").getValue());
        assertEquals(1200, new Furniture(FurnitureType.WARDROBE, "f1").getValue());
    }

    @Test
    void shouldDrawTheSymbol() {
        assertEquals("S", new Furniture(FurnitureType.SOFA, "g1").draw());
        assertEquals("W", new Furniture(FurnitureType.WARDROBE, "h1").draw());
    }
}
