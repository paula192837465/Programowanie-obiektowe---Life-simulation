import agh.cs.lab1.Vector2d;
import org.junit.Test;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

public class Vector2DTest {

    Vector2d vec = new Vector2d(1, 2);
    Vector2d vec2 = new Vector2d(-2, 1);
    Vector2d vec3 = new Vector2d(1,5);
    Vector2d vec4 = new Vector2d(1,5);

    @Test
    public void toStringTest() {
        assertEquals("(1,2)",vec.toString());
        assertEquals("(-2,1)",vec2.toString());
        assertEquals("(1,5)",vec4.toString());
    }

    @Test
    public void precedesTest() {
        assertEquals(false, vec.precedes(vec2));
        assertEquals(true, vec.precedes(vec3));
    }

    @Test
    public void followsTest() {
        assertEquals(true, vec.follows(vec2));
        assertEquals(true, vec.precedes(vec3));
    }

    @Test
    public void upperRightTest() {
        assertEquals(new Vector2d(1,2), vec.upperRight(vec2));
        assertEquals(new Vector2d(1,5), vec.upperRight(vec3));
        assertEquals(new Vector2d(1,5), vec2.upperRight(vec3));
    }

    @Test
    public void lowerLeftTest() {
        assertEquals(new Vector2d(-2,1), vec.lowerLeft(vec2));
        assertEquals(new Vector2d(1,2), vec.lowerLeft(vec3));
        assertEquals(new Vector2d(-2,1), vec3.lowerLeft(vec2));

    }

    @Test
    public void addTest() {
        assertEquals(new Vector2d(-1, 3), vec.add(vec2));
        assertEquals(new Vector2d(2, 7), vec.add(vec3));
        assertEquals(new Vector2d(-1, 6), vec3.add(vec2));
    }

    @Test
    public void subtractTest() {
        assertEquals(new Vector2d(3, 1), vec.subtract(vec2));
        assertEquals(new Vector2d(0, -3), vec.subtract(vec3));
        assertEquals(new Vector2d(3, 4), vec3.subtract(vec2));
    }

    @Test
    public void oppositeTest() {
        assertEquals(new Vector2d(-1, -2), vec.opposite());
        assertEquals(new Vector2d(2, -1), vec2.opposite());
        assertEquals(new Vector2d(-1, -5), vec3.opposite());
    }

    @Test
    public void equalsTest() {
        assertEquals(false, vec.equals(vec2));
        assertEquals(true, vec3.equals(vec4));
    }
}
