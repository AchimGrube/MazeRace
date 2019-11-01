package mazerace;

import opponents.Opponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Predicate;

public class Maze extends JPanel
{
    private final int mazeWidth, mazeHeight;
    private ArrayList<Cell> unvisitedCells;
    private Cell[][] maze;
    private Stack<Cell> backtrackStack;
    private Player player;
    private Opponent opponent;
    private Cell startCell, goalCell;

    private Predicate<Cell> hasAllWalls = (c) ->
    {
        int count = 0;
        for (int wall : c.getWalls())
        {
            count += wall;
        }
        return count == 4;
    };

    private Predicate<Cell> isDeadEnd = (c) ->
    {
        int count = 0;
        for (int wall : c.getWalls())
        {
            count += wall;
        }
        return count == 3;
    };

    public Maze(int mazeWidth, int mazeHeight)
    {
        setBackground(Status.VISITED.getColor());
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        unvisitedCells = new ArrayList<>();
        backtrackStack = new Stack<>();
        maze = new Cell[mazeWidth][mazeHeight];
        for (int x = 0; x < mazeWidth; x++)
        {
            for (int y = 0; y < mazeHeight; y++)
            {
                maze[x][y] = new Cell(x, y);
                unvisitedCells.add(maze[x][y]);
            }
        }

        for (int x = 0; x < mazeWidth; x++)
        {
            for (int y = 0; y < mazeHeight; y++)
            {
                maze[x][y].calculateNeighbours(maze, mazeWidth, mazeHeight);
            }
        }
    }

    int getMazeWidth()
    {
        return mazeWidth;
    }

    int getMazeHeight()
    {
        return mazeHeight;
    }

    public Cell[][] getMaze()
    {
        return maze;
    }

    private Cell getStartCell()
    {
        return startCell;
    }

    Cell getGoalCell()
    {
        return goalCell;
    }

    Player getPlayer()
    {
        return player;
    }

    Opponent getOpponent()
    {
        return opponent;
    }

    void setOpponent(Opponent opponent)
    {
        this.opponent = opponent;
    }

    void generate()
    {
        int rndX = (int) (Math.random() * mazeWidth);
        int rndY = (int) (Math.random() * mazeHeight);

        Cell currentCell = maze[rndX][rndY];

        while (!unvisitedCells.isEmpty())
        {
            unvisitedCells.remove(currentCell);
            currentCell.setStatus(Status.VISITED);

            ArrayList<Cell> neighbours = currentCell.getNeighbours();
            ArrayList<Cell> remove = new ArrayList<>();

            for (Cell cell : neighbours)
            {
                if (!hasAllWalls.test(cell))
                {
                    remove.add(cell);
                }
            }
            neighbours.removeAll(remove);

            if (!neighbours.isEmpty())
            {
                int n = (int) (Math.random() * neighbours.size());
                Cell nextCell = neighbours.get(n);
                int breakWallX = nextCell.getPositionX() - currentCell.getPositionX();
                int breakWallY = nextCell.getPositionY() - currentCell.getPositionY();
                if (breakWallY == -1)
                {
                    currentCell.breakWall(Cell.WALL_TOP);
                    nextCell.breakWall(Cell.WALL_BOTTOM);
                }
                else if (breakWallX == -1)
                {
                    currentCell.breakWall(Cell.WALL_LEFT);
                    nextCell.breakWall(Cell.WALL_RIGHT);
                }
                else if (breakWallY == 1)
                {
                    currentCell.breakWall(Cell.WALL_BOTTOM);
                    nextCell.breakWall(Cell.WALL_TOP);
                }
                else if (breakWallX == 1)
                {
                    currentCell.breakWall(Cell.WALL_RIGHT);
                    nextCell.breakWall(Cell.WALL_LEFT);
                }

                backtrackStack.push(currentCell);
                currentCell = nextCell;
            }
            else
            {
                currentCell = backtrackStack.pop();
            }
        }
        setStartCell();
        setGoalCell();
    }

    private void setStartCell()
    {
        int startX = 0;
        int startY = 0;

        do
        {
            startCell = maze[startX][startY];
            startX++;
            if (startX > mazeWidth - 1)
            {
                startX = 0;
                startY++;
            }
        }
        while (!isDeadEnd.test(startCell));
        startCell.setStatus(Status.START);
    }

    private void setGoalCell()
    {
        int goalX = mazeWidth - 1;
        int goalY = mazeHeight - 1;

        do
        {
            goalCell = maze[goalX][goalY];
            goalX--;
            if (goalX < 0)
            {
                goalX = mazeWidth - 1;
                goalY--;
            }
        }
        while (!isDeadEnd.test(goalCell));
        goalCell.setStatus(Status.GOAL);
    }

    void draw(int sideLength)
    {
        removeAll();

        player = new Player(this, 0, 0);
        add(player);
        if (opponent != null)
        {
            add(opponent);
        }

        for (int x = 0; x < mazeWidth; x++)
        {
            for (int y = 0; y < mazeHeight; y++)
            {
                Cell cell = maze[x][y];

                cell.setBounds(x * sideLength + 1, y * sideLength + 1, sideLength, sideLength);
                cell.draw();
                add(cell);
            }
        }
        
        player.setPosition(getStartCell().getPositionX(), getStartCell().getPositionY());
        player.draw();
        if (opponent != null)
        {
            opponent.setPosition(getStartCell().getPositionX(), getStartCell().getPositionY());
            opponent.draw();
        }
    }

    @Override
    public String toString()
    {
        return String.format("%dx%d", mazeWidth, mazeHeight);
    }
}
