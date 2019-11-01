package opponents;

import mazerace.Cell;
import mazerace.Maze;

import java.util.ArrayList;

public class AI_RandomPath extends Opponent
{
    private int showPath;

    public AI_RandomPath(Maze maze, int showPath)
    {
        super(maze);
        this.showPath = showPath;
    }

    @Override
    public Cell step()
    {
        Cell currentCell = getCurrentCell();
        ArrayList<Direction> possibleDirections = getPossibleDirections(currentCell);

        if (getCurrentDirection() != null)
        {
            if (isInDeadEnd.test(currentCell))
            {
                possibleDirections.clear();
                possibleDirections.add(Direction.getOppositeDirection(getCurrentDirection()));
            }
            else if (isInCorner.test(currentCell) || isAtCross.test(currentCell))
            {
                possibleDirections.remove(Direction.getOppositeDirection(getCurrentDirection()));
            }
            else if (isOnStraightPath.test(currentCell))
            {
                possibleDirections.clear();
                possibleDirections.add(getCurrentDirection());
            }
        }
        setCurrentDirection(getRandomDirection(possibleDirections));

        return super.step(showPath, getCurrentDirection().getDirectionX(), getCurrentDirection().getDirectionY());
    }

    private ArrayList<Direction> getPossibleDirections(Cell currentCell)
    {
        ArrayList<Direction> possibleDirections = new ArrayList<>();
        if (currentCell.getWalls()[WALL_TOP] == 0)
        {
            possibleDirections.add(Direction.UP);
        }
        if (currentCell.getWalls()[WALL_LEFT] == 0)
        {
            possibleDirections.add(Direction.LEFT);
        }
        if (currentCell.getWalls()[WALL_BOTTOM] == 0)
        {
            possibleDirections.add(Direction.DOWN);
        }
        if (currentCell.getWalls()[WALL_RIGHT] == 0)
        {
            possibleDirections.add(Direction.RIGHT);
        }
        return possibleDirections;
    }

    private Direction getRandomDirection(ArrayList<Direction> possibleDirections)
    {
        int random = (int) (Math.random() * possibleDirections.size());
        return possibleDirections.get(random);
    }

    @Override
    public String getOpponentName()
    {
        return "[RND]";
    }
}
