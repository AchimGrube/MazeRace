package opponents;

import mazerace.Cell;
import mazerace.Maze;

public class AI_RightHandMethod extends Opponent
{
    private int showPath;

    public AI_RightHandMethod(Maze maze, int showPath)
    {
        super(maze);
        this.showPath = showPath;
    }

    @Override
    public Cell step()
    {
        Cell currentCell = getCurrentCell();

        if (getCurrentDirection() == null)
        {
            setCurrentDirection(getNewDirection(currentCell));
        }
        else if (currentCell.getWalls()[getRightHandWall()] == 0)
        {
            setCurrentDirection(getRightHandDirection(getRightHandWall()));
        }
        else if (isInDeadEnd.test(currentCell))
        {
            setCurrentDirection(Direction.getOppositeDirection(getCurrentDirection()));
        }
        else if (isInCorner.test(currentCell))
        {
            setCurrentDirection(getNewDirection(currentCell));
        }

        return super.step(showPath, getCurrentDirection().getDirectionX(), getCurrentDirection().getDirectionY());
    }

    private int getRightHandWall()
    {
        switch (getCurrentDirection())
        {
            case UP:
                return WALL_RIGHT;
            case LEFT:
                return WALL_TOP;
            case DOWN:
                return WALL_LEFT;
            case RIGHT:
                return WALL_BOTTOM;
        }
        return -1;
    }

    private Direction getRightHandDirection(int wall)
    {
        switch (wall)
        {
            case WALL_TOP:
                return Direction.UP;
            case WALL_LEFT:
                return Direction.LEFT;
            case WALL_BOTTOM:
                return Direction.DOWN;
            case WALL_RIGHT:
                return Direction.RIGHT;
        }
        return null;
    }

    private Direction getNewDirection(Cell cell)
    {
        if (cell.getWalls()[WALL_TOP] == 0 && getCurrentDirection() != Direction.DOWN)
        {
            return Direction.UP;
        }
        if (cell.getWalls()[WALL_LEFT] == 0 && getCurrentDirection() != Direction.RIGHT)
        {
            return Direction.LEFT;
        }
        if (cell.getWalls()[WALL_BOTTOM] == 0 && getCurrentDirection() != Direction.UP)
        {
            return Direction.DOWN;
        }
        if (cell.getWalls()[WALL_RIGHT] == 0 && getCurrentDirection() != Direction.LEFT)
        {
            return Direction.RIGHT;
        }
        return null;
    }

    @Override
    public String getOpponentName()
    {
        return "[RHM]";
    }
}
