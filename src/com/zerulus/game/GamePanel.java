package com.zerulus.game;
 import javax.swing.JPanel;
 import java.awt.*;
 import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable{
    public static int width;
    public static int height;
    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    public GamePanel(int width, int height){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();

        if(thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init(){
        running = true;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();
    }
    @Override
    public void run() {
        init();
        while(running){
            update();
            render();
            draw();
        }
    }

    private int x = 0;

    public void update(){
        x++;
        System.out.println(x);
    };
    public void render(){};
    public void draw(){};

    /*
        Questions:
        1. what does runnable do?
        2. why does the run method need a thread? ok in the video he had the thread inside the run method but then moved it into addNotify... why?
        3. what is the init method for?
        4. how did he know he would need a render & draw method? what is about the code that implies that those methods are necessary?

     */
}
