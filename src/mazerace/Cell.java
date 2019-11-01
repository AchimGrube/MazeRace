package mazerace;

import my_tools.MyColors;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JPanel
{
    protected final static int WALL_TOP = 0, WALL_LEFT = 1, WALL_BOTTOM = 2, WALL_RIGHT = 3;
    private static int sideLength;

    private int positionX, positionY;
    private Status status;
    private int[] walls;
    private ArrayList<Cell> neighbours;

    public Cell(int posX, int posY)
    {
        this.positionX = posX;
        this.positionY = posY;
        status = Status.UNVISITED;
        walls = new int[]
        {
            1, 1, 1, 1
        };
        neighbours = new ArrayList<>();
    }

    int getPositionX()
    {
        return positionX;
    }

    void setPositionX(int positionX)
    {
        this.positionX = positionX;
    }

    int getPositionY()
    {
        return positionY;
    }

    void setPositionY(int positionY)
    {
        this.positionY = positionY;
    }

    protected final Status getStatus()
    {
        return status;
    }

    protected final void setStatus(Status status)
    {
        this.status = status;
    }

    public int[] getWalls()
    {
        return walls;
    }

    ArrayList<Cell> getNeighbours()
    {
        return neighbours;
    }

    static int getSideLength()
    {
        return sideLength;
    }

    static void setSideLength(int sideLength)
    {
        Cell.sideLength = sideLength;
    }

    private void addNeighbour(Cell cell)
    {
        neighbours.add(cell);
    }

    void breakWall(int wall)
    {
        walls[wall] = 0;
    }

    public void draw()
    {
        if (getStatus() == Status.SIMULATION)
        {
            setBackground(MyColors.getRainbowColor());
        }
        else
        {
            setBackground(status.getColor());
        }
        setBorder(BorderFactory.createMatteBorder(walls[WALL_TOP], walls[WALL_LEFT], walls[WALL_BOTTOM], walls[WALL_RIGHT], Color.BLACK));
    }

    void calculateNeighbours(Cell[][] maze, int width, int height)
    {
        int x = getPositionX();
        int y = getPositionY();

        if (x != 0)
        {
            addNeighbour(maze[x - 1][y]);
        }
        if (y != 0)
        {
            addNeighbour(maze[x][y - 1]);
        }
        if (x < width - 1)
        {
            addNeighbour(maze[x + 1][y]);
        }
        if (y < height - 1)
        {
            addNeighbour(maze[x][y + 1]);
        }
    }
}
