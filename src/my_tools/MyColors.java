package my_tools;

import java.awt.Color;

public class MyColors
{
    public static final Color RED = new Color(0xDB, 0x70, 0x93);
    public static final Color GREEN = new Color(0x98, 0xFB, 0x98);
    public static final Color BLUE = new Color(0xAF, 0xEE, 0xEE);
    public static final Color YELLOW = new Color(0xEE, 0xE8, 0xAA);
    public static Color RAINBOW = getRainbowColor();
    private static int rainbowCount = 0;

    public static Color mixColors(Color... colors)
    {
        float ratio = 1 / ((float) colors.length);
        int red = 0, green = 0, blue = 0;
        for (Color color : colors)
        {
            red += color.getRed() * ratio;
            green += color.getGreen() * ratio;
            blue += color.getBlue() * ratio;
        }
        return new Color(red, green, blue);
    }

    public static Color getRainbowColor()
    {
        Color color = null;
        switch (rainbowCount)
        {
            case 0:
                color = RED;
                break;
            case 1:
                color = mixColors(RED, YELLOW);
                break;
            case 2:
                color = YELLOW;
                break;
            case 3:
                color = mixColors(YELLOW, BLUE);
                break;
            case 4:
                color = BLUE;
                break;
            case 5:
                color = mixColors(RED, BLUE);
                break;
        }
        rainbowCount = rainbowCount == 5 ? 0 : ++rainbowCount;
        return color;
    }
}
