package agh.cs.lab1;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public String toString()
    {
        switch(this)
        {
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case EAST: return "Wschód";
            case WEST: return "Zachód";
            case SOUTH_EAST: return "Południowy-Wschód";
            case SOUTH_WEST: return "Południowy-Zachód";
            case NORTH_EAST: return "Północny-Wschód";
            case NORTH_WEST: return "Północny-Zachód";
        }
        return null;
    }

    public MapDirection next()
    {
        switch(this)
        {
            case EAST: return SOUTH_EAST;
            case SOUTH: return SOUTH_WEST;
            case WEST: return NORTH_WEST;
            case NORTH: return NORTH_EAST;
            case SOUTH_EAST: return SOUTH;
            case SOUTH_WEST: return WEST;
            case NORTH_EAST: return EAST;
            case NORTH_WEST: return NORTH;
        }
        return null;
    }

    public MapDirection previous()
    {
        switch(this)
        {
            case EAST: return NORTH_EAST;
            case SOUTH: return SOUTH_EAST;
            case WEST: return SOUTH_WEST;
            case NORTH: return NORTH_WEST;
            case SOUTH_EAST: return EAST;
            case SOUTH_WEST: return SOUTH;
            case NORTH_EAST: return NORTH;
            case NORTH_WEST: return WEST;
        }
        return null;
    }

    public Vector2d toUnitVector()
    {
        switch (this)
        {
            case EAST: return new Vector2d(1,0);
            case SOUTH:  return new Vector2d(0,-1);
            case WEST:  return new Vector2d(-1,0);
            case NORTH: return new Vector2d(0,1);
            case SOUTH_EAST: return new Vector2d(1,-1);
            case SOUTH_WEST: return new Vector2d(-1,-1);
            case NORTH_EAST: return new Vector2d(1,1);
            case NORTH_WEST: return new Vector2d(-1,1);
        }
        return null;
    }
}
