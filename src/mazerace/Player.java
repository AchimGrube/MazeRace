package mazerace;

import javax.swing.*;
import java.awt.*;

public class Player extends Cell
{
    static final int NO_HELPTRACK = 0, HELPTRACK = 1, SIMULATION = 2;
    private Maze maze;
    private int stepCount;

    public Player(Maze maze, int positionX, int positionY)
    {
        super(positionX, positionY);
        setStatus(Status.PLAYER);
        setBackground(getStatus().getColor());
        this.maze = maze;
    }

    int getStepCount()
    {
        return stepCount;
    }

    private void addStepCount()
    {
        stepCount++;
    }

    protected Cell getCurrentCell()
    {
        return maze.getMaze()[getPositionX()][getPositionY()];
    }

    protected Cell step(int helptrack, int directionX, int directionY)
    {
        Cell currentCell = getCurrentCell();
        if ((directionY == -1 && currentCell.getWalls()[WALL_TOP] == 0)
                || (directionX == 1 && currentCell.getWalls()[WALL_RIGHT] == 0)
                || (directionY == 1 && currentCell.getWalls()[WALL_BOTTOM] == 0)
                || (directionX == -1 && currentCell.getWalls()[WALL_LEFT] == 0))
        {
            addStepCount();
            int newPositionX = getPositionX() + directionX;
            int newPositionY = getPositionY() + directionY;
            Cell targetCell = maze.getMaze()[newPositionX][newPositionY];
            if (targetCell.getStatus() != Status.GOAL && targetCell.getStatus() != Status.START)
            {
                if (helptrack == HELPTRACK)
                {
                    targetCell.setStatus(Status.HELPTRACK);
                    targetCell.draw();
                }
                else if (helptrack == SIMULATION)
                {
                    targetCell.setStatus(Status.SIMULATION);
                    targetCell.draw();
                }
            }
            setPosition(newPositionX, newPositionY);
            draw();
        }
        return getCurrentCell();
    }

    public void setPosition(int positionX, int positionY)
    {
        setPositionX(positionX);
        setPositionY(positionY);
    }

    @Override
    public void draw()
    {
        super.draw();
        setSize(Cell.getSideLength() / 2, Cell.getSideLength() / 2);
        setLocation(getPositionX() * Cell.getSideLength() + 1 + Cell.getSideLength() / 4, getPositionY() * Cell.getSideLength() + 1 + Cell.getSideLength() / 4);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, Cell.getSideLength() / 50 + 1));
    }
}
