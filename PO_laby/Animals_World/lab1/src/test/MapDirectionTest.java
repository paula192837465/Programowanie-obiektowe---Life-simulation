import agh.cs.lab1.MapDirection;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MapDirectionTest {

    @Test
    public void test_next()
    {
        MapDirection dir = MapDirection.EAST;
        assertEquals(dir.next(),MapDirection.SOUTH_EAST);

        MapDirection dir2 = MapDirection.SOUTH_EAST;
        assertEquals(dir2.next(),MapDirection.SOUTH);

        MapDirection dir3 = MapDirection.SOUTH;
        assertEquals(dir3.next(),MapDirection.SOUTH_WEST);

        MapDirection dir4 = MapDirection.SOUTH_EAST;
        assertEquals(dir4.next(),MapDirection.SOUTH);

        MapDirection dir5 = MapDirection.WEST;
        assertEquals(dir5.next(),MapDirection.NORTH_WEST);

        MapDirection dir6 = MapDirection.NORTH_WEST;
        assertEquals(dir6.next(),MapDirection.NORTH);

        MapDirection dir7 = MapDirection.NORTH;
        assertEquals(dir7.next(),MapDirection.NORTH_EAST);

        MapDirection dir8 = MapDirection.NORTH_EAST;
        assertEquals(dir8.next(),MapDirection.EAST);
    }

    @Test
    public void test_previous()
    {
        MapDirection dir = MapDirection.EAST;
        assertEquals(dir.previous(),MapDirection.NORTH_EAST);

        MapDirection dir2 = MapDirection.SOUTH;
        assertEquals(dir2.previous(),MapDirection.SOUTH_EAST);

        MapDirection dir3 = MapDirection.WEST;
        assertEquals(dir3.previous(),MapDirection.SOUTH_WEST);

        MapDirection dir4 = MapDirection.NORTH;
        assertEquals(dir4.previous(),MapDirection.NORTH_WEST);

        MapDirection dir5 = MapDirection.NORTH_EAST;
        assertEquals(dir5.previous(),MapDirection.NORTH);

        MapDirection dir6 = MapDirection.NORTH_WEST;
        assertEquals(dir6.previous(),MapDirection.WEST);

        MapDirection dir7 = MapDirection.SOUTH_EAST;
        assertEquals(dir7.previous(),MapDirection.EAST);

        MapDirection dir8 = MapDirection.SOUTH_WEST;
        assertEquals(dir8.previous(),MapDirection.SOUTH);
    }
}
