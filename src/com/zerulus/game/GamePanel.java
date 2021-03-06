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

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update

        final int MUBR = 5; // must update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) lastUpdateTime / 1000000000;
        int oldFrameCount = 0;

        while(running){
            double now = System.nanoTime();
            int updateCount = 0;
            while((now - lastUpdateTime > TBU) && (updateCount < MUBR)){
                update();
                input();
                lastUpdateTime += TBU;
                updateCount++;
            }
            if(now - lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }

            input();
            render();
            draw();
            lastRenderTime = now;
            frameCount++;

            int thisSecond = (int) (lastSecondTime / 1000000000);
            if(thisSecond > lastSecondTime){
                if(frameCount != oldFrameCount){
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while(now - lastRenderTime < TTBR && now - lastUpdateTime < TBU){
                Thread.yield();

                try{
                    Thread.sleep(1);
                }
                catch(Exception e){
                    System.out.println("ERROR: yielding thread");
                }
                now = System.nanoTime();
            }


        }
    }


    public void update(){

    };

    public void input(){

    }

    public void render(){
        if(g != null){
            g.setColor(new Color(66, 134, 244));
            g.fillRect(0,0, width, height);
        }
    };
    public void draw(){
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
    };

    /*
        Questions:
        1. what does runnable do?
        2. why does the run method need a thread? ok in the video he had the thread inside the run method but then moved it into addNotify... why?
        3. what is the init method for?
        4. how did he know he would need a render & draw method? what is about the code that implies that those methods are necessary?
        5. what is the draw method doing?
        6.
     */
}
