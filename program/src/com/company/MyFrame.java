package com.company;
import javax.swing.*;

public class MyFrame extends JFrame
{
    GraphicsDemo graphicsDemo = new GraphicsDemo();

    public MyFrame ()
    {
        this.setSize(1000,1000);
        this.add(graphicsDemo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}
