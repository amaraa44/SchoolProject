package com.amaraa.main;

import com.amaraa.main.keymanager.KeyManager;
import com.amaraa.main.objects.Handler;
import com.amaraa.main.objects.ID;
import com.amaraa.main.objects.entities.BasicEnemy;
import com.amaraa.main.objects.entities.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    //4:3 aspect ratio
//    public static final int HEIGHT = 360, WIDTH = HEIGHT / 3 * 4;
    public static final int WIDTH = 480, HEIGHT = 360;
    private Thread thread;
    private boolean running = false;
    private Random r;

    private Handler handler;

    public Game(){

        //TODO: https://www.youtube.com/watch?v=5ufOPX8N1Rg

        handler = new Handler();
        this.addKeyListener(new KeyManager(handler));

        new Window(WIDTH, HEIGHT, "The game", this);

        r = new Random();
        System.out.println(WIDTH + " " + HEIGHT);
        handler.addObject(new Player(WIDTH/2-32,HEIGHT/5*4-32, ID.Player));
        handler.addObject(new BasicEnemy(r.nextInt(WIDTH-64),-31, ID.BasicEnemy));

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;

    }
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    private void tick(){
        handler.tick();
    }
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH, HEIGHT);

        handler.render(g);

        g.dispose();
        bs.show();
    }

    public static void main(String[] args){

        new Game();

    }
}
