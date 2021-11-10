package com.zerulus.game;
import javax.swing.JFrame;
public class Window extends JFrame{
    public Window(){
        setTitle("Your mom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new GamePanel(1200, 720)); // 1. setContentPane
        pack(); // 2. pack
        setLocationRelativeTo(null); // 3.
        setVisible(true);

    }
}

/*
    1. Does setContentPane set the content inside the window?
    2. Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
    3. why is there a null parameter here?
    4. what is the default value of setVisible()? is it false b/c he passed in true.
 */