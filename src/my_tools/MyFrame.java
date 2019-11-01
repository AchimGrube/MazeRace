package my_tools;

import javax.swing.*;

public class MyFrame extends JFrame
{
    public MyFrame()
    {
        this("");
    }

    public MyFrame(String title)
    {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void show(int width, int height)
    {
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}