package opponents;

public enum Direction
{
    UP(0, -1), LEFT(-1, 0), DOWN(0, 1), RIGHT(1, 0);

    private int directionX, directionY;

    Direction(int directionX, int directionY)
    {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public int getDirectionX()
    {
        return directionX;
    }

    public int getDirectionY()
    {
        return directionY;
    }

    public static Direction getOppositeDirection(Direction direction)
    {
        switch (direction)
        {
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            default:
                return null;
        }
    }
}
