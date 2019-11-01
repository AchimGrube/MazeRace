package opponents;

import mazerace.Cell;
import mazerace.Maze;
import mazerace.Player;
import mazerace.Status;

import java.util.function.Predicate;

public abstract class Opponent extends Player
{
    public Opponent(Maze maze)
    {
        super(maze, 0, 0);
        setStatus(Status.OPPONENT);
        setBackground(getStatus().getColor());
    }

    abstract public Cell step();
    
    abstract public String getOpponentName();

    private Direction currentDirection;

    Direction getCurrentDirection()
    {
        return currentDirection;
    }

    void setCurrentDirection(Direction currentDirection)
    {
        this.currentDirection = currentDirection;
    }

    Predicate<Cell> isInDeadEnd = (c) -> countWalls(c) == 3;

    Predicate<Cell> isOnStraightPath = (c) -> countWalls(c) == 2;

    Predicate<Cell> isAtCross = (c) -> countWalls(c) <= 1;

    Predicate<Cell> isInCorner = (c) ->
    {
        if (countWalls(c) == 2)
        {
            switch (currentDirection)
            {
                case UP:
                    return c.getWalls()[WALL_BOTTOM] == 0 && c.getWalls()[WALL_TOP] == 1;
                case LEFT:
                    return c.getWalls()[WALL_RIGHT] == 0 && c.getWalls()[WALL_LEFT] == 1;
                case DOWN:
                    return c.getWalls()[WALL_TOP] == 0 && c.getWalls()[WALL_BOTTOM] == 1;
                case RIGHT:
                    return c.getWalls()[WALL_LEFT] == 0 && c.getWalls()[WALL_RIGHT] == 1;
                default:
                    break;
            }
        }
        return false;
    };

    private int countWalls(Cell cell)
    {
        int count = 0;
        for (int wall : cell.getWalls())
        {
            count += wall;
        }
        return count;
    }
}