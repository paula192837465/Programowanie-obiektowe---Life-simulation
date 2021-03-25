package agh.cs.lab1;

import java.awt.*;

public class Grass extends AbstractWorldMapElement{

    public Grass(Vector2d position)
    {
        this.position = position;
    }

    public String toString()
    {
        return " * ";
    }

    public Color toColor() {
        return new Color(67, 222, 31);
    }
}
