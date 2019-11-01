package mazerace;

import my_tools.MyColors;

import java.awt.*;

public enum Status
{
    UNVISITED(Color.BLACK), VISITED(Color.WHITE), START(Color.GREEN), GOAL(Color.RED), PLAYER(MyColors.BLUE), HELPTRACK(MyColors.YELLOW),
    OPPONENT(MyColors.mixColors(MyColors.BLUE, MyColors.RED)), SIMULATION(MyColors.RAINBOW);

    private Color color;

    Status(Color color)
    {
        this.color = color;
    }

    public Color getColor()
    {
        return color;
    }
}
