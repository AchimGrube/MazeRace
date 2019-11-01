package opponents;

import mazerace.Cell;
import mazerace.Maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class AI_TremauxMethod extends Opponent
{
    private int showPath;
    private HashMap<Cell, HashMap<Direction, Integer>> mazeMap;

    private Predicate<Cell> isUnvisitedSquare = (c) ->
    {
        int count = 0;
        for (int value : mazeMap.get(c).values())
        {
            count += value;
        }
        return count == 0;
    };

    private Predicate<Cell> isFeederUnvisited = (c) -> mazeMap.get(c).get(Direction.getOppositeDirection(getCurrentDirection())) == 0;

    public AI_TremauxMethod(Maze maze, int showPath)
    {
        super(maze);
        this.showPath = showPath;
        mazeMap = new HashMap<>();
        createEmptyMazeMap(maze);
    }

    private void createEmptyMazeMap(Maze maze)
    {
        for (Cell[] cells : maze.getMaze())
        {
            for (Cell cell : cells)
            {
                HashMap<Direction, Integer> newEntry = new HashMap<>();
                for (Direction direction : Direction.values())
                {
                    newEntry.put(direction, 0);
                }
                mazeMap.put(cell, newEntry);
            }
        }
    }

    @Override
    public Cell step()
    {
        Cell currentCell = getCurrentCell();
        ArrayList<Direction> possibleDirections = getPossibleDirections(currentCell);
        boolean wasOnCross = false;

        if (getCurrentDirection() == null)
        {
            setCurrentDirection(getRandomDirection(possibleDirections));
        }
        else if (isAtCross.test(currentCell))
        {
            wasOnCross = true;

            if (isUnvisitedSquare.test(currentCell))
            {
                addMarker(currentCell, Direction.getOppositeDirection(getCurrentDirection()));
                possibleDirections.remove(Direction.getOppositeDirection(getCurrentDirection()));
            }
            else if (!isUnvisitedSquare.test(currentCell) && isFeederUnvisited.test(currentCell))
            {
                addMarker(currentCell, Direction.getOppositeDirection(getCurrentDirection()));
                possibleDirections.clear();
                possibleDirections.add(Direction.getOppositeDirection(getCurrentDirection()));
            }
            else if (!isUnvisitedSquare.test(currentCell) && !isFeederUnvisited.test(currentCell))
            {
                addMarker(currentCell, Direction.getOppositeDirection(getCurrentDirection()));
                ArrayList<Direction> validDirections = (ArrayList<Direction>)possibleDirections.clone();
                getValidPaths(currentCell, validDirections, 0);
                if (validDirections.isEmpty())
                {
                    validDirections = (ArrayList<Direction>)possibleDirections.clone();
                    getValidPaths(currentCell, validDirections, 1);
                }
                possibleDirections = validDirections;
            }
        }
        else if (isInCorner.test(currentCell))
        {
            possibleDirections.remove(Direction.getOppositeDirection(getCurrentDirection()));
        }
        else if (isInDeadEnd.test(currentCell))
        {
            possibleDirections.clear();
            possibleDirections.add(Direction.getOppositeDirection(getCurrentDirection()));
        }
        else if (isOnStraightPath.test(currentCell))
        {
            possibleDirections.clear();
            possibleDirections.add(getCurrentDirection());
        }
        setCurrentDirection(getRandomDirection(possibleDirections));

        if (wasOnCross)
        {
            addMarker(currentCell, getCurrentDirection());
        }

        return super.step(showPath, getCurrentDirection().getDirectionX(), getCurrentDirection().getDirectionY());
    }

    private void addMarker(Cell cell, Direction direction)
    {
        HashMap<Direction, Integer> change = mazeMap.get(cell);
        change.put(direction, change.get(direction) + 1);
    }

    private void getValidPaths(Cell currentCell, ArrayList<Direction> validDirections, int count)
    {
        if (mazeMap.get(currentCell).get(Direction.UP) != count)
        {
            validDirections.remove(Direction.UP);
        }
        if (mazeMap.get(currentCell).get(Direction.LEFT) != count)
        {
            validDirections.remove(Direction.LEFT);
        }
        if (mazeMap.get(currentCell).get(Direction.DOWN) != count)
        {
            validDirections.remove(Direction.DOWN);
        }
        if (mazeMap.get(currentCell).get(Direction.RIGHT) != count)
        {
            validDirections.remove(Direction.RIGHT);
        }
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
        return "[TRMX]";
    }
}
